package com.example.ideal48.application160519.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForumResponse {

    @SerializedName("topics")
    private List<Topic> mTopicList;

    public List<Topic> getmTopicList() {
        return mTopicList;
    }

    public class Topic {

        @SerializedName("title")
        private String mTitle;

        @SerializedName("date_posted")
        private String mDatePosted;

        @SerializedName("author_name")
        private String mAuthorName;

        @SerializedName("replies")
        private int mRepliesCount;

        @SerializedName("last_post")
        private LastPost mLastPost;

        public String getmTitle() {
            return mTitle;
        }

        public String getmDatePosted() {
            return mDatePosted;
        }

        public String getmAuthorName() {
            return mAuthorName;
        }

        public int getmRepliesCount() {
            return mRepliesCount;
        }

        public LastPost getmLastPost() {
            return mLastPost;
        }
    }

    public class LastPost {
        @SerializedName("author_name")
        private String authorName;

        @SerializedName("date_posted")
        private String datePosted;

        public String getAuthorName() {
            return authorName;
        }

        public String getDatePosted() {
            return datePosted;
        }
    }
}
