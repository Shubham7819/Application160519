package com.example.ideal48.application160519.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ideal48.application160519.AnimeRoomDatabase;
import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.AnimeDao;
import com.example.ideal48.application160519.activity.AnimeDetailsActivity;
import com.example.ideal48.application160519.model.Anime;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AnimeListAdapter extends PagedListAdapter<Anime, AnimeListAdapter.AnimeViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private AnimeDao animeDao;
    private List<Integer> favAnimeIdList = new ArrayList<>();

    public AnimeListAdapter(Context context) {
        super(DIFF_CALLBACK);
        mInflater = LayoutInflater.from(context);
        this.context = context;

        AnimeRoomDatabase animeRoomDatabase = AnimeRoomDatabase.getDatabase(context);
        animeDao = animeRoomDatabase.userDao();

        List<Anime> favAnimeList = animeDao.getAllFavAnime();

        for (int i = 0; i < favAnimeList.size(); i++) {
            favAnimeIdList.add(favAnimeList.get(i).getmMalId());
        }
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.anime_list_item, parent, false);
        return new AnimeViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final AnimeViewHolder holder, final int position) {
        holder.bindTo(getItem(position));
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
            if (anime != null) {
                titleView.setText(anime.getmTitle());
                scoreView.setText(String.valueOf(anime.getmScore()));
                typeView.setText("Type: " + anime.getmType());
                episodesView.setText("Episodes: " + String.valueOf(anime.getmEpisodes()));

                Picasso picasso = Picasso.with(context);
                picasso.load(anime.getmImageUrl()).into(posterView);

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
                                    animeDao.deleteFavAnime(anime);
                                    return null;
                                }
                            }.execute();
                            animeFavView.setImageResource(R.drawable.ic_favorite_border);
                        } else {
                            favAnimeIdList.add(anime.getmMalId());
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    animeDao.insertFavAnime(anime);
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
            } else {
                titleView.setText(R.string.loading);
                typeView.setText(R.string.loading);
            }
        }
    }

    private static final DiffUtil.ItemCallback<Anime> DIFF_CALLBACK = new DiffUtil.ItemCallback<Anime>() {

        @Override
        public boolean areItemsTheSame(Anime oldItem, Anime newItem) {
            return (oldItem.getmMalId() == newItem.getmMalId());
        }

        @Override
        public boolean areContentsTheSame(Anime oldItem, Anime newItem) {
            return true;
        }
    };
}
