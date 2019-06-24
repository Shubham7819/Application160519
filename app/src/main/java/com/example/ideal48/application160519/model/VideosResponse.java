package com.example.ideal48.application160519.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideosResponse {

    @SerializedName("promo")
    private List<PromoVideos> mPromoList;

    @SerializedName("episodes")
    private List<EpisodesVideos> mEpisodesList;

    public List<EpisodesVideos> getmEpisodesList() {
        return mEpisodesList;
    }

    public List<PromoVideos> getmPromoList() {
        return mPromoList;
    }
}
