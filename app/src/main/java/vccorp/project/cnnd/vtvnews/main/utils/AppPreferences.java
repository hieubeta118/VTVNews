package vccorp.project.cnnd.vtvnews.main.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Admin on 3/23/2016.
 */
public enum AppPreferences {
    INSTANCE;

    private SharedPreferences pref;

    public static void init(Context context){
        INSTANCE.pref = PreferenceManager.getDefaultSharedPreferences(context);
    }


}
