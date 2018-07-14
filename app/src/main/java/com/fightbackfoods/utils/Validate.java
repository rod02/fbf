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
}
