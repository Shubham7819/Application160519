package com.example.ideal48.application160519.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "fav_animes_table", indices = {@Index(value = {"mal_id"}, unique = true)})
public class FavAnime {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "mal_id")
    public int mMalId;

    public FavAnime(int malId) {
        this.mMalId = malId;
    }

    public int getmMalId() {
        return mMalId;
    }
}
