package com.fightbackfoods.api;

import com.fightbackfoods.model.HeightUnit;
import com.fightbackfoods.model.Treatment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseHeightUnit extends Response {
    @SerializedName("heightUnits")
    @Expose
    List<HeightUnit> heightUnits;


    public List<HeightUnit> getHeightUnits() {
        return heightUnits;
    }

    public void setTreatments(List<HeightUnit> treatments) {
        this.heightUnits = heightUnits;
    }


}
