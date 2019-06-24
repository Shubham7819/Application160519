package com.example.ideal48.application160519.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.model.Episodes;
import com.example.ideal48.application160519.model.EpisodesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.ideal48.application160519.activity.AnimeDetailsActivity.malId;
import static com.example.ideal48.application160519.activity.AnimeDetailsActivity.service;

/**
 * A simple {@link Fragment} subclass.
 */
public class EpisodesFragment extends Fragment {

    View view;
    RecyclerView episodesRecyclerView;
    TextView emptyView;
    View loadingIndicator;

    List<Episodes> episodesList;

    public EpisodesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.list_layout, container, false);
        loadingIndicator = view.findViewById(R.id.list_loading_indicator);
        emptyView = view.findViewById(R.id.list_empty_view);

        //AnimeDetailsActivity.service

        episodesRecyclerView = view.findViewById(R.id.recycler_view);

        Call<EpisodesResponse> call = service.getEpisodesResponse(malId);
        call.enqueue(new Callback<EpisodesResponse>() {
            @Override
            public void onResponse(Call<EpisodesResponse> call, Response<EpisodesResponse> response) {
                if (response.body() != null && response.body().getmEpisodesList().size() != 0){

                    episodesList = response.body().getmEpisodesList();

                    EpisodesListAdapter episodesListAdapter = new EpisodesListAdapter(getActivity());
                    episodesRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                    episodesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    loadingIndicator.setVisibility(View.GONE);
                    episodesRecyclerView.setAdapter(episodesListAdapter);

                } else {
                    loadingIndicator.setVisibility(View.GONE);
                    emptyView.setText("Episodes Not Found !");
                }

            }

            @Override
            public void onFailure(Call<EpisodesResponse> call, Throwable t) {
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Data !", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    class EpisodesListAdapter extends RecyclerView.Adapter<EpisodesFragment.EpisodesListAdapter.EpisodesViewHolder> {

        private LayoutInflater mInflater;
        private Context context;

        public EpisodesListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public EpisodesFragment.EpisodesListAdapter.EpisodesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mItemView = mInflater.inflate(R.layout.epsiodes_list_item, parent, false);
            return new EpisodesFragment.EpisodesListAdapter.EpisodesViewHolder(mItemView);
        }

        @Override
        public void onBindViewHolder(EpisodesFragment.EpisodesListAdapter.EpisodesViewHolder holder, int position) {

            final Episodes currentEpisode = episodesList.get(position);
            String date = currentEpisode.getmAired();

            if (currentEpisode.getmAired() != null) {
                String[] separatedDate = currentEpisode.getmAired().split("T");
                date = separatedDate[0];
            }

            holder.episodeIndexTV.setText(String.valueOf(currentEpisode.getmEpisodeId()));
            holder.episodeTitleEngTV.setText(currentEpisode.getmTitle());
            holder.episodeAiredDateTV.setText(date);
            holder.otherTitlesTv.setText(currentEpisode.getmTitleRomanji() + "("
                    + currentEpisode.getmTitleJap() + ")");


//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    new AnimeVideoPopupWindow(context, R.layout.image_popup_window_layout, view, currentPicture.getmLarge(), null);
//                    Bundle b = new Bundle();
//                    b.putString(getString(R.string.url), currentEpisode.getmVideoUrl());
//                    b.putBoolean(getString(R.string.is_youtube_link), true);
//
//                    ShowVideoDialogFragment videoDialogFragment = new ShowVideoDialogFragment();
//                    videoDialogFragment.setArguments(b);
//                    videoDialogFragment.show(getActivity().getSupportFragmentManager().beginTransaction(), "ShowVideoDialogFragment");
//                    Toast.makeText(context, "item clicked...", Toast.LENGTH_SHORT).show();
//                }
//            });

        }

        @Override
        public int getItemCount() {
            return episodesList.size();
        }

        public class EpisodesViewHolder extends RecyclerView.ViewHolder {

            TextView episodeIndexTV;
            TextView episodeTitleEngTV;
            TextView episodeAiredDateTV;
            TextView otherTitlesTv;

            public EpisodesViewHolder(View itemView) {
                super(itemView);
                episodeIndexTV = itemView.findViewById(R.id.episode_index_tv);
                episodeTitleEngTV = itemView.findViewById(R.id.episode_title_eng_tv);
                episodeAiredDateTV = itemView.findViewById(R.id.episode_aired_date_tv);
                otherTitlesTv = itemView.findViewById(R.id.other_titles_tv);
            }
        }
    }

}
