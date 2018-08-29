package com.fightbackfoods.model;

import com.fightbackfoods.Api;
import com.fightbackfoods.api.ResponseArticle;
import com.fightbackfoods.api.ResponseDrink;
import com.fightbackfoods.utils.TextUtils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Callback;

public class Drink extends Item {

    private static final String TYPE_WATER = "1";
    @SerializedName("drink_id")
    String id;

    @SerializedName("user_id")
    String userId;

    @SerializedName("drink_type_id")
    String typeId;
    @SerializedName("amount")
    String amount;
    @SerializedName("drink_unit_id")
    String unitId;
    @SerializedName("unit")
    String unit;
    @SerializedName("type")
    String type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return type;
    }

    @Override
    public String getDescription() {
        return amount+unit;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public static void add(String unit, String type, int amount , String date, Callback<ResponseDrink> callback){
        Map<String, String> map = Token.toMap();
        map.put("unit", unit);
        map.put("type", type);
        map.put("amount", String.valueOf(amount));
        map.put("date", date);
        Api.getInstance().userDrinkAdd(map, callback);
    }

    public static void add(String unit, String type, int amount, Callback<ResponseDrink> callback){
        add(unit,type,amount, TextUtils.getDateFormatToday(),callback);
    }

    public static void add(int amount, String unitId, Callback<ResponseDrink> callback){
        add(unitId,TYPE_WATER,amount, TextUtils.getDateFormatToday(),callback);
    }

    public static void add(int amount, String unitId, String date,  Callback<ResponseDrink> callback){
        add(unitId,TYPE_WATER,amount,date,callback);
    }
    public static void unitsGet( Callback<ResponseDrink.Units> callback){
        Api.getInstance().drinkUnits(callback);
    }
    public static void get(String date, Callback<ResponseDrink> callback){
        Map<String, String> map = Token.toMap();
        if(date!=null) map.put("date", date);
        Api.getInstance().userDrinks(map,callback);
    }
    public static void get(Callback<ResponseDrink> callback){
       get(null,callback);
    }
    public static class Units implements com.fightbackfoods.interfaces.Item {

        @SerializedName("udu_id")
        String id;
        @SerializedName("name")
        String name;

        public Units() {
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDescription() {
            return name;
        }
    }
}
