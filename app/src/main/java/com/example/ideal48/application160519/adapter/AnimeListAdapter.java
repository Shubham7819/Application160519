package com.example.ideal48.application160519.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ideal48.application160519.UserDao;
import com.example.ideal48.application160519.UserRoomDatabase;
import com.example.ideal48.application160519.activity.AnimeDetailsActivity;
import com.example.ideal48.application160519.model.Anime;
import com.example.ideal48.application160519.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AnimeListAdapter extends RecyclerView.Adapter<AnimeListAdapter.AnimeViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private List<Anime> mAnimeList;
    UserDao userDao;
    List<Anime> favAnimeList = new ArrayList<>();
    List<Integer> favAnimeIdList = new ArrayList<>();

    public AnimeListAdapter(Context context, List<Anime> animeList) {
        if (context != null) {
            mInflater = LayoutInflater.from(context);
            this.context = context;UserRoomDatabase userRoomDatabase = UserRoomDatabase.getDatabase(context);
            userDao = userRoomDatabase.userDao();
            favAnimeList = userDao.getAllFavAnime();
            for (int i = 0; i < favAnimeList.size(); i++) {
                favAnimeIdList.add((Integer) favAnimeList.get(i).getmMalId());
            }
        }
        mAnimeList = animeList;
    }

    @Override
    public AnimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.anime_list_item, parent, false);
        return new AnimeViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(final AnimeViewHolder holder, final int position) {
        final Anime mCurrentAnime = mAnimeList.get(position);
        holder.titleView.setText(mCurrentAnime.getmTitle());
        holder.scoreView.setText(String.valueOf(mCurrentAnime.getmScore()));
        holder.typeView.setText("Type: " + mCurrentAnime.getmType());
        holder.episodesView.setText("Episodes: " + String.valueOf(mCurrentAnime.getmEpisodes()));

        Picasso picasso = Picasso.with(context);
        picasso.load(mCurrentAnime.getmImageUrl()).into(holder.posterView);

        if (favAnimeIdList.contains((Integer) mCurrentAnime.getmMalId())) {
            holder.animeFavView.setImageResource(R.drawable.ic_favorite);
        } else {
            holder.animeFavView.setImageResource(R.drawable.ic_favorite_border);
        }

        holder.animeFavView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favAnimeIdList.contains((Integer) mCurrentAnime.getmMalId())) {
                    favAnimeIdList.remove((Integer) mCurrentAnime.getmMalId());
                    new AsyncTask<Void, Void, Void>(){
                        @Override
                        protected Void doInBackground(Void... voids) {
                            userDao.deleteFavAnime(mCurrentAnime);
                            return null;
                        }
                    }.execute();
                    holder.animeFavView.setImageResource(R.drawable.ic_favorite_border);
                } else {
                    favAnimeIdList.add((Integer) mCurrentAnime.getmMalId());
                    new AsyncTask<Void, Void, Void>(){
                        @Override
                        protected Void doInBackground(Void... voids) {
                            userDao.insertFavAnime(mCurrentAnime);
                            return null;
                        }
                    }.execute();
                    holder.animeFavView.setImageResource(R.drawable.ic_favorite);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int malId = mCurrentAnime.getmMalId();
                Intent i = new Intent(context, AnimeDetailsActivity.class);
                i.putExtra("malId", malId);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAnimeList.size();
    }

    public class AnimeViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView typeView;
        TextView episodesView;
        TextView scoreView;
        ImageView posterView;
        ImageView animeFavView;

        public AnimeViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.anime_title_tv);
            typeView = itemView.findViewById(R.id.anime_type_tv);
            episodesView = itemView.findViewById(R.id.anime_episodes_tv);
            scoreView = itemView.findViewById(R.id.anime_score_tv);
            posterView = itemView.findViewById(R.id.anime_poster_iv);
            animeFavView = itemView.findViewById(R.id.anime_fav_iv);
        }
    }
}
