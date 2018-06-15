package com.fightbackfoods.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.fightbackfoods.Api;
import com.fightbackfoods.model.Token;

public class TokenManager {

    private static final String SHARED_PREF = "token_pref";
    private static Token token;

    public static Token getToken() {
        if (token!=null){
            return token;
        }
        token = getFromPref();
        return token;
    }

    private static Token getFromPref() {

        final SharedPreferences sharedPref = Api.getApplicationContext().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        String value = sharedPref.getString("value","");
        token = new Token(value);
        return token;
    }

    public static void setToken(String value, boolean writeToCache){
        token = new Token(value);
        if(writeToCache) {

            SharedPreferences sharedPref =  Api.getApplicationContext().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("value", value);
            editor.apply();
        }
    }

}
