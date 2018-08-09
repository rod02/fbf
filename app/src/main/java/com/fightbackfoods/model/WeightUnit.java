package com.fightbackfoods.model;

import com.fightbackfoods.interfaces.Item;
import com.google.gson.annotations.SerializedName;

public class WeightUnit implements Item {

    @SerializedName("height_unit_id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;


    public WeightUnit() {
        this.name="";
        this.description="";
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
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }
}
