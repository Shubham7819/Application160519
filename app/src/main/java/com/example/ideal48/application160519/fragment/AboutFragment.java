package com.example.ideal48.application160519.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ideal48.application160519.R;

import static com.example.ideal48.application160519.activity.HomeActivity.toolbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    View rootView;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v("AboutFragment.java", "OnCreate called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile_layout, container, false);

        Log.v("AboutFragment.java", "OnCreateView called");

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        toolbar.setTitle(R.string.about);
    }
}
