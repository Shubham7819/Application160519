package com.example.ideal48.application160519.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.fragment.AboutFragment;
import com.example.ideal48.application160519.fragment.AnimeFragment;
import com.example.ideal48.application160519.fragment.FavoriteFragment;

public class HomeActivity extends AppCompatActivity {

    public static Toolbar toolbar;
    FragmentManager fragmentManager;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("HomeActivity.java", "OnCreate called");

        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.home_activity_toolbar);
        bottomNav = findViewById(R.id.home_activity_bottom_navigation_bar);

        toolbar.setEnabled(false);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.home_activity_frame_layout, new AnimeFragment());
        ft.commit();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.action_anime:
                        FragmentTransaction ft1 = fragmentManager.beginTransaction();
                        ft1.replace(R.id.home_activity_frame_layout, new AnimeFragment());
                        ft1.commit();
                        break;

                    case R.id.action_favourites:
                        FragmentTransaction ft2 = fragmentManager.beginTransaction();
                        ft2.replace(R.id.home_activity_frame_layout, new FavoriteFragment());
                        ft2.commit();
                        break;

                    case R.id.action_profile:
                        FragmentTransaction ft3 = fragmentManager.beginTransaction();
                        ft3.replace(R.id.home_activity_frame_layout, new AboutFragment());
                        ft3.commit();
                        break;

                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("HomeActivity.java", "onResume called");
    }
}
