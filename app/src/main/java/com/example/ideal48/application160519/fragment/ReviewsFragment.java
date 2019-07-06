package com.example.ideal48.application160519.fragment;


import android.content.Context;
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
import com.example.ideal48.application160519.model.Review;
import com.example.ideal48.application160519.model.ReviewsResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {

    RecyclerView recyclerView;
    View view;
    List<Review> reviewList;
    Picasso picasso;
    View loadingIndicator;
    TextView emptyReviewsListTV;

    public ReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.list_layout, container, false);
        loadingIndicator = view.findViewById(R.id.list_loading_indicator);
        emptyReviewsListTV = view.findViewById(R.id.list_empty_view);

        picasso = Picasso.get();
        recyclerView = view.findViewById(R.id.list_recycler_view);

        Call<ReviewsResponse> call = AnimeDetailsActivity.service.getReviewsResponse(AnimeDetailsActivity.malId);
        call.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewsResponse> call, @NonNull Response<ReviewsResponse> response) {
                if (response.body() != null && response.body().getmReviewsList().size() != 0) {

                    reviewList = response.body().getmReviewsList();

                    ReviewsListAdapter reviewsListAdapter = new ReviewsListAdapter(getActivity());

                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                    loadingIndicator.setVisibility(View.GONE);
                    recyclerView.setAdapter(reviewsListAdapter);

                } else {
                    loadingIndicator.setVisibility(View.GONE);
                    emptyReviewsListTV.setText(R.string.empty_reviews_msg);
                }

            }

            @Override
            public void onFailure(@NonNull Call<ReviewsResponse> call, @NonNull Throwable t) {
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Data !", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsFragment.ReviewsListAdapter.ReviewsViewHolder> {

        private LayoutInflater mInflater;
        private Context context;

        ReviewsListAdapter(Context context) {
            if (context != null) {
                mInflater = LayoutInflater.from(context);
                this.context = context;
            }
        }

        @NonNull
        @Override
        public ReviewsFragment.ReviewsListAdapter.ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View mItemView = mInflater.inflate(R.layout.reviews_list_item, parent, false);
            return new ReviewsFragment.ReviewsListAdapter.ReviewsViewHolder(mItemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewsFragment.ReviewsListAdapter.ReviewsViewHolder holder, int position) {

            Review currentReview = reviewList.get(position);

            picasso.load(currentReview.getmReviewer().getmReviewerImageUrl()).into(holder.reviewerImageView);

            holder.reviewerNameTV.setText(currentReview.getmReviewer().getmReviewerName());

            String[] separatedDate = currentReview.getmReviewDate().split("T");
            String date = separatedDate[0];
            holder.reviewDateTV.setText(date);

            holder.helpfulCountTV.setText(currentReview.getmHelpfulCount() + getActivity().getString(R.string.helpful_count_review_text));

            holder.episodesSeenTV.setText(currentReview.getmReviewer().getmEpisodesSeen() + getActivity().getString(R.string.episodes_seen));

            holder.reviewerRatingTV.setText(getActivity().getString(R.string.overall_rating) + currentReview.getmReviewer().getmScores().getmOverallScore());

            holder.reviewContentTV.setText(currentReview.getmReviewText());

        }

        @Override
        public int getItemCount() {
            return reviewList.size();
        }

        class ReviewsViewHolder extends RecyclerView.ViewHolder {

            ImageView reviewerImageView;
            TextView reviewerNameTV;
            TextView reviewDateTV;
            TextView helpfulCountTV;
            TextView episodesSeenTV;
            TextView reviewerRatingTV;
            TextView reviewContentTV;

            ReviewsViewHolder(View itemView) {
                super(itemView);
                reviewerImageView = itemView.findViewById(R.id.reviewer_image);
                reviewerNameTV = itemView.findViewById(R.id.reviewer_name);
                reviewDateTV = itemView.findViewById(R.id.review_date);
                helpfulCountTV = itemView.findViewById(R.id.helpful_count);
                episodesSeenTV = itemView.findViewById(R.id.episodes_seen);
                reviewerRatingTV = itemView.findViewById(R.id.reviewer_rating);
                reviewContentTV = itemView.findViewById(R.id.review_content);
            }
        }
    }

}
