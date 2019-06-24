package com.example.ideal48.application160519.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ideal48.application160519.AnimeImagePopupWindow;
import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.activity.AnimeDetailsActivity;
import com.example.ideal48.application160519.model.NewsResponse;
import com.example.ideal48.application160519.model.PicturesResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PicturesFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    List<PicturesResponse.Pictures> picturesList;
    Picasso picasso;
    Context context;
    View loadingIndicator;

    public PicturesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.list_layout, container, false);
        loadingIndicator = view.findViewById(R.id.list_loading_indicator);

        context = getActivity();
        picasso = Picasso.with(context);
        recyclerView = view.findViewById(R.id.recycler_view);

        Call<PicturesResponse> call = AnimeDetailsActivity.service.getPicturesResponse(AnimeDetailsActivity.malId);
        call.enqueue(new Callback<PicturesResponse>() {
            @Override
            public void onResponse(Call<PicturesResponse> call, Response<PicturesResponse> response) {
                if (response.body() != null && response.body().getmPicturesList().size() != 0) {

                    picturesList = response.body().getmPicturesList();

                    PicturesAdapter adapter = new PicturesAdapter(context);

                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    loadingIndicator.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);

                } else {
                    loadingIndicator.setVisibility(View.GONE);
                    Toast.makeText(context, "Images not found !", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PicturesResponse> call, Throwable t) {
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.PicturesViewHolder>{

        LayoutInflater mInflater;

        public PicturesAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public PicturesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mItemView = mInflater.inflate(R.layout.pictures_list_item, parent, false);
            return new PicturesViewHolder(mItemView);
        }

        @Override
        public void onBindViewHolder(final PicturesViewHolder holder, int position) {
            final PicturesResponse.Pictures currentPicture = picturesList.get(position);

            picasso.load(currentPicture.getmSmall()).into(holder.pictureIv);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AnimeImagePopupWindow(context, R.layout.image_popup_window_layout, view, currentPicture.getmLarge(), null);
                    Toast.makeText(context, "item clicked...", Toast.LENGTH_SHORT).show();
                }
            });

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder imageDialog = new AlertDialog.Builder(getActivity());
////                    ImageView showImage = new ImageView(getActivity());
//                    imageDialog.setView(holder.pictureIv);
//
//                    imageDialog.setNegativeButton("ok", new DialogInterface.OnClickListener()
//                    {
//                        public void onClick(DialogInterface arg0, int arg1)
//                        {
//                        }
//                    }).create().show();
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return picturesList.size();
        }

        public class PicturesViewHolder extends RecyclerView.ViewHolder{

            ImageView pictureIv;

            public PicturesViewHolder(View itemView) {
                super(itemView);
                pictureIv = itemView.findViewById(R.id.picture_iv);
            }
        }
    }
}
