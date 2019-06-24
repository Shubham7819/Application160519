package com.example.ideal48.application160519.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.activity.AnimeDetailsActivity;
import com.example.ideal48.application160519.model.Anime;
import com.example.ideal48.application160519.model.AnimeDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavListAdapter extends RecyclerView.Adapter<FavListAdapter.FavViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private List<AnimeDetails> mAnimeList;

    public FavListAdapter(Context context, List<AnimeDetails> mAnimeList) {
        this.context = context;
        this.mAnimeList = mAnimeList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public FavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.anime_list_item, parent, false);
        return new FavViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(FavViewHolder holder, int position) {
        final AnimeDetails mCurrentAnime = mAnimeList.get(position);
        holder.titleView.setText(mCurrentAnime.getmTitle());
        holder.scoreView.setText(String.valueOf(mCurrentAnime.getmScore()));
        holder.typeView.setText(mCurrentAnime.getmType());
        holder.episodesView.setText(String.valueOf(mCurrentAnime.getmEpisodes()));
        Picasso picasso = Picasso.with(context);
        picasso.load(mCurrentAnime.getmImageUrl()).into(holder.posterView);

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

    public class FavViewHolder extends RecyclerView.ViewHolder {

        TextView titleView;
        TextView typeView;
        TextView episodesView;
        TextView scoreView;
        ImageView posterView;

        public FavViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.anime_title_tv);
            typeView = itemView.findViewById(R.id.anime_type_tv);
            episodesView = itemView.findViewById(R.id.anime_episodes_tv);
            scoreView = itemView.findViewById(R.id.anime_score_tv);
            posterView = itemView.findViewById(R.id.anime_poster_iv);
        }
    }
}
