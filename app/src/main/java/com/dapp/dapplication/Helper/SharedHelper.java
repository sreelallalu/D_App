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


    public void setStudent(String userDetails) {


        mSharedPreferences.edit().putString(SHAREVALUE.User_detail, userDetails).apply();

    }

    public void setTeacher(String userDetails) {


        mSharedPreferences.edit().putString(SHAREVALUE.TeacherD, userDetails).apply();

    }


    public String getTeacherDetails() {


        return mSharedPreferences.getString(SHAREVALUE.TeacherD, "");

    }

    public String getStudentDetails() {


        return mSharedPreferences.getString(SHAREVALUE.User_detail, "");

    }


    public void setLoginCheck(boolean b) {
        mSharedPreferences.edit().putBoolean(SHAREVALUE.logincheck, b).apply();

    }


    public String getRegType() {


        return mSharedPreferences.getString(SHAREVALUE.regtype, "");


    }

    public boolean getLoginCheck() {


        return mSharedPreferences.getBoolean(SHAREVALUE.logincheck, false);


    }


}
