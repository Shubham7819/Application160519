package com.example.ideal48.application160519;

import android.app.Application;
import android.os.AsyncTask;

/**
 * Created by ideal48 on 16/5/19.
 */

public class UserRepository {

    private AnimeDao mAnimeDao;
//    private List<User> mAllUsers;

    UserRepository(Application application){
        AnimeRoomDatabase db = AnimeRoomDatabase.getDatabase(application);
        mAnimeDao = db.userDao();
        //mAllUsers = mAnimeDao.getAllUsers();
    }

    //List<User> getmAllUsers(){
    //    return mAllUsers;
    //}

//    public void insert(User user){
//        new insertAsyncTask(mAnimeDao).execute(user);
//    }

//    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {
//
//        private AnimeDao mAsyncTaskDao;
//
//        insertAsyncTask(AnimeDao dao) {
//            mAsyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(final User... params) {
//        //    mAsyncTaskDao.insert(params[0]);
//            return null;
//        }
//    }

}
