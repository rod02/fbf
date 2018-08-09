package com.fightbackfoods.api;

import com.fightbackfoods.model.Food;
import com.fightbackfoods.model.FoodByGroupList;
import com.fightbackfoods.model.FoodGroup;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseFoodByGroup extends Response {
    @SerializedName("foods")
    @Expose
    FoodByGroupList foods;

    public FoodByGroupList getFoods() {
        return foods;
    }

    public void setFoods(FoodByGroupList foods) {
        this.foods = foods;
    }

    public List<Food> getList() {
        try {
            return foods.getList().getItem();

        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public int getListSize() {
        return getList().size();
    }
}
