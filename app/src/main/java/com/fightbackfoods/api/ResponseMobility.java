package com.fightbackfoods.api;

import com.fightbackfoods.model.Mobility;
import com.fightbackfoods.model.Treatment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMobility extends Response {
    @SerializedName("mobilities")
    @Expose
    List<Mobility> mobilities;

    public List<Mobility> getMobilities() {
        return mobilities;
    }

    public void setMobilities(List<Mobility> mobilities) {
        this.mobilities = mobilities;
    }
}
