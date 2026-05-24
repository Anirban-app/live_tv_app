package com.example.livetvapp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
    private static final String PREF_NAME = "live_tv_prefs";
    private static final String KEY_BOOT_TO_PLAYER = "boot_to_player";
    private static final String KEY_DARK_THEME = "dark_theme";

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static boolean isBootToPlayerEnabled(Context context) {
        return getPreferences(context).getBoolean(KEY_BOOT_TO_PLAYER, false);
    }

    public static void setBootToPlayerEnabled(Context context, boolean enabled) {
        getPreferences(context).edit().putBoolean(KEY_BOOT_TO_PLAYER, enabled).apply();
    }

    public static boolean isDarkTheme(Context context) {
        return getPreferences(context).getBoolean(KEY_DARK_THEME, false);
    }

    public static void setDarkTheme(Context context, boolean enabled) {
        getPreferences(context).edit().putBoolean(KEY_DARK_THEME, enabled).apply();
    }
}
