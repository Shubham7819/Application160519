package com.example.ideal48.application160519;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.ideal48.application160519.model.Anime;

import java.util.List;

/**
 * Created by ideal48 on 16/5/19.
 */

@Dao
public interface AnimeDao {

    @Query("SELECT * FROM fav_animes_table WHERE mal_id = :malId AND fav = 'true'")
    Anime isAnimeFav(int malId);

    @Query("UPDATE fav_animes_table SET fav = 'true' WHERE mal_id = :malId")
    void setAnimeFav(int malId);

    @Query("UPDATE fav_animes_table SET fav = 'false' WHERE mal_id = :malId")
    void deleteFavAnime(int malId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAnime(Anime anime);

    @Query("SELECT * FROM fav_animes_table WHERE tab_type = :tabType ORDER BY score DESC")
    List<Anime> getAllCachedAnime(String tabType);

    @Query("DELETE FROM fav_animes_table")
    abstract void deleteAllAnimes();

    @Query("SELECT * FROM fav_animes_table WHERE fav = 'true' GROUP BY mal_id")
    List<Anime> getAllFavAnime();
}
