package com.example.ideal48.application160519.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {

    @SerializedName("articles")
    private List<News> mNewsList;

    public List<News> getmNewsList() {
        return mNewsList;
    }

    public class News {
        @SerializedName("title")
        private String mTitle;

        @SerializedName("date")
        private String mDate;

        @SerializedName("author_name")
        private String mAuthorName;

        @SerializedName("image_url")
        private String mImageUrl;

        @SerializedName("intro")
        private String mIntro;

        public String getmTitle() {
            return mTitle;
        }

        public String getmDate() {
            return mDate;
        }

        public String getmAuthorName() {
            return mAuthorName;
        }

        public String getmImageUrl() {
            return mImageUrl;
        }

        public String getmIntro() {
            return mIntro;
        }
    }
}
