package com.fightbackfoods.model;

import com.fightbackfoods.interfaces.Item;
import com.google.gson.annotations.SerializedName;

public class FoodGroup implements Item {


    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("offset")
    int offset;

    public FoodGroup() {
        name="";
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return String.valueOf(getOffset());
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
