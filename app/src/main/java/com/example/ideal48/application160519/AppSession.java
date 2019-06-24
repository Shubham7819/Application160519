package com.example.ideal48.application160519;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Manish on 4/7/16.
 */
public class AppSession {

    private static final String SESSION_NAME = "com.example.ideal48.application160519";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor prefsEditor;

    public AppSession(Context context) {
        mSharedPreferences = context.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        prefsEditor = mSharedPreferences.edit();
    }

    public String getLoginId() {
        return mSharedPreferences.getString("user", "");
    }

    public void setLoginId(String loginId) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("user", loginId);
        prefsEditor.commit();
    }

    public String getPassword() {
        return mSharedPreferences.getString("getPassword", "");
    }

    public void setPassword(String password) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getPassword", password);
        prefsEditor.commit();
    }

    public void clearAppSession() {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.remove("user");
        prefsEditor.commit();
    }

    public void setRememberUser(String userId, String password) {
        prefsEditor.putString("remember_user_id", userId);
        prefsEditor.putString("remember_user_password", password);
        prefsEditor.commit();
    }

    public String getRememberUserId() {
        return mSharedPreferences.getString("remember_user_id", "");

    }

    public String getRememberUserPassword() {
        return mSharedPreferences.getString("remember_user_password", "");
    }

}

