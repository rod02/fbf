package com.fightbackfoods.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodByGroup {

    @SerializedName("q")
    private String q;
    @SerializedName("sr")
    private String sr;
    @SerializedName("ds")
    private String ds;
    @SerializedName("start")
    private int start;
    @SerializedName("end")
    private int end;
    @SerializedName("total")
    private int total;
    @SerializedName("sort")
    private String sort;
    @SerializedName("group")
    private String group;
    @SerializedName("item")
    private List<Food> item;


    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<Food> getItem() {
        return item;
    }

    public void setItem(List<Food> item) {
        this.item = item;
    }
}
