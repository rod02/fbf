package com.fightbackfoods.api;

import com.fightbackfoods.model.Gender;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGender extends Response {
    @SerializedName("genders")
    @Expose
    List<Gender> genders;

    public List<Gender> getGenders() {
        return genders;
    }

    public void setGenders(List<Gender> genders) {
        this.genders = genders;
    }
}
