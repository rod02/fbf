package com.fightbackfoods.model;

import com.fightbackfoods.utils.TokenManager;

public class Token {
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Token(String token) {
        this.token = token;
    }

    public static String get(){
        return getCurrentToken().token;
    }

    private static Token getCurrentToken() {
       return TokenManager.getToken();
    }
}
