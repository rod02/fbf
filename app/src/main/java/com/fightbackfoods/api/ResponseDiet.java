package com.fightbackfoods.api;

import com.fightbackfoods.model.FoodGroup;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDiet extends Response {
    @SerializedName("diet_id")
    @Expose
    int dietId;

    public int getDietId() {
        return dietId;
    }

    public void setDietId(int dietId) {
        this.dietId = dietId;
    }
}
