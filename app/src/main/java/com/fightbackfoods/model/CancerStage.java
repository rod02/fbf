package com.fightbackfoods.model;

import com.fightbackfoods.interfaces.Item;
import com.google.gson.annotations.SerializedName;

public class CancerStage implements Item {

    @SerializedName("cancer_stage_id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;

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
