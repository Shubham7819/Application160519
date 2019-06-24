package com.example.ideal48.application160519.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by ideal48 on 16/5/19.
 */

@Entity(tableName = "users_table", indices = {@Index(value = {"user_id"}, unique = true)} )
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public String mUserId;

    @ColumnInfo(name = "password")
    public String mPassword;

    @ColumnInfo(name = "f_name")
    public String mFName;

    @ColumnInfo(name = "l_name")
    public String mLName;

    @ColumnInfo(name = "fav")
    public boolean mFav = false;

    @ColumnInfo(name = "dob")
    public String mDOB;

    @ColumnInfo(name = "byte_image")
    public byte[] mImageInByte;

    public User() {
    }

    public User(String userId, String password, String fName, String lName, String dob, byte[] imageInByte){
        mUserId = userId;
        mPassword = password;
        mFName = fName;
        mLName = lName;
        mDOB = dob;
        mImageInByte = imageInByte;
    }

    public byte[] getmImageInByte() {
        return mImageInByte;
    }

    public void setmImageInByte(byte[] mImageInByte) {
        this.mImageInByte = mImageInByte;
    }

    public int getId() {
        return id;
    }

    public String getmUserId() {
        return mUserId;
    }

    public String getmPassword() {
        return mPassword;
    }

    public String getmFName() {
        return mFName;
    }

    public String getmLName() {
        return mLName;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public boolean ismFav() {
        return mFav;
    }

    public void setmFav(boolean mIsFav) {
        this.mFav = mIsFav;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void setmFName(String mFName) {
        this.mFName = mFName;
    }

    public void setmDOB(String mDOB) {
        this.mDOB = mDOB;
    }

    public String getmDOB() {
        return mDOB;
    }

    public void setmLName(String mLName) {
        this.mLName = mLName;
    }
}
