package com.playground.skypass.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

/**
 * Created by aderifaldi on 13/01/2016.
 */
public class GlobalVariable {

    public static final String AZURE_SERVICE_URL = "https://easysell.azure-mobile.net/";
    public static final String AZURE_SERVICE_KEY = "azHzzCqaZQylQCUlbsJnNPStWOXFzg75";

    public static final String PREF_NAME = "BSD_POINT_PREFERENCE";
    private static final String IS_LOCATION_REQUEST_GRANTED = "IsLocationRequestGranted";
    private static final String IS_SAMSUNG_DEVICE = "IsSamsungDevice";
    private static final String LATITUDE = "userLatitude";
    private static final String LONGITUDE = "userLongitude";
    private static final String LOGIN_TOKEN = "loginToken";
    private static final String IS_LOGIN = "isLogin";
    private static final String USER_NAME = "userName";
    private static final String USER_EMAIL = "userEmail";
    private static final String USER_PHOTO_URL = "userPhotoURL";
    private static final String GCM_TOKEN = "deviceToken";
    public static final String NOTIFICATION_ID = "notificationId";
    public static final String NOTIFICATION_REQUEST_CODE = "requestCode";
    public static final String ISREGISTERPUSH = "isRegisterPush";

    public static final double DEFAULT_LATITUDE = -6.300503d;
    public static final double DEFAULT_LONGITUDE = 106.651777d;
    public static final int REQUEST_LOCATION = 1;

    public static void saveLocation(Context context, double lat, double lon){
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(LATITUDE, Double.doubleToRawLongBits(lat));
        edit.putLong(LONGITUDE, Double.doubleToRawLongBits(lon));
        edit.apply();
    }

    public static Location getLocation(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        Double latitude = Double.longBitsToDouble(sharedPreferences.getLong(LATITUDE, Double.doubleToRawLongBits(DEFAULT_LATITUDE)));
        Double longitude = Double.longBitsToDouble(sharedPreferences.getLong(LONGITUDE, Double.doubleToRawLongBits(DEFAULT_LONGITUDE)));
        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    public static void saveNotificationId(Context context, int data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NOTIFICATION_ID, data);
        editor.apply();
    }

    public static int getNotificationId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        int data = sharedPreferences.getInt(NOTIFICATION_ID, 0);

        return data;
    }

    public static void saveRequestCode(Context context, int data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NOTIFICATION_REQUEST_CODE, data);
        editor.apply();
    }

    public static int getRequestCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        int data = sharedPreferences.getInt(NOTIFICATION_REQUEST_CODE, 0);

        return data;
    }

    public static void saveGCMToken(Context context, String deviceToken) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(GCM_TOKEN, deviceToken);
        editor.apply();
    }

    public static String getGCMToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        String deviceToken = sharedPreferences.getString(GCM_TOKEN, null);

        return deviceToken;
    }

    public static void saveIsRegisterPush(Context context, boolean data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ISREGISTERPUSH, data);
        editor.apply();
    }

    public static boolean getIsRegisterPush(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(ISREGISTERPUSH, false);
    }

    public static void setLocationRequestGranted(Context context, boolean data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOCATION_REQUEST_GRANTED, data);
        editor.apply();
    }

    public static boolean getLocationRequestGranted(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOCATION_REQUEST_GRANTED, false);
    }

    public static void setIsSamsungDevice(Context context, boolean data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_SAMSUNG_DEVICE, data);
        editor.apply();
    }

    public static boolean getIsSamsungDevice(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_SAMSUNG_DEVICE, false);
    }

    public static void saveLoginToken(Context context, String data){
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOGIN_TOKEN, data);
        editor.apply();
    }

    public static String getLoginToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LOGIN_TOKEN, null);
    }

    public static void saveUserName(Context context, String data){
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, data);
        editor.apply();
    }

    public static String getUserName(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_NAME, null);
    }

    public static void saveUserEmail(Context context, String data){
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_EMAIL, data);
        editor.apply();
    }

    public static String getUserEmail(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_EMAIL, null);
    }

    public static void saveUserPhotoURL(Context context, String data){
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PHOTO_URL, data);
        editor.apply();
    }

    public static String getUserPhotoURL(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_PHOTO_URL, null);
    }

    public static boolean getIsLogin(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public static void saveIsLogin(Context context, boolean data){
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGIN, data);
        editor.apply();
    }

}
