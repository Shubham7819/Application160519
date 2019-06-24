package com.example.ideal48.application160519.model;

import com.example.ideal48.application160519.model.Anime;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeResponse {

    @SerializedName("request_hash")
    private String mRequestHash;

    @SerializedName("request_cached")
    private boolean mRequestCached;

    @SerializedName("request_cache_expiry")
    private int mRequestCacheExpiry;

    @SerializedName("top")
    private List<Anime> mTop;

    public void setmRequestHash(String mRequestHash) {
        this.mRequestHash = mRequestHash;
    }

    public void setmRequestCached(boolean mRequestCached) {
        this.mRequestCached = mRequestCached;
    }

    public void setmRequestCacheExpiry(int mRequestCacheExpiry) {
        this.mRequestCacheExpiry = mRequestCacheExpiry;
    }

    public void setmTop(List<Anime> mTop) {
        this.mTop = mTop;
    }

    public String getmRequestHash() {
        return mRequestHash;
    }

    public boolean ismRequestCached() {
        return mRequestCached;
    }

    public int getmRequestCacheExpiry() {
        return mRequestCacheExpiry;
    }

    public List<Anime> getmTop() {
        return mTop;
    }
}
