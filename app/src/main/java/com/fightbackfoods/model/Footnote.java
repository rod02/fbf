package com.fightbackfoods.model;

import com.google.gson.annotations.SerializedName;

public class Footnote {
    @SerializedName("name")
    private String name;
    @SerializedName("upds")
    private String upds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpds() {
        return upds;
    }

    public void setUpds(String upds) {
        this.upds = upds;
    }
}
