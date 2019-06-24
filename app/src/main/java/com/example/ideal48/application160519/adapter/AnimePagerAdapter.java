package com.example.ideal48.application160519.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.ideal48.application160519.fragment.CommonAnimeFragment;
import com.example.ideal48.application160519.R;

public class AnimePagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public AnimePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Log.e("Position",">>>>>>>>>>>>>>>>>>>>>>>>>"+position);

        if (position == 0) {
            Bundle b0 = new Bundle();
            b0.putString(mContext.getString(R.string.url), "all");
            CommonAnimeFragment fragmentAll = new CommonAnimeFragment();
            fragmentAll.setArguments(b0);
            return fragmentAll;
        } else if (position == 1) {
            Bundle b1 = new Bundle();
            b1.putString(mContext.getString(R.string.url), "airing");
            CommonAnimeFragment fragmentAiring = new CommonAnimeFragment();
            fragmentAiring.setArguments(b1);
            return fragmentAiring;
        } else if (position == 2) {
            Bundle b2 = new Bundle();
            b2.putString(mContext.getString(R.string.url), "upcoming");
            CommonAnimeFragment fragmentUpcoming = new CommonAnimeFragment();
            fragmentUpcoming.setArguments(b2);
            return fragmentUpcoming;
        } else if (position == 3) {
            Bundle b = new Bundle();
            b.putString(mContext.getString(R.string.url), "tv");
            CommonAnimeFragment fragment = new CommonAnimeFragment();
            fragment.setArguments(b);
            return fragment;
        } else if (position == 4) {
            Bundle b = new Bundle();
            b.putString(mContext.getString(R.string.url), "movie");
            CommonAnimeFragment fragment = new CommonAnimeFragment();
            fragment.setArguments(b);
            return fragment;
        } else if (position == 5) {
            Bundle b = new Bundle();
            b.putString(mContext.getString(R.string.url), "ova");
            CommonAnimeFragment fragment = new CommonAnimeFragment();
            fragment.setArguments(b);
            return fragment;
        } else if (position == 6) {
            Bundle b = new Bundle();
            b.putString(mContext.getString(R.string.url), "special");
            CommonAnimeFragment fragment = new CommonAnimeFragment();
            fragment.setArguments(b);
            return fragment;
        } else if (position == 7) {
            Bundle b = new Bundle();
            b.putString(mContext.getString(R.string.url), "bypopularity");
            CommonAnimeFragment fragment = new CommonAnimeFragment();
            fragment.setArguments(b);
            return fragment;
        } else {
            Bundle b = new Bundle();
            b.putString(mContext.getString(R.string.url), "favorite");
            CommonAnimeFragment fragment = new CommonAnimeFragment();
            fragment.setArguments(b);
            return fragment;
        }
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.all);
        } else if (position == 1) {
            return mContext.getString(R.string.airing);
        } else if (position == 2) {
            return mContext.getString(R.string.upcoming);
        } else if (position == 3) {
            return mContext.getString(R.string.tv);
        } else if (position == 4) {
            return mContext.getString(R.string.movie);
        } else if (position == 5) {
            return mContext.getString(R.string.ova);
        } else if (position == 6) {
            return mContext.getString(R.string.special);
        } else if (position == 7) {
            return mContext.getString(R.string.bypopularity);
        } else {
            return mContext.getString(R.string.favorite);
        }
    }
}
