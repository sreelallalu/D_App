package com.dapp.dapplication.Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sreelal on 14/12/17.
 */
public class SharedHelper {
    private SharedPreferences mSharedPreferences;

    public SharedHelper(Context context) {

        this.mSharedPreferences = context.getSharedPreferences(SHAREVALUE.main, Context.MODE_PRIVATE);
    }


    public void setRegType(String centerid) {
        mSharedPreferences.edit().putString(SHAREVALUE.regtype, centerid).apply();
    }


    public void setUserDetails(String userDetails) {


        mSharedPreferences.edit().putString(SHAREVALUE.User_detail, userDetails).apply();

    }


    public void setWelcome(boolean b) {
        mSharedPreferences.edit().putBoolean(SHAREVALUE.welcomescreen, b).apply();

    }


    public void setLoginCheck(boolean b) {
        mSharedPreferences.edit().putBoolean(SHAREVALUE.logincheck, b).apply();

    }

    public void setFirebaseToken(String token) {
        mSharedPreferences.edit().putString(SHAREVALUE.firebasetoken, token).apply();

    }


    public String getRegType() {


        return mSharedPreferences.getString(SHAREVALUE.regtype, "");


    }

    public boolean getLoginCheck() {


        return mSharedPreferences.getBoolean(SHAREVALUE.logincheck, false);


    }


}
