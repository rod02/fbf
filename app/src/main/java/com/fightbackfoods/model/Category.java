package com.fightbackfoods.model;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("category_id")
    String id;
    @SerializedName("name")
    String name;

    public static final String EDUCATION ="3";
    public static final String FOODTRACKER ="1";
    public static final String LIFESTYLE ="2";
    public static final String COMMUNITY ="4";

    public static final String RECIPE ="5";




}