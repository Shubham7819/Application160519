package com.example.ideal48.application160519.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ideal48.application160519.fragment.HostFragment;
import com.example.ideal48.application160519.fragment.LoginFragment;
import com.example.ideal48.application160519.fragment.SignUpFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ideal48 on 16/5/19.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> tabs = new ArrayList<>();

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
//        if (position == 0) {
//            return new LoginFragment();
//        } else {
//            return new SignUpFragment();
//        }
    }

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        initializeTabs();
    }

    private void initializeTabs() {
        tabs.add(HostFragment.newInstance(new LoginFragment()));
        tabs.add(HostFragment.newInstance(new SignUpFragment()));
    }

}
