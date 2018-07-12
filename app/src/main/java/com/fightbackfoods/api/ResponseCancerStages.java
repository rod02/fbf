package com.fightbackfoods.api;

import com.fightbackfoods.model.CancerType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCancerStages extends Response {
    @SerializedName("cancerStages")
    @Expose
    List<CancerType> cancerStages;


    public List<CancerType> getCancerStages() {
        return cancerStages;
    }

    public void setCancerStages(List<CancerType> cancerStages) {
        this.cancerStages = cancerStages;
    }
}
