package com.fightbackfoods.model;

import com.fightbackfoods.interfaces.Item;
import com.google.gson.annotations.SerializedName;

public class Mobility implements Item {

    @SerializedName("mobility_id")
    int id;
    @SerializedName("name")
    String description;
    @SerializedName("description")
    String name;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
