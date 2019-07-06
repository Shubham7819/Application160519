package com.example.ideal48.application160519.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.v7.util.DiffUtil;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "fav_animes_table")   //, indices = {@Index(value = {"mal_id"}, unique = true)}
public class Anime {

    @PrimaryKey(autoGenerate = true)
    public int mId;

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

    @ColumnInfo(name = "fav")
    public boolean isFav;

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public boolean isFav() {
        return isFav;
    }

    @ColumnInfo(name = "tab_type")
    public String mTabType;

    public void setmTabType(String mTabType) {
        this.mTabType = mTabType;
    }

    public static DiffUtil.ItemCallback<Anime> DIFF_CALLBACK = new DiffUtil.ItemCallback<Anime>() {

        @Override
        public boolean areItemsTheSame(Anime oldItem, Anime newItem) {
            return (oldItem.getmMalId() == newItem.getmMalId());
        }

        @Override
        public boolean areContentsTheSame(Anime oldItem, Anime newItem) {
            return (oldItem.getmMalId() == newItem.getmMalId());
        }
    };

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

}
