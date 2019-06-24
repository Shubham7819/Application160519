package com.example.ideal48.application160519.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ideal48.application160519.AppSession;
import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.UserDao;
import com.example.ideal48.application160519.UserRoomDatabase;
import com.example.ideal48.application160519.activity.EditProfileActivity;
import com.example.ideal48.application160519.activity.MainActivity;
import com.example.ideal48.application160519.model.User;

import java.io.ByteArrayInputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    User currentUser = null;
    String userId = null;
    SharedPreferences sharedPref;

    View rootView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v("ProfileFragment.java", "OnCreate called");

//        sharedPref = getActivity().getSharedPreferences(getString(R.string.stored_user_id), Context.MODE_PRIVATE);
        userId = new AppSession(getActivity()).getLoginId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile_layout, container, false);

        Log.v("ProfileFragment.java", "OnCreateView called");

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        UserRoomDatabase userRoomDatabase = UserRoomDatabase.getDatabase(getActivity());
        final UserDao userDao = userRoomDatabase.userDao();

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                currentUser = userDao.getUserDetails(userId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (currentUser != null) {
                    TextView idTV = rootView.findViewById(R.id.id_tv);
                    //Log.v("ProfileFragment", "current user id " + currentUser.getId());
                    idTV.setText(String.valueOf(currentUser.getId()));

                    TextView userIdTV = rootView.findViewById(R.id.user_id_tv);
                    userIdTV.setText(currentUser.getmUserId());

                    TextView passwordTV = rootView.findViewById(R.id.password_tv);
                    passwordTV.setText(currentUser.getmPassword());

                    ImageView userIV = rootView.findViewById(R.id.profile_iv);
                    byte[] byteImage = currentUser.getmImageInByte();
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(byteImage);
                    Bitmap image = BitmapFactory.decodeStream(imageStream);
                    userIV.setImageBitmap(image);

                    TextView fNameTV = rootView.findViewById(R.id.f_name_tv);
                    fNameTV.setText(currentUser.getmFName());

                    TextView lNameTV = rootView.findViewById(R.id.l_name_tv);
                    lNameTV.setText(currentUser.getmLName());

                    TextView dobTV = rootView.findViewById(R.id.profile_dob_tv);
                    dobTV.setText(currentUser.getmDOB());
                }
            }
        }.execute();

        Button editProfileBtn = rootView.findViewById(R.id.edit_profile_btn);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity() , EditProfileActivity.class);
                i.putExtra(getActivity().getResources().getString(R.string.user_id), currentUser.getmUserId());
                getActivity().startActivity(i);

            }
        });

        Button logoutBtn = rootView.findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences pref = getActivity().getSharedPreferences(getString(R.string.stored_user_id), Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();

//                editor.putString(getString(R.string.user_name), "");
//                editor.commit();
                //        new AppSession(getActivity()).setLoginId("");

                new AppSession(getActivity()).clearAppSession();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });
    }
}
