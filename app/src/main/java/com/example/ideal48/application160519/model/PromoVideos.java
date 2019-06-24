package com.example.ideal48.application160519.model;

import com.google.gson.annotations.SerializedName;

public class PromoVideos {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("image_url")
    private String mImageUrl;

    @SerializedName("video_url")
    private String mVideoUrl;

    public String getmTitle() {
        return mTitle;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmVideoUrl() {
        return mVideoUrl;
    }
}
