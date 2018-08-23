package com.fightbackfoods.model;

import com.google.gson.annotations.SerializedName;

public class SubCategory {
    @SerializedName("sub_category_id")
    String id;
    @SerializedName("category_id")
    String categoryId;
    @SerializedName("name")
    String name;

    public static final String MIND ="1";
    public static final String BODY ="2";
    public static final String SOUL ="3";

}
