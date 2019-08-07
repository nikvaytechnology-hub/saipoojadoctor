package com.nikvay.saipoojadoctor.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.nikvay.saipoojadoctor.model.DoctorModel;

import java.util.ArrayList;

public class SharedUtils {

    private static SharedPreferences preferences;

    private static SharedPreferences.Editor editor;
    private static String DEVICE_TOKEN = "DEVICE_TOKEN";

    public static String getSharedUtils(Context mContext)
    {
         preferences=mContext.getSharedPreferences(StaticContent.UserData.MY_PREFERENCE, Context.MODE_PRIVATE);
         return preferences.getString(StaticContent.UserData.IS_LOGIN,"");
    }
    public static void putSharedUtils(Context mContext) {
        preferences = mContext.getSharedPreferences(StaticContent.UserData.MY_PREFERENCE, Context.MODE_PRIVATE);
        editor= preferences.edit();
        editor.putString(StaticContent.UserData.IS_LOGIN, "true");
        editor.commit();
    }

    public static void removeSharedUtils(Context mContext)
    {
        preferences = mContext.getSharedPreferences(StaticContent.UserData.MY_PREFERENCE, Context.MODE_PRIVATE);
        editor= preferences.edit();
        editor.putString(StaticContent.UserData.IS_LOGIN, "false");
        editor.commit();
    }

    public static void putDeviceToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(StaticContent.DeviceToken.DEVICE_TOKEN, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(DEVICE_TOKEN, token)
                .apply();
    }

    public static String getDeviceToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(StaticContent.DeviceToken.DEVICE_TOKEN, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DEVICE_TOKEN, "");
    }

    public static void addUserUtils(Context mContext, ArrayList<DoctorModel> doctorModelArrayList) {

        preferences = mContext.getSharedPreferences(StaticContent.UserData.MY_PREFERENCE, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(StaticContent.UserData.DOCTOR_ID,doctorModelArrayList.get(0).getDoctor_id());
        editor.putString(StaticContent.UserData.USER_ID, doctorModelArrayList.get(0).getUser_id());
        editor.putString(StaticContent.UserData.NAME, doctorModelArrayList.get(0).getName());
        editor.putString(StaticContent.UserData.EMAIL, doctorModelArrayList.get(0).getEmail());
        editor.putString(StaticContent.UserData.ADDRESS, doctorModelArrayList.get(0).getAddress());
        editor.putString(StaticContent.UserData.DEPARTMENT_ID, doctorModelArrayList.get(0).getDepartment_id());
        editor.putString(StaticContent.UserData.PROFILE, doctorModelArrayList.get(0).getProfile());
        editor.putString(StaticContent.UserData.HOSPITAL_NAME, doctorModelArrayList.get(0).getTitle());
        editor.putString(StaticContent.UserData.HOSPITAL_NAME, doctorModelArrayList.get(0).getTitle());
        editor.putString(StaticContent.UserData.IS_SUPER_ADMIN, doctorModelArrayList.get(0).getIs_super_admin());

        editor.commit();
    }

    public static ArrayList<DoctorModel> getUserDetails(Context mContext) {
        ArrayList<DoctorModel> userDetailsModuleArrayList = new ArrayList<>();
        DoctorModel doctorModel = new DoctorModel();
        preferences = mContext.getSharedPreferences(StaticContent.UserData.MY_PREFERENCE, Context.MODE_PRIVATE);
        doctorModel.setDoctor_id(preferences.getString(StaticContent.UserData.DOCTOR_ID, ""));
        doctorModel.setUser_id(preferences.getString(StaticContent.UserData.USER_ID, ""));
        doctorModel.setName(preferences.getString(StaticContent.UserData.NAME, ""));
        doctorModel.setEmail(preferences.getString(StaticContent.UserData.EMAIL, ""));
        doctorModel.setAddress(preferences.getString(StaticContent.UserData.ADDRESS, ""));
        doctorModel.setDepartment_id(preferences.getString(StaticContent.UserData.DEPARTMENT_ID, ""));
        doctorModel.setProfile(preferences.getString(StaticContent.UserData.PROFILE, ""));
        doctorModel.setTitle(preferences.getString(StaticContent.UserData.HOSPITAL_NAME, ""));
        doctorModel.setIs_super_admin(preferences.getString(StaticContent.UserData.IS_SUPER_ADMIN, ""));

        userDetailsModuleArrayList.add(doctorModel);
        return userDetailsModuleArrayList;
    }
    public static void updateProfile(Context mContext,String name,String email,String address,String profile)
    {
        preferences = mContext.getSharedPreferences(StaticContent.UserData.MY_PREFERENCE, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(StaticContent.UserData.NAME,name);
        editor.putString(StaticContent.UserData.EMAIL, email);
        editor.putString(StaticContent.UserData.ADDRESS, address);
        editor.putString(StaticContent.UserData.PROFILE,profile);
        editor.commit();
    }

    public static boolean clearShareUtils(Context mContext){
        SharedPreferences settings = mContext.getSharedPreferences(StaticContent.UserData.MY_PREFERENCE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        return editor.commit();
    }

 /*   public static  void serviceData(Context mContext)
    {
        preferences = mContext.getSharedPreferences(StaticContent.UserData.APPOINTMENT, mContext.MODE_PRIVATE);
        editor = preferences.edit();
        editor.commit();

    }
*/

}
