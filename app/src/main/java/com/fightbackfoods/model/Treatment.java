package com.fightbackfoods.model;

import com.fightbackfoods.interfaces.Item;
import com.google.gson.annotations.SerializedName;

public class Treatment implements Item {

    @SerializedName("treatment_id")
    String id;
    @SerializedName("name")
    String description;
    @SerializedName("description")
    String name;


    public Treatment() {
        this.name="";
        this.description="";
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
