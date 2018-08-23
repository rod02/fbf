package com.fightbackfoods.api;

import com.fightbackfoods.model.HealthAspectCategory;
import com.fightbackfoods.model.Journal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseHealthAspect extends Response {


    @SerializedName("healthAspects")
    private List<HealthAspectCategory> healthAspects;


    @SerializedName("healthAspectCategories")
    private List<HealthAspectCategory> healthAspectCategories;

    public List<HealthAspectCategory> getHealthAspects() {
        return healthAspects;
    }

    public void setHealthAspects(List<HealthAspectCategory> healthAspects) {
        this.healthAspects = healthAspects;
    }

    public List<HealthAspectCategory> getHealthAspectCategories() {
        return healthAspectCategories;
    }

    public void setHealthAspectCategories(List<HealthAspectCategory> healthAspectCategories) {
        this.healthAspectCategories = healthAspectCategories;
    }
}
