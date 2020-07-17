package vk.dogbreed.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SharedPreferencesHelper {

    private static final String PREF_TIME = "pref_time";
    private static SharedPreferencesHelper instance;
    private SharedPreferences preferences;

    private SharedPreferencesHelper(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public static SharedPreferencesHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesHelper(context);
        }
        return instance;
    }

    public void saveUpdateTime(long time) {
        preferences.edit().putLong(PREF_TIME, time).apply();
    }

    public long getLastUpdateTime() {
        return preferences.getLong(PREF_TIME, 0);
    }
}
