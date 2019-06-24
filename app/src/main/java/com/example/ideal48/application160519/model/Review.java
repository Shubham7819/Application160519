package com.example.ideal48.application160519.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("helpful_count")
    private int mHelpfulCount;

    @SerializedName("date")
    private String mReviewDate;

    @SerializedName("reviewer")
    private Reviewer mReviewer;

    @SerializedName("content")
    private String mReviewText;

    public class Reviewer {

        @SerializedName("image_url")
        private String mReviewerImageUrl;

        @SerializedName("username")
        private String mReviewerName;

        @SerializedName("episodes_seen")
        private int mEpisodesSeen;

        @SerializedName("scores")
        private Scores mScores;

        public String getmReviewerImageUrl() {
            return mReviewerImageUrl;
        }

        public String getmReviewerName() {
            return mReviewerName;
        }

        public int getmEpisodesSeen() {
            return mEpisodesSeen;
        }

        public Scores getmScores() {
            return mScores;
        }
    }

    public class Scores {
        @SerializedName("overall")
        private int mOverallScore;

        public int getmOverallScore() {
            return mOverallScore;
        }
    }

    public int getmHelpfulCount() {
        return mHelpfulCount;
    }

    public String getmReviewDate() {
        return mReviewDate;
    }

    public Reviewer getmReviewer() {
        return mReviewer;
    }

    public String getmReviewText() {
        return mReviewText;
    }
}
