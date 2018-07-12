package com.fightbackfoods.api;

import android.opengl.Visibility;

import com.fightbackfoods.model.Mobility;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseVisibility extends Response {
    @SerializedName("visibilities")
    @Expose
    List<Visibility> visibilities;

    public List<Visibility> getVisibilities() {
        return visibilities;
    }

    public void setVisibilities(List<Visibility> visibilities) {
        this.visibilities = visibilities;
    }
}
