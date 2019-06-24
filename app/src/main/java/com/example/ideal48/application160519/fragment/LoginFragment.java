package com.example.ideal48.application160519.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ideal48.application160519.AppSession;
import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.UserDao;
import com.example.ideal48.application160519.UserRoomDatabase;
import com.example.ideal48.application160519.activity.HomeActivity;
import com.example.ideal48.application160519.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText userIdET;
    private EditText passwordET;
    private CheckBox rememberUserCheckBox;
    private AppSession appSession;
    private static final String TAG = LoginFragment.class.getSimpleName();

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout, container, false);

        // Making instance of XML views.
        userIdET = view.findViewById(R.id.user_id_ET);
        passwordET = view.findViewById(R.id.password_ET);
        rememberUserCheckBox = view.findViewById(R.id.remember_user_check_box);
        Button loginButton = view.findViewById(R.id.login_btn);

        // Initializing AppSession for SharedPreference transactions.
        appSession = new AppSession(getActivity());

        // Get Remembered User's Id and Password.
        final String rememberUserId = appSession.getRememberUserId();
        String rememberUserPassword = appSession.getRememberUserPassword();

        // Check if user's details is stored
        // Set details in EditText if found
        if (rememberUserId != "" && !TextUtils.isEmpty(rememberUserId)) {
            userIdET.setText(rememberUserId);
        }
        if (rememberUserPassword != "" && !TextUtils.isEmpty(rememberUserPassword)) {
            passwordET.setText(rememberUserPassword);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String userId = userIdET.getText().toString();
                final String password = passwordET.getText().toString();

                if (userId.isEmpty() || password.isEmpty()) {

                    Toast.makeText(getActivity(), "All Fields Mandatory", Toast.LENGTH_SHORT).show();

                } else {

                    UserRoomDatabase userRoomDatabase = UserRoomDatabase.getDatabase(getActivity());
                    final UserDao userDao = userRoomDatabase.userDao();

                    new AsyncTask<Void, User, User>() {
                        @Override
                        protected User doInBackground(Void... voids) {

                            User user = userDao.checkIfUser(userId, password);
/*
                            String passwordD = userDao.getPassword(userId);
                            boolean isUserRegistered = userDao.checkIfUser(userId);
                            Object strObj = passwordD;
                            if (isUserRegistered){
                                if (password.equals(strObj)){
                                    Intent i = new Intent(getActivity(), HomeActivity.class);
                                    startActivity(i);
                                }else {
                                    Toast.makeText(getActivity(), "UserId or Password is incorrect !", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "UserId does not exist !", Toast.LENGTH_SHORT).show();
                            }
*/
                            return user;
                        }


                        @Override
                        protected void onPostExecute(User user) {
                            super.onPostExecute(user);

                            if (user != null) {
                                Toast.makeText(getActivity(), "Loggin success", Toast.LENGTH_SHORT).show();

//                                SharedPreferences sharedPrefe = getActivity().getSharedPreferences(getString(R.string.stored_user_id), Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sharedPrefe.edit();
//                                editor.putString(getString(R.string.user_name), userId);
//                                editor.commit();

                                if (rememberUserCheckBox.isChecked()) {
                                    appSession.setRememberUser(userId, password);
                                } else {
                                    if (userId.equals(rememberUserId)) {
                                        appSession.setRememberUser("", "");
                                    }
                                }

                                appSession.setLoginId(userId);

                                Intent i = new Intent(getActivity(), HomeActivity.class);
                                startActivity(i);
                                getActivity().finish();

//                                user.getmFName();
                            } else {
                                Toast.makeText(getActivity(), "Incorrect login detail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute();
                }
            }
        });

        // Take user to SignUpFragment on Button click.
        Button signUpButton = (Button) view.findViewById(R.id.sign_up_btn);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                SignUpFragment signUpFragment = new SignUpFragment();
                ft.replace(R.id.main_activity_frame, signUpFragment).addToBackStack("LoginFragment");
                ft.commit();
            }
        });

        return view;
    }
}
