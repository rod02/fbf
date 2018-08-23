package com.fightbackfoods.api;

import com.fightbackfoods.model.Tip;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTip extends Response {


    @SerializedName("tips")
    private List<Tip> tips;


    public List<Tip> getTips() {
        return tips;
    }

    public void setTips(List<Tip> tips) {
        this.tips = tips;
    }
}
