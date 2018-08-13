package com.fightbackfoods.model;

import com.fightbackfoods.interfaces.Item;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Nutrients implements Item{

    @SerializedName("nutrient_id")
    private String id;

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

    @SerializedName("nutrient_label")
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        try {
            return value.concat(unit);

        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return "";
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
