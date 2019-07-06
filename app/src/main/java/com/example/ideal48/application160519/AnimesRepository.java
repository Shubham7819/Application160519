package com.example.ideal48.application160519;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.paging.PagedList;
import android.content.Context;
import android.util.Log;

import com.example.ideal48.application160519.model.Anime;

import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class AnimesRepository {

    private static final String TAG = AnimesRepository.class.getSimpleName();
    private static AnimesRepository instance;
    final private AnimesNetwork network;
    final private AnimeRoomDatabase database;
    final private MediatorLiveData liveDataMerger;

    public AnimesRepository(Context context) {

        AnimeDataSourceFactory dataSourceFactory = new AnimeDataSourceFactory();

        network = new AnimesNetwork(dataSourceFactory, boundaryCallback);
        database = AnimeRoomDatabase.getDatabase(context.getApplicationContext());

        // when we get new movies from net we set them into the database
        liveDataMerger = new MediatorLiveData<>();

        liveDataMerger.addSource(network.getPagedAnimes(), value -> {
            liveDataMerger.setValue(value);
            Log.d(TAG, value.toString());
        });

        // save the movies into db
//        dataSourceFactory.getAnimes().
//                observeOn(Schedulers.io()).
//                subscribe(new Action1<Anime>() {
//                    @Override
//                    public void call(Anime anime) {
//                        database.animeDao().insertFavAnime(anime);
//                    }
//                });

    }

    private PagedList.BoundaryCallback<Anime> boundaryCallback = new PagedList.BoundaryCallback<Anime>() {
        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();

//            liveDataMerger.addSource(database.getAnimes(), value -> {
//                liveDataMerger.setValue(value);
//                liveDataMerger.removeSource(database.getAnimes());
//            });
        }
    };

    public static AnimesRepository getInstance(Context context){
        if(instance == null){
            instance = new AnimesRepository(context);
        }
        return instance;
    }

    public LiveData<PagedList<Anime>> getAnimes(){
        return  liveDataMerger;
    }

    public LiveData<NetworkState> getNetworkState() {
        return network.getNetworkState();
    }

}
