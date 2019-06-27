package com.example.ideal48.application160519;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.ideal48.application160519.model.Anime;

import java.util.List;

/**
 * Created by ideal48 on 16/5/19.
 */

@Dao
public interface AnimeDao {

    @Query("SELECT * FROM fav_animes_table WHERE mal_id = :malId")
    Anime isAnimeFav(int malId);

    @Delete
    void deleteFavAnime(Anime anime);

    @Insert
    void insertFavAnime(Anime anime);

    @Query("SELECT * FROM fav_animes_table")
    List<Anime> getAllFavAnime();
}
