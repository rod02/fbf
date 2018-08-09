package com.fightbackfoods.model;

import com.fightbackfoods.api.ResponseError;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodByGroupList {

    @SerializedName("list")
    private FoodByGroup list;


    @SerializedName("errors")
    private ResponseError errors;


    public FoodByGroup getList() {
        return list;
    }

    public void setList(FoodByGroup list) {
        this.list = list;
    }

    public ResponseError getErrors() {
        return errors;
    }

    public void setErrors(ResponseError errors) {
        this.errors = errors;
    }
}