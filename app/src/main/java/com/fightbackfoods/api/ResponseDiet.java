package com.fightbackfoods.api;

import com.fightbackfoods.model.FoodGroup;
import com.fightbackfoods.model.UserDiet;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDiet extends Response {
    @SerializedName("diet_id")
    @Expose
    int dietId;

    @SerializedName("user_diet")
    public List<UserDiet> userDiet;

    public int getDietId() {
        return dietId;
    }

    public void setDietId(int dietId) {
        this.dietId = dietId;
    }

    public List<UserDiet> getUserDiet() {
        return userDiet;
    }

    public void setUserDiet(List<UserDiet> userDiet) {
        this.userDiet = userDiet;
    }
}
