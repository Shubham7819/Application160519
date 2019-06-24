package com.example.ideal48.application160519.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PicturesResponse {

    @SerializedName("pictures")
    private List<Pictures> mPicturesList;

    public List<Pictures> getmPicturesList() {
        return mPicturesList;
    }

    public class Pictures {
        public String getmSmall() {
            return mSmall;
        }

        @SerializedName("small")
        private String mSmall;

        @SerializedName("large")
        private String mLarge;

        public String getmLarge() {
            return mLarge;
        }
    }
}
