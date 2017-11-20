package com.mobitribe.currencyconvertor.extras;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobitribe.currencyconvertor.model.UserConversion;

import java.lang.reflect.Type;

/**
 * Author: Muhammad Shahab
 * Date: 18/10/17.
 * Description: class to save or retrieve data from shared preferences
 */

public class SharedPreferences {

    private static final String USER_CONVERSION = "USER_CONVERSION";
    private static final String THEME = "THEME";
    private final android.content.SharedPreferences mPrefs;

    public SharedPreferences(Context mContext) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }
    public boolean saveJobSeeker() {
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(UserConversion.getInstance());
        prefsEditor.putString(USER_CONVERSION, json);
        return prefsEditor.commit();
    }

    public UserConversion getJobSeeker() {
        Gson gson = new Gson();
        String json = mPrefs.getString(USER_CONVERSION, "");
        Type type = new TypeToken<UserConversion>() {}.getType();
        return gson.fromJson(json,type);
    }

    public boolean putTheme(int pos) {
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putInt(THEME, pos);
        return prefsEditor.commit();
    }
    public int getTheme() {
        return mPrefs.getInt(THEME,0);
    }
}
