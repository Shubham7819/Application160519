package com.example.ideal48.application160519.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ideal48.application160519.fragment.EditUserFragment;
import com.example.ideal48.application160519.R;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        String mUserId = getIntent().getStringExtra(getString(R.string.user_id));

        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.user_id), mUserId);

        EditUserFragment editUserFragment = new EditUserFragment();
        editUserFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.edit_profile_frame, editUserFragment);
        ft.commit();
    }
}
