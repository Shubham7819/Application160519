package com.example.ideal48.application160519.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ideal48.application160519.fragment.DetailsFragment;
import com.example.ideal48.application160519.fragment.EpisodesFragment;
import com.example.ideal48.application160519.fragment.ForumFragment;
import com.example.ideal48.application160519.fragment.NewsFragment;
import com.example.ideal48.application160519.fragment.PicturesFragment;
import com.example.ideal48.application160519.fragment.RecommendationsFragment;
import com.example.ideal48.application160519.fragment.ReviewsFragment;
import com.example.ideal48.application160519.fragment.StatsFragment;
import com.example.ideal48.application160519.fragment.VideosFragment;

public class AnimeDetailsPagerAdapter extends FragmentPagerAdapter {

    Context mContext;

    public AnimeDetailsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new DetailsFragment();
        } else if (position == 1){
            return new VideosFragment();
        } else if (position == 2){
            return new EpisodesFragment();
        } else if (position == 3){
            return new ReviewsFragment();
        } else if (position == 4){
            return new RecommendationsFragment();
        } else if (position == 5){
            return new StatsFragment();
        } else if (position == 6){
            return new NewsFragment();
        } else if (position == 7){
            return new ForumFragment();
        } else {
            return new PicturesFragment();
        }
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return "Details";
        } else if (position == 1){
            return "Videos";
        } else if (position == 2){
            return "Episodes";
        } else if (position == 3){
            return "Reviews";
        } else if (position == 4){
            return "recommendations";
        } else if (position == 5){
            return "stats";
        } else if (position == 6){
            return "news";
        } else if (position == 7){
            return "forum";
        } else {
            return "pictures";
        }
    }
}
