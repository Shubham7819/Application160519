package com.example.ideal48.application160519.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.fragment.UserDetailsFragment;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        Log.d("UserActivity", "OnCreate called");

        int mId = getIntent().getIntExtra(getString(R.string.id), 0);
        String mUserId = getIntent().getStringExtra(getString(R.string.user_id));
        String mPassword = getIntent().getStringExtra(getString(R.string.password));
        String mFName = getIntent().getStringExtra(getString(R.string.first_name));
        String mLName = getIntent().getStringExtra(getString(R.string.last_name));
        String mDOB = getIntent().getStringExtra(getString(R.string.dob));
        byte[] mImageInByte = getIntent().getByteArrayExtra(getString(R.string.image_bytes));

        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.id), mId);
        bundle.putString(getString(R.string.user_id), mUserId);
        bundle.putString(getString(R.string.password), mPassword);
        bundle.putString(getString(R.string.first_name), mFName);
        bundle.putString(getString(R.string.last_name), mLName);
        bundle.putString(getString(R.string.dob), mDOB);
        bundle.putByteArray(getString(R.string.image_bytes), mImageInByte);


        UserDetailsFragment userDetailsFragment = new UserDetailsFragment();
        userDetailsFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.user_frame_layout,userDetailsFragment);
        ft.commit();
    }
}
