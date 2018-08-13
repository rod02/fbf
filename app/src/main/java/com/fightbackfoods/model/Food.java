package com.fightbackfoods.model;

import com.fightbackfoods.Api;
import com.fightbackfoods.App;
import com.fightbackfoods.api.ResponseFoodByGroup;
import com.fightbackfoods.api.ResponseFoodList;
import com.fightbackfoods.api.ResponseNutrients;
import com.fightbackfoods.interfaces.Item;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Callback;

public class Food implements Item, Serializable {

    @SerializedName("offset")
    private String offset;
    @SerializedName("group")
    private String group;
    @SerializedName("ndbno")
    private String ndbno;
    @SerializedName("name")
    private String name;
    @SerializedName("ds")
    private String ds;

    @SerializedName("manu")
    private String manu;

    @SerializedName("ru")
    private String ru;


    @SerializedName("ing")
    private Ingredients ing;

    @SerializedName("nutrients")
    private List<Nutrients> nutrients;

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getNdbno() {
        return ndbno;
    }

    public void setNdbno(String ndbno) {
        this.ndbno = ndbno;
    }

    @Override
    public String getId() {
        return "0";
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return getGroup();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getManu() {
        return manu;
    }

    public void setManu(String manu) {
        this.manu = manu;
    }

    public String getRu() {
        return ru;
    }

    public void setRu(String ru) {
        this.ru = ru;
    }

    public Ingredients getIng() {
        return ing;
    }

    public void setIng(Ingredients ing) {
        this.ing = ing;
    }

    public List<Nutrients> getNutrients() {
        return nutrients;
    }

    public void setNutrients(List<Nutrients> nutrients) {
        this.nutrients = nutrients;
    }

    public static void search(String query, int offset, Callback<ResponseFoodList> callback) {
        Map<String, String> map = Token.toMap();
        map.put("query",query);
        map.put("offset", String.valueOf(offset));
        Api.getInstance().foodSearch(map,callback);
    }
    public static void searchByGroup(String query, int offset, String groupId, Callback<ResponseFoodByGroup> callback) {
        Map<String, String> map = createSearchMap(query,offset);
        map.put("fgId",groupId);
        Api.getInstance().foodGroupSearch(map,callback);
    }

    private static  Map<String, String> createSearchMap(String query, int offset){
        Map<String, String> map = Token.toMap();
        map.put("query",query);
        map.put("offset", String.valueOf(offset));
        return map;
    }

    public static void report(String ndbno, Callback<ResponseNutrients> callback) {
        Map<String, String> map = Token.toMap();
        map.put("foodId", ndbno);
        Api.getInstance().foodReport(map, callback);
    }
}
