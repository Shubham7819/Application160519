package com.example.ideal48.application160519;

import android.app.Application;
import android.os.AsyncTask;

import com.example.ideal48.application160519.model.User;

import java.util.List;

/**
 * Created by ideal48 on 16/5/19.
 */

public class UserRepository {

    private UserDao mUserDao;
    private List<User> mAllUsers;

    UserRepository(Application application){
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
        //mAllUsers = mUserDao.getAllUsers();
    }

    //List<User> getmAllUsers(){
    //    return mAllUsers;
    //}

    public void insert(User user){
        new insertAsyncTask(mUserDao).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
