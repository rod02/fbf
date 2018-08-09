package com.fightbackfoods.model;

import com.google.gson.annotations.SerializedName;

public class Measure {


    @SerializedName("label")
    private String label;


    @SerializedName("eqv")
    private double eqv;


    @SerializedName("eunit")
    private String eunit;

    @SerializedName("qty")
    private double qty;

    @SerializedName("value")
    private String value;

    public Measure(String label, double eqv, String eunit, double qty, String value) {
        this.label = label;
        this.eqv = eqv;
        this.eunit = eunit;
        this.qty = qty;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getEqv() {
        return eqv;
    }

    public void setEqv(double eqv) {
        this.eqv = eqv;
    }

    public String getEunit() {
        return eunit;
    }

    public void setEunit(String eunit) {
        this.eunit = eunit;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
