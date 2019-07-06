package com.example.ideal48.application160519.fragment;


import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.paging.PagedList;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideal48.application160519.AnimeDataSource;
import com.example.ideal48.application160519.AnimeRoomDatabase;
import com.example.ideal48.application160519.DBAnimesDataSource;
import com.example.ideal48.application160519.MainThreadExecutor;
import com.example.ideal48.application160519.NetworkState;
import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.adapter.AnimeListAdapter;
import com.example.ideal48.application160519.model.Anime;


public class CommonAnimeFragment extends Fragment {

    View mview;
    View loadingIndicator;
    TextView mEmptyStateTextView;
    RecyclerView animeRecyclerView;
    MediatorLiveData liveDataMerger;
    AnimeRoomDatabase database;
    PagedList<Anime> list;
    LiveData<NetworkState> networkState;
    AnimeListAdapter mAdapter;

    String mAnimeCategory = "";
    private static final String TAG = CommonAnimeFragment.class.getSimpleName();

    public CommonAnimeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mAnimeCategory = getArguments().getString(getString(R.string.url));
            Log.v(TAG, "Category: " + mAnimeCategory);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mview = inflater.inflate(R.layout.anime_list_layout, container, false);

        mEmptyStateTextView = mview.findViewById(R.id.empty_view);
        loadingIndicator = mview.findViewById(R.id.loading_indicator);
        animeRecyclerView = mview.findViewById(R.id.anime_recycler_list);

        animeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        animeRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        liveDataMerger = new MediatorLiveData<>();
        database = AnimeRoomDatabase.getDatabase(getActivity());

        //  observersRegisters();

        return mview;
    }

    private void observersRegisters() {

        final AnimeListAdapter mAdapter = new AnimeListAdapter(getActivity());
//        viewModel.getAnimes().observe(this, mAdapter::submitList);
//        viewModel.getNetworkState().observe(this, networkState -> {
//            mAdapter.setNetworkState(networkState);
//        });
//        repository.getAnimes().observe(this, mAdapter::submitList);
//        repository.getNetworkState().observe(this, networkState -> {
//            mAdapter.setNetworkState(networkState);
//        });
        loadingIndicator.setVisibility(View.GONE);
        animeRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v(TAG, "Category: " + mAnimeCategory + ": onResume Called");

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        MainThreadExecutor executor = new MainThreadExecutor();

        mAdapter = new AnimeListAdapter(getActivity());

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(50).setInitialLoadSizeHint(50)
                .setPrefetchDistance(5)
                .setEnablePlaceholders(true).build();

        PagedList.BoundaryCallback<Anime> boundaryCallback = new PagedList.BoundaryCallback<Anime>() {
            @Override
            public void onZeroItemsLoaded() {
                super.onZeroItemsLoaded();
            }

            @Override
            public void onItemAtEndLoaded(@NonNull Anime itemAtEnd) {
                super.onItemAtEndLoaded(itemAtEnd);
//                Toast.makeText(getActivity(), "Yo1", Toast.LENGTH_SHORT).show();
            }
        };

        PagedList.Callback callback = new PagedList.Callback() {
            @Override
            public void onChanged(int position, int count) {
                Log.v(TAG, "OnChanged called, Position: " + position + " " + count);
            }

            @Override
            public void onInserted(int position, int count) {
                Log.v(TAG, "OnInserted called, Position: " + position + " " + count);
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        for (int i = 0; i < 50; i++) {
                            Anime anime = list.get(i);
                            anime.setmTabType(mAnimeCategory);
                            database.animeDao().insertAnime(list.get(i));
                        }
                        return null;
                    }
                }.execute();
            }

            @Override
            public void onRemoved(int position, int count) {
                Log.v(TAG, "OnRemoved called, Position: " + position + " " + count);
            }
        };

//            animeRecyclerView.setHasFixedSize(true);
//
//        loadingIndicator.setVisibility(View.GONE);
//            animeRecyclerView.setAdapter(mAdapter);
//
        AnimeDataSource dataSource = new AnimeDataSource(getActivity(), mAnimeCategory);

//            AnimeDataSourceFactory dataSourceFactory = new AnimeDataSourceFactory();

        if (!database.animeDao().getAllCachedAnime(mAnimeCategory).isEmpty()) {

            PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(50).setPageSize(50).build();

            DBAnimesDataSource dbDataSource = new DBAnimesDataSource(getActivity(), database.animeDao(), mAnimeCategory);

            MutableLiveData<DBAnimesDataSource> networkStatus = new MutableLiveData<>();

            networkStatus.postValue(dbDataSource);

            networkState = Transformations.switchMap(networkStatus,
                    (Function<DBAnimesDataSource, LiveData<NetworkState>>)
                            DBAnimesDataSource::getNetworkState);

            observe();

            list = new PagedList.Builder<>(dbDataSource, pagedListConfig).setFetchExecutor(executor)
                    .setNotifyExecutor(executor).setBoundaryCallback(boundaryCallback).build();

            // list = database.getAnimes();
            Log.v(TAG, "last item key; " + list.size());
//            Log.v(TAG, "items in db; " + database.animeDao().getAllFavAnime().size());
        } else {

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                list = new PagedList.Builder<>(dataSource, config).setFetchExecutor(executor)
                        .setNotifyExecutor(executor).build();

                list.addWeakCallback(null, callback);

                MutableLiveData<AnimeDataSource> networkStatus = new MutableLiveData<>();

                networkStatus.postValue(dataSource);

                networkState = Transformations.switchMap(networkStatus,
                        (Function<AnimeDataSource, LiveData<NetworkState>>)
                                AnimeDataSource::getNetworkState);
                observe();
            } else {
                mEmptyStateTextView.setText(R.string.no_internet);
            }

        }

////            PagedList<Anime> list = new PagedList.Builder<>(dataSource, config).setFetchExecutor(executor).setNotifyExecutor(executor).build();

//            LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, config);




//        list = livePagedListBuilder.
//                    setFetchExecutor(executor).
//                    setBoundaryCallback(boundaryCallback).
//                    build();

//            list.observe(this, mAdapter::submitList);

        animeRecyclerView.setAdapter(mAdapter);

        loadingIndicator.setVisibility(View.GONE);
        mAdapter.submitList(list);

//            dataSource.getRequestFailureLiveData().observe(this, new Observer<NetworkState>() {
//                @Override
//                public void onChanged(@Nullable final NetworkState requestFailure) {
//                    if (requestFailure == null) return;
//
//                    Snackbar.make(getActivity().getWindow().getDecorView()
//                            .findViewById(android.R.id.content), requestFailure.getMsg(), Snackbar.LENGTH_LONG)
//                            .setAction("RETRY", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    // Retry the failed request
//                                    requestFailure.getRetryable().retry();
//                                    Toast.makeText(getActivity(), requestFailure.getStatus().toString(), Toast.LENGTH_SHORT).show();
//                                }
//                            }).show();
//                }
//            });

//        } else {
//
//            loadingIndicator.setVisibility(View.GONE);
//            mEmptyStateTextView.setText(R.string.no_internet);
//
//            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
//
//        }
    }

    void observe(){
        networkState.observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                mAdapter.setNetworkState(networkState);
            }
        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//    }

}
