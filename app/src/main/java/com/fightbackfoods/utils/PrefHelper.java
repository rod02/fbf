package com.fightbackfoods.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.fightbackfoods.Api;

abstract public class PrefHelper {
    protected static String SHARED_PREF = "shared_pref";


     protected   static SharedPreferences.Editor getEditor() {
        return getPref().edit();
    }
    public static void clearPref()   {
        SharedPreferences.Editor editor = getEditor();
        editor.clear();
        editor.apply();

    }

     protected static SharedPreferences getPref() {
        return Api.getApplicationContext().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
    }

}
