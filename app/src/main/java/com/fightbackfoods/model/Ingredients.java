package com.fightbackfoods.model;

import com.google.gson.annotations.SerializedName;

public class Ingredients {

    @SerializedName("desc")
    private String desc;

    @SerializedName("upd")
    private String upd;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUpd() {
        return upd;
    }

    public void setUpd(String upd) {
        this.upd = upd;
    }
}
