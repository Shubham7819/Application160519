package com.example.ideal48.application160519.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.AnimeDao;
import com.example.ideal48.application160519.AnimeRoomDatabase;
import com.example.ideal48.application160519.adapter.FavAnimeListAdapter;
import com.example.ideal48.application160519.model.Anime;

import java.util.ArrayList;
import java.util.List;

import static com.example.ideal48.application160519.activity.HomeActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private TextView mEmptyListTV;
    private RecyclerView mRecyclerView;
    View loadingIndicator;
    AnimeDao animeDao;

    List<Anime> favAnimeList = new ArrayList<>();


    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);

        loadingIndicator = view.findViewById(R.id.list_loading_indicator);
        mEmptyListTV = view.findViewById(R.id.list_empty_view);
        mRecyclerView = view.findViewById(R.id.list_recycler_view);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        AnimeRoomDatabase animeRoomDatabase = AnimeRoomDatabase.getDatabase(getActivity());
        animeDao = animeRoomDatabase.animeDao();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        toolbar.setTitle(R.string.favorite);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {

                favAnimeList = animeDao.getAllFavAnime();
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if (favAnimeList == null || favAnimeList.size() == 0) {
                    loadingIndicator.setVisibility(View.GONE);
                    mRecyclerView.removeAllViewsInLayout();
                    mEmptyListTV.setText(R.string.fav_list_empty);
                } else {
                    FavAnimeListAdapter mAdapter = new FavAnimeListAdapter(getActivity(), favAnimeList);
                    loadingIndicator.setVisibility(View.GONE);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            }
        }.execute();
    }
}
