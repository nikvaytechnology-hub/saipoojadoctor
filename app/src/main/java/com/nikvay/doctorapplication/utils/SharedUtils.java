package com.nikvay.doctorapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtils {
    private static final String IS_LOGIN="IS_LOGIN";
    private static final String MY_PREFERENCE="CNP_MAINTENANCE";
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static String getSharedUtils(Context mContext)
    {
         preferences=mContext.getSharedPreferences(MY_PREFERENCE,mContext.MODE_PRIVATE);
         return preferences.getString(IS_LOGIN,"");
    }
    public static void putSharedUtils(Context mContext) {
        preferences = mContext.getSharedPreferences(MY_PREFERENCE, mContext.MODE_PRIVATE);
        editor= preferences.edit();
        editor.putString(IS_LOGIN, "true");
        editor.commit();
    }

    public static void removeSharedUtils(Context mContext)
    {
        preferences = mContext.getSharedPreferences(MY_PREFERENCE, mContext.MODE_PRIVATE);
        editor= preferences.edit();
        editor.putString(IS_LOGIN, "false");
        editor.commit();
    }


}
