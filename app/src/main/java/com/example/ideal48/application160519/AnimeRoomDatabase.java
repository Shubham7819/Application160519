package com.example.ideal48.application160519;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.ideal48.application160519.model.Anime;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by ideal48 on 16/5/19.
 */

@Database(entities = {Anime.class}, version = 1)
public abstract class AnimeRoomDatabase extends RoomDatabase {

    public abstract AnimeDao animeDao();
    private PagedList<Anime> animesPaged;
    private static final Object sLock = new Object();

    private static volatile AnimeRoomDatabase INSTANCE;

    public static AnimeRoomDatabase getDatabase(Context context) {
      //  if (INSTANCE == null) {
            synchronized (sLock) { //synchronized (AnimeRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AnimeRoomDatabase.class, "anime_database").allowMainThreadQueries().build();   //
                   // INSTANCE.init();
                }
                return INSTANCE;
            }
     //   }
    }

    private void init() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(Integer.MAX_VALUE).setPageSize(Integer.MAX_VALUE).build();
//        Executor executor = Executors.newFixedThreadPool(3);
        MainThreadExecutor executor = new MainThreadExecutor();
//        DBAnimesDataSourceFactory dataSourceFactory = new DBAnimesDataSourceFactory(animeDao());
//        DBAnimesDataSource dataSource = new DBAnimesDataSource(animeDao());
//        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
//        animesPaged = new PagedList.Builder<>(dataSource, pagedListConfig).setFetchExecutor(executor).setNotifyExecutor(executor).build();
    }

    public PagedList<Anime> getAnimes() {
        Log.v("AnimeRoomDatabase", "returning paged animes" + animesPaged.getLastKey());
        return animesPaged;
    }
}
