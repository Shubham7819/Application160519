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

import com.example.ideal48.application160519.fragment.AnimeFragment;
import com.example.ideal48.application160519.fragment.FavoriteFragment;
import com.example.ideal48.application160519.fragment.HomeFragment;
import com.example.ideal48.application160519.fragment.ProfileFragment;
import com.example.ideal48.application160519.R;

public class HomeActivity extends AppCompatActivity {

    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("HomeActivity.java", "OnCreate called");

        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("Home");
        toolbar.setEnabled(false);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frame_layout, new HomeFragment());
        ft.commit();

        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.frame_layout, new HomeFragment(), "HomeFragment");
                        ft.commit();
                        break;

                    case R.id.action_anime:
                        FragmentTransaction ft1 = fragmentManager.beginTransaction();
                        ft1.replace(R.id.frame_layout, new AnimeFragment());
                        ft1.commit();
                        break;

                    case R.id.action_favourites:
                        FragmentTransaction ft2 = fragmentManager.beginTransaction();
                        ft2.replace(R.id.frame_layout, new FavoriteFragment());
                        ft2.commit();
                        break;

                    case R.id.action_profile:
                        FragmentTransaction ft3 = fragmentManager.beginTransaction();
                        ft3.replace(R.id.frame_layout, new ProfileFragment());
                        ft3.commit();
                        break;

                }
                return true;
            }
        });
    }

//    @Override
//    protected void onResumeFragments() {
//        super.onResumeFragments();
//        Log.d("HomeActivity", "OnResume called");
//        if (DeleteUserDialogFragment.DELETION_RESULT != 0){
//            Fragment frg = null;
//            frg = getSupportFragmentManager().findFragmentByTag("HomeFragment");
//            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.detach(frg);
//            ft.attach(frg);
//            DeleteUserDialogFragment.DELETION_RESULT = 0;
//            ft.commit();
//        }
//    }

}
