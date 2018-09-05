package com.fightbackfoods.utils;

import com.facebook.Profile;
import com.fightbackfoods.model.User;

public class Validate {

    public static boolean isLoggedIn() {
        try {
            return !(Profile.getCurrentProfile() == null && User.getCurrentUser().getUserId() == 0);
        }catch (NullPointerException e){
            return false;
        }
    }

    public static String nullString(String s) {
        try {
            if(s==null)return "";
            return s;
        }catch (NullPointerException e){
            return "";
        }
    }

    public static boolean isNullString(String s) {
        try {
            return (s == null || s.equals("") || s.equals("null"));
        }catch (NullPointerException e){
            return true;
        }
    }

    public static String path(String path) {
        try {
            String http = "http";
            if(isNullString(path)) return "";
            if (path.contains(http)) return path;
            else path = "http://".concat(path);
            return path;
        } catch (NullPointerException e) {
            return path;
        }
    }
}
