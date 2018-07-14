package com.fightbackfoods.api;

import com.fightbackfoods.model.CancerStage;
import com.fightbackfoods.model.CancerType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCancerStages extends Response {
    @SerializedName("cancerStages")
    @Expose
    List<CancerStage> cancerStages;


    public List<CancerStage> getCancerStages() {
        return cancerStages;
    }

    public void setCancerStages(List<CancerStage> cancerStages) {
        this.cancerStages = cancerStages;
    }
}
