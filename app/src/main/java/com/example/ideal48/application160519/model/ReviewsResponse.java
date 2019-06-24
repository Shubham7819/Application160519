package com.example.ideal48.application160519.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsResponse {

    @SerializedName("reviews")
    private List<Review> mReviewsList;

    public List<Review> getmReviewsList() {
        return mReviewsList;
    }
}
