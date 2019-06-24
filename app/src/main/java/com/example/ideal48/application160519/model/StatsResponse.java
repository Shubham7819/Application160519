package com.example.ideal48.application160519.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatsResponse {

    @SerializedName("watching")
    private int mWatchingCount;

    @SerializedName("completed")
    private int mCompletedCount;

    @SerializedName("on_hold")
    private int mOnHoldCount;

    @SerializedName("dropped")
    private int mDroppedCount;

    @SerializedName("plan_to_watch")
    private int mPlanToWatchCount;

    @SerializedName("total")
    private int mTotalCount;

    @SerializedName("scores")
    private Score mScoresList;

    public int getmWatchingCount() {
        return mWatchingCount;
    }

    public int getmCompletedCount() {
        return mCompletedCount;
    }

    public int getmOnHoldCount() {
        return mOnHoldCount;
    }

    public int getmDroppedCount() {
        return mDroppedCount;
    }

    public int getmPlanToWatchCount() {
        return mPlanToWatchCount;
    }

    public int getmTotalCount() {
        return mTotalCount;
    }

    public Score getmScoresList() {
        return mScoresList;
    }

    public class AnimeScore {

        @SerializedName("votes")
        private int mVotes;

        @SerializedName("percentage")
        private double mPercentage;

        public int getmVotes() {
            return mVotes;
        }

        public double getmPercentage() {
            return mPercentage;
        }
    }

    public class Score {

        @SerializedName("1")
        private AnimeScore mScore1;

        @SerializedName("2")
        private AnimeScore mScore2;

        @SerializedName("3")
        private AnimeScore mScore3;

        @SerializedName("4")
        private AnimeScore mScore4;

        @SerializedName("5")
        private AnimeScore mScore5;

        @SerializedName("6")
        private AnimeScore mScore6;

        @SerializedName("7")
        private AnimeScore mScore7;

        @SerializedName("8")
        private AnimeScore mScore8;

        @SerializedName("9")
        private AnimeScore mScore9;

        @SerializedName("10")
        private AnimeScore mScore10;

        public AnimeScore getmScore1() {
            return mScore1;
        }

        public AnimeScore getmScore2() {
            return mScore2;
        }

        public AnimeScore getmScore3() {
            return mScore3;
        }

        public AnimeScore getmScore4() {
            return mScore4;
        }

        public AnimeScore getmScore5() {
            return mScore5;
        }

        public AnimeScore getmScore6() {
            return mScore6;
        }

        public AnimeScore getmScore7() {
            return mScore7;
        }

        public AnimeScore getmScore8() {
            return mScore8;
        }

        public AnimeScore getmScore9() {
            return mScore9;
        }

        public AnimeScore getmScore10() {
            return mScore10;
        }
    }
}
