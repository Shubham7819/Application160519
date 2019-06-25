package com.example.ideal48.application160519.fragment;


import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.example.ideal48.application160519.AppSession;
import com.example.ideal48.application160519.GetDataService;
import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.RetrofitClientInstance;
import com.example.ideal48.application160519.UserDao;
import com.example.ideal48.application160519.adapter.AnimeListAdapter;
import com.example.ideal48.application160519.adapter.FavAnimeListAdapter;
import com.example.ideal48.application160519.adapter.FavListAdapter;
import com.example.ideal48.application160519.adapter.UserListAdapter;
import com.example.ideal48.application160519.UserRoomDatabase;
import com.example.ideal48.application160519.model.Anime;
import com.example.ideal48.application160519.model.AnimeDetails;
import com.example.ideal48.application160519.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private TextView mEmptyListTV;
    RecyclerView mRecyclerView;
    View loadingIndicator;
    UserDao userDao;

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
        mRecyclerView = view.findViewById(R.id.recycler_view);

        UserRoomDatabase userRoomDatabase = UserRoomDatabase.getDatabase(getActivity());
        userDao = userRoomDatabase.userDao();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {

                favAnimeList = userDao.getAllFavAnime();
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if (favAnimeList == null || favAnimeList.size() == 0){
                    loadingIndicator.setVisibility(View.GONE);
                    mRecyclerView.removeAllViewsInLayout();
                    mEmptyListTV.setText("Favorite List is Empty !");
                } else {
                    FavAnimeListAdapter mAdapter = new FavAnimeListAdapter(getActivity(), favAnimeList);
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                    loadingIndicator.setVisibility(View.GONE);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            }
        }.execute();
    }
}
