package com.example.ideal48.application160519;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.example.ideal48.application160519.model.Anime;

import rx.subjects.ReplaySubject;

public class AnimeDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<AnimeDataSource> networkStatus;
    AnimeDataSource animeDataSource;

    public AnimeDataSourceFactory() {
        networkStatus = new MutableLiveData<>();
     //   animeDataSource = new AnimeDataSource("upcoming");
    }

    @Override
    public DataSource create() {
        networkStatus.postValue(animeDataSource);
        return animeDataSource;
    }

    public MutableLiveData<AnimeDataSource> getNetworkStatus() {
        return networkStatus;
    }
}
