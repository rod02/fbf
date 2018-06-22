package com.fightbackfoods.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.fightbackfoods.Api;

public class ConfigPrefHelper  {
    private static final String SHARED_PREF = "conf_check_up";
    private static final String JOURNAL_LAST_UPDATE = "jo_lst_up";


     static SharedPreferences.Editor getEditor() {
        return getPref().edit();
    }
    public static void clearPref()   {
        SharedPreferences.Editor editor = getEditor();

        editor.clear();
        editor.apply();

    }

     static SharedPreferences getPref() {
        return Api.getApplicationContext().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
    }

    public static String getJournalLastUpdate(){
        return getPref().getString(JOURNAL_LAST_UPDATE,"0");
    }


}
