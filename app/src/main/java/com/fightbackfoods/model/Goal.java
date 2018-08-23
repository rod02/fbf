package com.fightbackfoods.model;

import com.google.gson.annotations.SerializedName;

public abstract class Goal extends Item {

    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("points")
    private String points;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
