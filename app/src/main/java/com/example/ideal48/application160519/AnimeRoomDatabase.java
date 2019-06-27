package com.example.ideal48.application160519;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.ideal48.application160519.model.Anime;

/**
 * Created by ideal48 on 16/5/19.
 */

@Database(entities = {Anime.class}, version = 1)
public abstract class AnimeRoomDatabase extends RoomDatabase {

    public abstract AnimeDao userDao();

    private static volatile AnimeRoomDatabase INSTANCE;

    public static AnimeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AnimeRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AnimeRoomDatabase.class, "anime_database").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
