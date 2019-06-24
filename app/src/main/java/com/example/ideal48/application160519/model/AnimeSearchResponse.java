package com.example.ideal48.application160519.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeSearchResponse {

    @SerializedName("results")
    private List<SearchResult> mSearchResultsList;

    public List<SearchResult> getmSearchResultsList() {
        return mSearchResultsList;
    }

    public class SearchResult {
        @SerializedName("mal_id")
        private int mMalId;

        @SerializedName("image_url")
        private String mImageUrl;

        @SerializedName("title")
        private String mTitle;

        @SerializedName("synopsis")
        private String mSynopsis;

        @SerializedName("type")
        private String mType;

        @SerializedName("episodes")
        private int mEpisodesCount;

        @SerializedName("score")
        private double mScore;

        public int getmMalId() {
            return mMalId;
        }

        public String getmImageUrl() {
            return mImageUrl;
        }

        public String getmTitle() {
            return mTitle;
        }

        public String getmSynopsis() {
            return mSynopsis;
        }

        public String getmType() {
            return mType;
        }

        public int getmEpisodesCount() {
            return mEpisodesCount;
        }

        public double getmScore() {
            return mScore;
        }
    }
}
