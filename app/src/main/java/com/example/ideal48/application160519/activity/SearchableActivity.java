package com.example.ideal48.application160519.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideal48.application160519.GetDataService;
import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.RetrofitClientInstance;
import com.example.ideal48.application160519.model.AnimeSearchResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchableActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        recyclerView = findViewById(R.id.search_result_recycler_view);

        Toolbar toolbar = findViewById(R.id.searchable_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            toolbar.setTitle(query);

            if (query.length() < 3) {
                Toast.makeText(this, "Query should not be less than 3 characters.", Toast.LENGTH_SHORT).show();
            } else {
                GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                Call<AnimeSearchResponse> call = service.getSearchResults(query, 1);
                call.enqueue(new Callback<AnimeSearchResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<AnimeSearchResponse> call, @NonNull Response<AnimeSearchResponse> response) {
                        if (response.body() != null || response.body().getmSearchResultsList().size() != 0) {
                            List<AnimeSearchResponse.SearchResult> searchResultList = response.body().getmSearchResultsList();
                            Log.e("SearchableActivity", searchResultList.get(0).getmTitle());
                            recyclerView.addItemDecoration(new DividerItemDecoration(SearchableActivity.this, LinearLayoutManager.VERTICAL));
                            recyclerView.setLayoutManager(new LinearLayoutManager(SearchableActivity.this));

                            ResultListAdapter adapter = new ResultListAdapter(SearchableActivity.this, searchResultList);
                            recyclerView.setAdapter(adapter);

                        } else {
                            Toast.makeText(SearchableActivity.this, "No Results Found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AnimeSearchResponse> call, Throwable t) {
                        Toast.makeText(SearchableActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ResultViewHolder> {

        private LayoutInflater mInflater;
        private List<AnimeSearchResponse.SearchResult> mResultList;
        Picasso picasso;

        public ResultListAdapter(Context context, List<AnimeSearchResponse.SearchResult> resultList) {
            mInflater = LayoutInflater.from(context);
            mResultList = resultList;
            picasso = Picasso.get();
        }

        @NonNull
        @Override
        public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mItemView = mInflater.inflate(R.layout.anime_search_result_item, parent, false);
            return new ResultViewHolder(mItemView);
        }

        @Override
        public void onBindViewHolder(ResultViewHolder holder, int position) {

            final AnimeSearchResponse.SearchResult currentResult = mResultList.get(position);

            picasso.load(currentResult.getmImageUrl()).into(holder.resultImage);

            holder.resultTitle.setText(currentResult.getmTitle());
            holder.resultType.setText(currentResult.getmType());
            holder.resultEpisodes.setText(String.valueOf(currentResult.getmEpisodesCount()));
            holder.resultScore.setText(String.valueOf(currentResult.getmScore()));
            holder.resultSynopsis.setText(currentResult.getmSynopsis());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchableActivity.this, AnimeDetailsActivity.class);
                    intent.putExtra("malId", currentResult.getmMalId());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return mResultList.size();
        }

        public class ResultViewHolder extends RecyclerView.ViewHolder {

            ImageView resultImage;
            TextView resultTitle;
            TextView resultType;
            TextView resultEpisodes;
            TextView resultScore;
            TextView resultSynopsis;

            public ResultViewHolder(View itemView) {
                super(itemView);
                resultImage = itemView.findViewById(R.id.search_result_image);
                resultTitle = itemView.findViewById(R.id.search_result_title);
                resultType = itemView.findViewById(R.id.search_result_type);
                resultEpisodes = itemView.findViewById(R.id.search_result_episodes);
                resultScore = itemView.findViewById(R.id.search_result_score);
                resultSynopsis = itemView.findViewById(R.id.search_result_synopsis);
            }
        }
    }
}
