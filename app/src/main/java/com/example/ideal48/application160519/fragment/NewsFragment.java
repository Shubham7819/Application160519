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
import com.example.ideal48.application160519.model.NewsResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    List<NewsResponse.News> newsList;
    Picasso picasso;
    Context context;
    View loadingIndicator;
    private TextView mEmptyStateTextView;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.list_layout, container, false);

        loadingIndicator = view.findViewById(R.id.list_loading_indicator);
        mEmptyStateTextView = view.findViewById(R.id.list_empty_view);
        recyclerView = view.findViewById(R.id.list_recycler_view);

        context = getActivity();
        picasso = Picasso.get();

        Call<NewsResponse> call = AnimeDetailsActivity.service.getNewsResponse(AnimeDetailsActivity.malId);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if ((response.body() != null) && (response.body().getmNewsList().size() != 0)) {

                    newsList = response.body().getmNewsList();

                    NewsListAdapter adapter = new NewsListAdapter(context);

                    recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    loadingIndicator.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);

                } else {
                    loadingIndicator.setVisibility(View.GONE);
                    mEmptyStateTextView.setText(R.string.empty_news_msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

        private LayoutInflater mInflater;

        NewsListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View mItemView = mInflater.inflate(R.layout.anime_list_item, parent, false);
            return new NewsViewHolder(mItemView);
        }

        @Override
        public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

            NewsResponse.News currentNews = newsList.get(position);

            String[] separatedDate = currentNews.getmDate().split(":00");

            picasso.load(currentNews.getmImageUrl()).into(holder.posterIV);
            holder.titleTV.setText(currentNews.getmTitle());
            holder.introTV.setText(currentNews.getmIntro());
            holder.otherDetailsTV.setText(separatedDate[0] + " by " + currentNews.getmAuthorName());

        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }

        class NewsViewHolder extends RecyclerView.ViewHolder {

            ImageView posterIV;
            TextView titleTV;
            TextView introTV;
            TextView otherDetailsTV;

            NewsViewHolder(View itemView) {
                super(itemView);
                posterIV = itemView.findViewById(R.id.anime_poster_iv);
                titleTV = itemView.findViewById(R.id.anime_title_tv);
                introTV = itemView.findViewById(R.id.anime_type_tv);
                otherDetailsTV = itemView.findViewById(R.id.anime_episodes_tv);
            }
        }
    }
}
