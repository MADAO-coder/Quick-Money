package com.example.a3130_group_6;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

//Code for map has been taken from tutorials on Google Map Integration
public class PrefrenceUtil {

    public static void firstTimeAskingPermission(Context context, String permission, boolean isFirstTime, String prefName){
        SharedPreferences sharedPreference = context.getSharedPreferences(prefName, MODE_PRIVATE);
        sharedPreference.edit().putBoolean(permission, isFirstTime).apply();
    }
    public static boolean isFirstTimeAskingPermission(Context context, String permission, String prefName){
        return context.getSharedPreferences(prefName, MODE_PRIVATE).getBoolean(permission, true);
    }
}

