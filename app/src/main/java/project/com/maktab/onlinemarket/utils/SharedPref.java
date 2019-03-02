package project.com.maktab.onlinemarket.utils;

import android.preference.PreferenceManager;

import project.com.maktab.onlinemarket.OnlineMarketApp;

public class SharedPref {
    private static final String CUSTOMER_ID_PREF = "CUSTOMER_ID_PREF";

    public static void addCustomerId(long id) {
        PreferenceManager.getDefaultSharedPreferences(OnlineMarketApp.getAppInstance()).edit()
                .putLong(CUSTOMER_ID_PREF, id).apply();
    }

    public static long getCustomerId() {
        return PreferenceManager.getDefaultSharedPreferences(OnlineMarketApp.getAppInstance()).getLong(CUSTOMER_ID_PREF, -1);
    }


}
