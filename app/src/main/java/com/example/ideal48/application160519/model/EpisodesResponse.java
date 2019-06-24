package com.example.ideal48.application160519.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EpisodesResponse {

    @SerializedName("episodes")
    private List<Episodes> mEpisodesList;

    public List<Episodes> getmEpisodesList() {
        return mEpisodesList;
    }
}
