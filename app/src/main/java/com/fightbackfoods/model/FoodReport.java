package com.fightbackfoods.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodReport {


    @SerializedName("sr")
    private String sr;


    @SerializedName("type")
    private String type;

    @SerializedName("food")
    private String food;

    @SerializedName("footnotes")
    private List<Footnote> footnotes;



    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public List<Footnote> getFootnotes() {
        return footnotes;
    }

    public void setFootnotes(List<Footnote> footnotes) {
        this.footnotes = footnotes;
    }
}
