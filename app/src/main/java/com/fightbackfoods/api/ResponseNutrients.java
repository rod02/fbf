package com.fightbackfoods.api;

import com.fightbackfoods.model.Mobility;
import com.fightbackfoods.model.Nutrients;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseNutrients extends Response {
    @SerializedName("nutrients")
    @Expose
    List<Nutrients> nutrients;


    public List<Nutrients> getNutrients() {
        return nutrients;
    }

    public void setNutrients(List<Nutrients> nutrients) {
        this.nutrients = nutrients;
    }

    public boolean isEmpty(){
        try{
            getNutrients().isEmpty();

        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isSuccessful() {
        try {
            return success.equalsIgnoreCase("true");
        }catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }
}
