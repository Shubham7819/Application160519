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

//    List<User> usersList;
    RecyclerView mRecyclerView;
    UserDao userDao;
//    String userId;

//    GetDataService service;

//    int[] favAnimeIDList;
    List<Anime> favAnimeList = new ArrayList<>();

//    ProgressDialog progressDialog;
    View loadingIndicator;
    private TextView mEmptyListTV;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);
        loadingIndicator = view.findViewById(R.id.list_loading_indicator);
        mEmptyListTV = view.findViewById(R.id.list_empty_view);

//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();

        UserRoomDatabase userRoomDatabase = UserRoomDatabase.getDatabase(getActivity());
        userDao = userRoomDatabase.userDao();

//        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

//        userId = new AppSession(getActivity()).getLoginId();

        mRecyclerView = view.findViewById(R.id.recycler_view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
//                usersList = userDao.getFavUsers(userId, false);
//                favAnimeIDList = userDao.getAllFavAnime();
                favAnimeList = userDao.getAllFavAnime();
//                for (int i = 0; i < favAnimeIDList.length; i++){
//                    Call<AnimeDetails> call = service.getAnimeDetails(favAnimeIDList[i]);
//                    call.enqueue(new Callback<AnimeDetails>() {
//                        @Override
//                        public void onResponse(Call<AnimeDetails> call, Response<AnimeDetails> response) {
//                            if (response.body() != null) {
//                                favAnimeList.add(response.body());
//                            } else {
//                                Toast.makeText(getActivity(), "No Data..", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<AnimeDetails> call, Throwable t) {
//                            Toast.makeText(getActivity(), "details of anime not found.. ", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
//                UserListAdapter mAdapter = new UserListAdapter(getActivity(), usersList);
//                progressDialog.dismiss();
                if (favAnimeList == null || favAnimeList.size() == 0){
                    loadingIndicator.setVisibility(View.GONE);
                    mRecyclerView.removeAllViewsInLayout();
                    mEmptyListTV.setText("Favorite List is Empty !");
                } else {
                    AnimeListAdapter mAdapter = new AnimeListAdapter(getActivity(), favAnimeList);
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
