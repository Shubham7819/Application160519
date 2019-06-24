package com.example.ideal48.application160519.model;

import com.google.gson.annotations.SerializedName;

public class EpisodesVideos {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("episode")
    private String mEpisode;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("image_url")
    private String mImageUrl;

    public String getmTitle() {
        return mTitle;
    }

    public String getmEpisode() {
        return mEpisode;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }
}
