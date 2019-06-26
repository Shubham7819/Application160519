package com.example.ideal48.application160519.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.UserDao;
import com.example.ideal48.application160519.UserRoomDatabase;
import com.example.ideal48.application160519.activity.AnimeDetailsActivity;
import com.example.ideal48.application160519.model.Anime;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavAnimeListAdapter extends RecyclerView.Adapter<FavAnimeListAdapter.FavAnimeViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private List<Anime> mAnimeList;
    private UserDao userDao;

    public FavAnimeListAdapter(Context context, List<Anime> animeList) {
        if (context != null) {
            mInflater = LayoutInflater.from(context);
            this.context = context;
            UserRoomDatabase userRoomDatabase = UserRoomDatabase.getDatabase(context);
            userDao = userRoomDatabase.userDao();
        }
        mAnimeList = animeList;
    }

    @NonNull
    @Override
    public FavAnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.anime_list_item, parent, false);
        return new FavAnimeViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavAnimeViewHolder holder, final int position) {
        final Anime mCurrentAnime = mAnimeList.get(position);
        holder.titleView.setText(mCurrentAnime.getmTitle());
        holder.scoreView.setText(String.valueOf(mCurrentAnime.getmScore()));
        holder.typeView.setText("Type: " + mCurrentAnime.getmType());
        holder.episodesView.setText("Episodes: " + String.valueOf(mCurrentAnime.getmEpisodes()));

        Picasso picasso = Picasso.with(context);
        picasso.load(mCurrentAnime.getmImageUrl()).into(holder.posterView);

        holder.animeFavView.setImageResource(R.drawable.ic_favorite);

        holder.animeFavView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        userDao.deleteFavAnime(mCurrentAnime);
                        return null;
                    }
                }.execute();
                mAnimeList.remove(mCurrentAnime);
                notifyDataSetChanged();

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

    class FavAnimeViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView typeView;
        TextView episodesView;
        TextView scoreView;
        ImageView posterView;
        ImageView animeFavView;

        FavAnimeViewHolder(View itemView) {
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
