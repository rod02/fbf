package com.fightbackfoods.model;

import com.fightbackfoods.utils.TokenManager;

import java.util.HashMap;
import java.util.Map;

public class Token {
    String token;
    public static Map<String,String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(User.getCurrentUserId()));
        map.put("token", Token.get());
        return map;
    }


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
