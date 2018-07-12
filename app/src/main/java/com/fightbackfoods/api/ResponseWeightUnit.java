package com.fightbackfoods.api;

import com.fightbackfoods.model.HeightUnit;
import com.fightbackfoods.model.WeightUnit;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseWeightUnit extends Response {
    @SerializedName("weightUnits")
    @Expose
    List<WeightUnit> weightUnits;


    public List<WeightUnit> getWeightUnits() {
        return weightUnits;
    }

    public void setWeightUnits(List<WeightUnit> weightUnits) {
        this.weightUnits = weightUnits;
    }
}
