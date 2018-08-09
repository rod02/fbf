package com.fightbackfoods.api;

import com.fightbackfoods.model.CancerStage;
import com.fightbackfoods.model.Food;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFoodList extends Response {
    @SerializedName("foodList")
    @Expose
    List<Food> foodList;

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }
}
