package com.example.ideal48.application160519.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ideal48.application160519.AnimeRoomDatabase;
import com.example.ideal48.application160519.NetworkState;
import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.AnimeDao;
import com.example.ideal48.application160519.activity.AnimeDetailsActivity;
import com.example.ideal48.application160519.model.Anime;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AnimeListAdapter extends PagedListAdapter<Anime, RecyclerView.ViewHolder> {

    private Context context;
    private AnimeDao animeDao;
    private List<Integer> favAnimeIdList = new ArrayList<>();
    private NetworkState networkState;

    public AnimeListAdapter(Context context) {
        super(Anime.DIFF_CALLBACK);
        this.context = context;

        AnimeRoomDatabase animeRoomDatabase = AnimeRoomDatabase.getDatabase(context);
        animeDao = animeRoomDatabase.animeDao();

        List<Anime> favAnimeList = animeDao.getAllFavAnime();

        for (int i = 0; i < favAnimeList.size(); i++) {
            favAnimeIdList.add(favAnimeList.get(i).getmMalId());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.anime_list_item) {
            View view = layoutInflater.inflate(R.layout.anime_list_item, parent, false);
            AnimeViewHolder viewHolder = new AnimeViewHolder(view);
            return viewHolder;
        } else if (viewType == R.layout.network_state_item) {
            View view = layoutInflater.inflate(R.layout.network_state_item, parent, false);
            return new NetworkStateItemViewHolder(view);
        } else {
            throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.anime_list_item:
                ((AnimeViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.network_state_item:
                ((NetworkStateItemViewHolder) holder).bindView(networkState);
                break;
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.anime_list_item;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    class AnimeViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView typeView;
        TextView episodesView;
        TextView scoreView;
        ImageView posterView;
        ImageView animeFavView;

        AnimeViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.anime_title_tv);
            typeView = itemView.findViewById(R.id.anime_type_tv);
            episodesView = itemView.findViewById(R.id.anime_episodes_tv);
            scoreView = itemView.findViewById(R.id.anime_score_tv);
            posterView = itemView.findViewById(R.id.anime_poster_iv);
            animeFavView = itemView.findViewById(R.id.anime_fav_iv);
        }

        void bindTo(final Anime anime) {
//            if (anime != null) {
                titleView.setText(anime.getmTitle());
                scoreView.setText(String.valueOf(anime.getmScore()));
                typeView.setText("Type: " + anime.getmType());
                episodesView.setText("Episodes: " + String.valueOf(anime.getmEpisodes()));

                Picasso.get().load(anime.getmImageUrl()).into(posterView);

                if (favAnimeIdList.contains(anime.getmMalId())) {
                    animeFavView.setImageResource(R.drawable.ic_favorite);
                } else {
                    animeFavView.setImageResource(R.drawable.ic_favorite_border);
                }

                animeFavView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (favAnimeIdList.contains(anime.getmMalId())) {
                            favAnimeIdList.remove((Integer) anime.getmMalId());
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    animeDao.deleteFavAnime(anime.getmMalId());
                                    return null;
                                }
                            }.execute();
                            animeFavView.setImageResource(R.drawable.ic_favorite_border);
                        } else {
                            favAnimeIdList.add(anime.getmMalId());
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    animeDao.setAnimeFav(anime.getmMalId());
                                    return null;
                                }
                            }.execute();
                            animeFavView.setImageResource(R.drawable.ic_favorite);
                        }
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int malId = anime.getmMalId();
                        Intent i = new Intent(context, AnimeDetailsActivity.class);
                        i.putExtra("malId", malId);
                        context.startActivity(i);
                    }
                });
//            } else {
//                titleView.setText(R.string.loading);
//                typeView.setText(R.string.loading);
//            }
        }
    }

    public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        private final ProgressBar progressBar;
        private final TextView errorMsg;
        private final Button retryBtn;

        public NetworkStateItemViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
            errorMsg = itemView.findViewById(R.id.error_msg);
            retryBtn = itemView.findViewById(R.id.retry_btn);
        }


        public void bindView(NetworkState networkState) {

            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                progressBar.setVisibility(View.VISIBLE);
                retryBtn.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
                errorMsg.setVisibility(View.VISIBLE);
                errorMsg.setText(networkState.getMsg());
                if (networkState.getMsg() != "Not Found") {
                    retryBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            networkState.getRetryable().retry();
                        }
                    });
                } else {
                    retryBtn.setVisibility(View.GONE);
                }
            } else {
                errorMsg.setVisibility(View.GONE);
            }
        }
    }
}
