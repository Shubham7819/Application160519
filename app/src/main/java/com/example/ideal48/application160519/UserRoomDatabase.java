package com.example.ideal48.application160519;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.ideal48.application160519.model.Anime;
import com.example.ideal48.application160519.model.User;

/**
 * Created by ideal48 on 16/5/19.
 */

@Database(entities = {User.class, Anime.class}, version = 1)
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    private static volatile UserRoomDatabase INSTANCE;

    public static UserRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserRoomDatabase.class, "user_database").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
