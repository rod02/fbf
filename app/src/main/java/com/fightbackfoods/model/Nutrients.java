package com.fightbackfoods.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Nutrients {

    @SerializedName("nutrient_id")
    private String nutrient_id;

    @SerializedName("name")
    private String name;


    @SerializedName("derivation")
    private String derivation;


    @SerializedName("group")
    private String group;

    @SerializedName("unit")
    private String unit;

    @SerializedName("value")
    private String value;

    @SerializedName("measures")
    private List<Measure> measures;

    public String getNutrient_id() {
        return nutrient_id;
    }

    public void setNutrient_id(String nutrient_id) {
        this.nutrient_id = nutrient_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDerivation() {
        return derivation;
    }

    public void setDerivation(String derivation) {
        this.derivation = derivation;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }
}
