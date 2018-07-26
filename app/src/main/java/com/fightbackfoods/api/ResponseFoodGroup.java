package com.fightbackfoods.api;

import com.fightbackfoods.model.FoodGroup;
import com.fightbackfoods.model.WeightUnit;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFoodGroup extends Response {
    @SerializedName("foodGroups")
    @Expose
    List<FoodGroup> foodGroups;

    public List<FoodGroup> getFoodGroups() {
        return foodGroups;
    }

    public void setFoodGroups(List<FoodGroup> foodGroups) {
        this.foodGroups = foodGroups;
    }
}
