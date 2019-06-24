package com.example.ideal48.application160519.model;

import com.google.gson.annotations.SerializedName;

public class AnimeDetails {

    @SerializedName("mal_id")
    private int mMalId;

    public int getmMalId() {
        return mMalId;
    }

    @SerializedName("image_url")
    private String mImageUrl;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("title_english")
    private String mTitleEng;

    @SerializedName("title_japanese")
    private String mTitleJap;

    @SerializedName("title_synonyms")
    private String mTitleSyno;

    @SerializedName("type")
    private String mType;

    @SerializedName("source")
    private String mSource;

    @SerializedName("episodes")
    private int mEpisodes;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("duration")
    private String mDuration;

    @SerializedName("score")
    private double mScore;

    @SerializedName("scored_by")
    private int mScoredBy;

    @SerializedName("rank")
    private int mRank;

    @SerializedName("popularity")
    private int mPopularity;

    @SerializedName("members")
    private int mMembers;

    @SerializedName("favorites")
    private int mFavorites;

    @SerializedName("synopsis")
    private String mSynopsis;

    @SerializedName("background")
    private String mBackground;

    @SerializedName("premiered")
    private String mPremiered;

    @SerializedName("broadcast")
    private String mBroadcast;

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmTitleEng() {
        return mTitleEng;
    }

    public String getmTitleJap() {
        return mTitleJap;
    }

    public String getmTitleSyno() {
        return mTitleSyno;
    }

    public String getmType() {
        return mType;
    }

    public String getmSource() {
        return mSource;
    }

    public int getmEpisodes() {
        return mEpisodes;
    }

    public String getmStatus() {
        return mStatus;
    }

    public String getmDuration() {
        return mDuration;
    }

    public double getmScore() {
        return mScore;
    }

    public int getmScoredBy() {
        return mScoredBy;
    }

    public int getmRank() {
        return mRank;
    }

    public int getmPopularity() {
        return mPopularity;
    }

    public int getmMembers() {
        return mMembers;
    }

    public int getmFavorites() {
        return mFavorites;
    }

    public String getmSynopsis() {
        return mSynopsis;
    }

    public String getmBackground() {
        return mBackground;
    }

    public String getmPremiered() {
        return mPremiered;
    }

    public String getmBroadcast() {
        return mBroadcast;
    }
}
