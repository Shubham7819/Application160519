package com.example.ideal48.application160519.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecommendationsResponse {

    @SerializedName("recommendations")
    private List<Recommendations> mRecommendationsList;

    public List<Recommendations> getmRecommendationsList() {
        return mRecommendationsList;
    }

    public class Recommendations {

        @SerializedName("mal_id")
        private int mMalId;

        public int getmMalId() {
            return mMalId;
        }

        @SerializedName("image_url")
        private String mImageUrl;

        @SerializedName("title")
        private String mTitle;

        @SerializedName("recommendation_count")
        private int mRecommendationCount;

        public String getmImageUrl() {
            return mImageUrl;
        }

        public String getmTitle() {
            return mTitle;
        }

        public int getmRecommendationCount() {
            return mRecommendationCount;
        }
    }
}
