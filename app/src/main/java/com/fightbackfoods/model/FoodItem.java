package com.fightbackfoods.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FoodItem {


    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("qty")
    private String qty;
    @SerializedName("value")
    private String value;


    public FoodItem(int id, String name, String qty, String value) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static List<FoodItem> createDummy(){
        List<FoodItem> dummy = new ArrayList<>();
        dummy.add(new FoodItem(1,"Egg", "1 Large", "72"));
        dummy.add(new FoodItem(1,"Egg's Benedict", "Egg generic 1 order", "422"));
        dummy.add(new FoodItem(1,"Bread, Egg", "1 Oz", "82"));
        dummy.add(new FoodItem(1,"Egg Whites", "100g", "48"));
        dummy.add(new FoodItem(1,"Bagels Egg", "1 Large", "79"));

        return dummy;
    }

}
