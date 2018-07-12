package com.fightbackfoods.api;

import com.fightbackfoods.model.CancerType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCancerType extends Response {
    @SerializedName("cancerTypes")
    @Expose
    List<CancerType> cancerTypes;


    public List<CancerType> getCancerTypes() {
        return cancerTypes;
    }

    public void setCancerTypes(List<CancerType> cancerTypes) {
        this.cancerTypes = cancerTypes;
    }
}
