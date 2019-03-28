package project.com.maktab.onlinemarket.utils;

import android.preference.PreferenceManager;

import project.com.maktab.onlinemarket.OnlineMarketApp;

public class SharedPref {
    private static final String CUSTOMER_ID_PREF = "CUSTOMER_ID_PREF";
    private static final String PRODUCT_LAST_ID = "PRODUCT_LAST_ID";
    private static final String IS_ALARM_ON = "IS_ALARM_ON";
    private static final String ALARM_INTERVALS = "ALARM_INTERVALS";

    public static void addCustomerId(long id) {
        PreferenceManager.getDefaultSharedPreferences(OnlineMarketApp.getAppInstance()).edit()
                .putLong(CUSTOMER_ID_PREF, id).apply();
    }

    public static long getCustomerId() {
        return PreferenceManager.getDefaultSharedPreferences(OnlineMarketApp.getAppInstance()).getLong(CUSTOMER_ID_PREF, -1);
    }

    public static void setProductLastId(String id){
        PreferenceManager.getDefaultSharedPreferences(OnlineMarketApp.getAppInstance()).edit().putString(PRODUCT_LAST_ID,id).apply();
    }

    public static String getProductLastId(){
        return PreferenceManager.getDefaultSharedPreferences(OnlineMarketApp.getAppInstance()).getString(PRODUCT_LAST_ID,"");
    }

    public static void setAlarmStatus(boolean status){

        PreferenceManager.getDefaultSharedPreferences(OnlineMarketApp.getAppInstance()).edit()
                .putBoolean(IS_ALARM_ON,status).apply();

    }

    public static boolean isAlarmOn(){

        return PreferenceManager.getDefaultSharedPreferences(OnlineMarketApp.getAppInstance())
                .getBoolean(IS_ALARM_ON,false);

    }
    public static void setTimeIntervals(long timeIntervals){
        PreferenceManager.getDefaultSharedPreferences(OnlineMarketApp.getAppInstance()).edit()
                .putLong(ALARM_INTERVALS,timeIntervals).apply();

    }
    public static long getTimeIntervals(){
        return PreferenceManager.getDefaultSharedPreferences(OnlineMarketApp.getAppInstance()).getLong(ALARM_INTERVALS,-1);
    }






}
