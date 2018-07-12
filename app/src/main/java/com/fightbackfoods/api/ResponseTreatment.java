package com.fightbackfoods.api;

import com.fightbackfoods.model.CancerType;
import com.fightbackfoods.model.Treatment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTreatment extends Response {
    @SerializedName("treatments")
    @Expose
    List<Treatment> treatments;


    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }


}
