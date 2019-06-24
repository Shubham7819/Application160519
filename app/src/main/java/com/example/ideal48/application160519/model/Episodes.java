package com.example.ideal48.application160519.model;

import com.google.gson.annotations.SerializedName;

public class Episodes {

    @SerializedName("episode_id")
    private int mEpisodeId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("title_japanese")
    private String mTitleJap;

    @SerializedName("title_romanji")
    private String mTitleRomanji;

    @SerializedName("aired")
    private String mAired;

    @SerializedName("video_url")
    private String mVideoUrl;

    public String getmVideoUrl() {
        return mVideoUrl;
    }

    public int getmEpisodeId() {
        return mEpisodeId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmTitleJap() {
        return mTitleJap;
    }

    public String getmTitleRomanji() {
        return mTitleRomanji;
    }

    public String getmAired() {
        return mAired;
    }
}
