package com.example.ssandoy.s236305_mappe2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by ssandoy on 28.10.2016.
 */

public class LanguageSelector {

    public final static String LANGUAGE = "language";
    public final static String DEFAULT_LANGUAGE = "en";

    public static void changeLanguage(Context context, String language) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(new Locale(language));
        resources.updateConfiguration(configuration, displayMetrics);
        saveLanguage(context, language);
    }

    private static void saveLanguage(Context context, String language) {
        SharedPreferences sprfs = context.getSharedPreferences(LANGUAGE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sprfs.edit();
        editor.putString(LANGUAGE, language);
        editor.commit();
    }

    public static void loadLanguage(Context context) {
        SharedPreferences sprfs = context.getSharedPreferences(LANGUAGE, Activity.MODE_PRIVATE);
        String language = sprfs.getString(LANGUAGE, DEFAULT_LANGUAGE);
        changeLanguage(context, language);
    }

}
