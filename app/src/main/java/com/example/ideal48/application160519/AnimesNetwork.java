package com.example.ideal48.application160519;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.example.ideal48.application160519.model.Anime;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AnimesNetwork {

    final private LiveData<PagedList<Anime>> animesPaged;
    final private LiveData<NetworkState> networkState;

    public AnimesNetwork(AnimeDataSourceFactory dataSourceFactory, PagedList.BoundaryCallback<Anime> boundaryCallback) {

        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20).setPageSize(20).setPrefetchDistance(40).build();

        networkState = Transformations.switchMap(dataSourceFactory.getNetworkStatus(),
                (Function<AnimeDataSource, LiveData<NetworkState>>)
                        AnimeDataSource::getNetworkState);

        Executor executor = Executors.newFixedThreadPool(3);

        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);

        animesPaged = livePagedListBuilder.
                setFetchExecutor(executor).
                setBoundaryCallback(boundaryCallback).
                build();

    }

    public LiveData<PagedList<Anime>> getPagedAnimes() {
        return animesPaged;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
