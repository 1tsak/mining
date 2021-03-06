package com.oadev.mining.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.oadev.model.User;
import com.oadev.mining.activity.LoginActivity;

/**
 * Created by Belal on 9/5/2017.
 */

//here for this class we are using a singleton pattern

public class PrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "biddingsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_AMOUNT = "keyamount";
    private static final String KEY_ID = "keyuserid";
    private static final String KEY_PHONE = "keyphone";
    private static final String KEY_REFER = "keyrefer";
    private static final String KEY_REFERER = "keyreferer";

    private static PrefManager mInstance;
    private static Context mCtx;

    private PrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized PrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_AMOUNT, user.getAmount());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_REFER, user.getRefercode());
        editor.putString(KEY_REFERER, user.getReferedby());
        editor.apply();
    }

    public void updateAmount(String amount){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_AMOUNT, amount);
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_AMOUNT, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_REFER, null),
                sharedPreferences.getString(KEY_REFERER, null));
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
