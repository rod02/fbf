package com.fightbackfoods.model;

import com.fightbackfoods.interfaces.Item;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CancerType implements Item {

    @SerializedName("cancer_type_id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;

    public CancerType() {
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
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }
}
