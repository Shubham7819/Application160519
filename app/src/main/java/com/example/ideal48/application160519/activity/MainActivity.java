package com.example.ideal48.application160519.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.example.ideal48.application160519.AppSession;
import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {

//    public static ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.stored_user_id), Context.MODE_PRIVATE);
//        String userId = sharedPref.getString(getString(R.string.user_name), null);
//        Log.v("MainActivity", "user id is " + userId);

        // Get logged in User's Id.
        String userId = new AppSession(this).getLoginId();

        // Check if user is already logged in.
        if (userId == "" && TextUtils.isEmpty(userId)) {
            Log.v("MainActivity", "User in not logged in, inflating log-in screen." + userId);

            // User is not already logged-in inflating log-in screen.
            LoginFragment loginFragment = new LoginFragment();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_activity_frame, loginFragment);
            ft.commit();
//            viewPager = (ViewPager) findViewById(R.id.view_pager);
//            SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
//
//            viewPager.setOffscreenPageLimit(1);
//            viewPager.setAdapter(adapter);

        } else {
            // User is already logged in start HomeActivity.
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            this.finish();

            Log.v("MainActivity", "User already logged in, skipping sign-in." + userId);

        }
    }

//    @Override
//    public void onBackPressed() {
//        if (!BackStackFragment.handleBackPressed(getSupportFragmentManager())) {
//            super.onBackPressed();
//        }
//    }

}
