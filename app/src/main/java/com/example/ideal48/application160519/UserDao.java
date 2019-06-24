package com.example.ideal48.application160519;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ideal48.application160519.model.Anime;
import com.example.ideal48.application160519.model.FavAnime;
import com.example.ideal48.application160519.model.User;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by ideal48 on 16/5/19.
 */

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM users_table WHERE user_id != :userId ")
    List<User> getAllUsers(String userId);

    //@Query("SELECT password from users_table where user_id LIKE :userId")
    //String getPassword(String userId);

    @Query("SELECT * FROM users_table WHERE user_id = :userId AND password = :password")
    User checkIfUser(String userId, String password);

    @Query("SELECT * FROM users_table WHERE user_id = :userId")
    User getUserDetails(String userId);

    @Query("DELETE FROM users_table WHERE user_id = :userId")
    void delete(String userId);

    @Update
    void updateUserDetails(User user);

    @Query("SELECT * FROM users_table WHERE user_id != :userId AND fav != :b")
    List<User> getFavUsers(String userId, boolean b);

    @Query("UPDATE users_table SET fav = :b WHERE user_id = :userId")
    void setFavUser(String userId, boolean b);

    @Query("UPDATE users_table SET dob = :dob WHERE user_id = :userId")
    void setDOB(String userId, String dob);

    @Query("SELECT * FROM fav_animes_table WHERE mal_id = :malId")
    Anime isAnimeFav(int malId);

    @Delete
    public void deleteFavAnime(Anime anime);

    @Insert
    void insertFavAnime(Anime anime);

    @Query("SELECT * FROM fav_animes_table")
    List<Anime> getAllFavAnime();
}
