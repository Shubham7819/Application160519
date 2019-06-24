package com.example.ideal48.application160519.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "fav_animes_table", indices = {@Index(value = {"mal_id"}, unique = true)})
public class Anime {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "mal_id")
    @SerializedName("mal_id")
    public int mMalId;

    @Ignore
    @SerializedName("rank")
    private int mRank;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    public String mTitle;

    @ColumnInfo(name = "image_url")
    @SerializedName("image_url")
    public String mImageUrl;

    @ColumnInfo(name = "type")
    @SerializedName("type")
    public String mType;

    @ColumnInfo(name = "episodes")
    @SerializedName("episodes")
    public int mEpisodes;

    @Ignore
    @SerializedName("members")
    private int mMembers;

    @ColumnInfo(name = "score")
    @SerializedName("score")
    public double mScore;

    public int getmMalId() {
        return mMalId;
    }

    public int getmRank() {
        return mRank;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmType() {
        return mType;
    }

    public int getmEpisodes() {
        return mEpisodes;
    }

    public int getmMembers() {
        return mMembers;
    }

    public double getmScore() {
        return mScore;
    }

    public Anime(int mMalId, String mTitle, String mImageUrl, String mType, int mEpisodes, double mScore) {
        this.mMalId = mMalId;
        this.mTitle = mTitle;
        this.mImageUrl = mImageUrl;
        this.mType = mType;
        this.mEpisodes = mEpisodes;
        this.mScore = mScore;
    }

    public Anime() {
    }

    Anime(int malId, int rank, String title, String imageUrl, String type,
          int episodes, int members, double score) {
        mMalId = malId;
        mRank = rank;
        mTitle = title;
        mImageUrl = imageUrl;
        mType = type;
        mEpisodes = episodes;
        mMembers = members;
        mScore = score;
        mImageUrl = imageUrl;
    }

}
