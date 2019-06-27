package com.example.ideal48.application160519.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.activity.AnimeDetailsActivity;
import com.example.ideal48.application160519.model.RecommendationsResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendationsFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    List<RecommendationsResponse.Recommendations> recommendationsList;
    Picasso picasso;
    Context context;
    View loadingIndicator;
    TextView emptyListTV;

    public RecommendationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.list_layout, container, false);

        loadingIndicator = view.findViewById(R.id.list_loading_indicator);
        emptyListTV = view.findViewById(R.id.list_empty_view);
        recyclerView = view.findViewById(R.id.list_recycler_view);

        context = getActivity();
        picasso = Picasso.with(context);

        Call<RecommendationsResponse> call = AnimeDetailsActivity.service.getRecommendationsResponse(AnimeDetailsActivity.malId);
        call.enqueue(new Callback<RecommendationsResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecommendationsResponse> call, @NonNull Response<RecommendationsResponse> response) {
                if (response.body() != null && response.body().getmRecommendationsList().size() != 0) {

                    recommendationsList = response.body().getmRecommendationsList();

                    RecommendationsListAdapter adapter = new RecommendationsListAdapter(context);

                    recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    loadingIndicator.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);

                } else {
                    loadingIndicator.setVisibility(View.GONE);
                    emptyListTV.setText(R.string.empty_recomm_msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecommendationsResponse> call, @NonNull Throwable t) {
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    class RecommendationsListAdapter extends RecyclerView.Adapter<RecommendationsFragment.RecommendationsListAdapter.RecommendationsViewHolder> {

        private LayoutInflater mInflater;
        private Context context;

        RecommendationsListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            this.context = context;
        }

        @NonNull
        @Override
        public RecommendationsFragment.RecommendationsListAdapter.RecommendationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View mItemView = mInflater.inflate(R.layout.anime_list_item, parent, false);
            return new RecommendationsFragment.RecommendationsListAdapter.RecommendationsViewHolder(mItemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecommendationsFragment.RecommendationsListAdapter.RecommendationsViewHolder holder, int position) {

            final RecommendationsResponse.Recommendations currentRecommendation = recommendationsList.get(position);

            picasso.load(currentRecommendation.getmImageUrl()).into(holder.posterImageView);

            holder.titleTV.setText(currentRecommendation.getmTitle());

            holder.recommendedByTV.setText("Recommended by " + currentRecommendation.getmRecommendationCount() + " users");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AnimeDetailsActivity.class);
                    intent.putExtra("malId", currentRecommendation.getmMalId());
                    getActivity().finish();
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return recommendationsList.size();
        }

        class RecommendationsViewHolder extends RecyclerView.ViewHolder {

            ImageView posterImageView;
            TextView titleTV;
            TextView recommendedByTV;

            RecommendationsViewHolder(View itemView) {
                super(itemView);
                posterImageView = itemView.findViewById(R.id.anime_poster_iv);
                titleTV = itemView.findViewById(R.id.anime_title_tv);
                recommendedByTV = itemView.findViewById(R.id.anime_type_tv);
            }
        }
    }

}
