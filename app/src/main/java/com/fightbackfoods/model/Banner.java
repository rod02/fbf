package com.fightbackfoods.model;

import com.fightbackfoods.Api;
import com.fightbackfoods.api.ResponseBanner;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Callback;

public class Banner extends Item {
    @SerializedName("banner_id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("img_url")
    String img_url;
    @SerializedName("link")
    String link;
    @SerializedName("category_id")
    String category_id;
    @SerializedName("sub_category_id")
    String sub_category_id;
    @SerializedName("created_by")
    private String createdBy;
    @SerializedName("created_at")

    private static List<Banner> cache = new ArrayList<>();

    private static Map<String, List<Banner>> cache2 = new HashMap<>();

    public static List<Banner> getCache() {
        return cache;
    }

    public static void setCache(List<Banner> cache) {
        Banner.cache = cache;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public static void fetch(Callback<ResponseBanner> callback) {
       fetch(callback, Category.FOODTRACKER);
    }

    public static void fetch(Callback<ResponseBanner> callback, String category) {
        Map<String, String> map = Token.toMap();
        map.put("category", category);
        Api.getInstance().banners(map,callback);
    }

    public static List<Banner> getCache(String category) {
        try {
            return cache2.get(category);
        }catch (NullPointerException e){e.printStackTrace();}
        return new ArrayList<>();
    }

    public static void setCache(String category, List<Banner> banners) {
        cache2.put(category, banners);
    }


    public static void fetch(String category, String subCategory, Callback<ResponseBanner> callback) {
        Map<String, String> map = Token.toMap();
        map.put("category", category);
        if(subCategory!=null && !(subCategory.equals("")))
            map.put("subCategory", subCategory);
        Api.getInstance().banners(map,callback);
    }

    public static Map<String, List<Banner>> getCache2() {
        return cache2;
    }

    public static void setCache2(Map<String, List<Banner>> cache2) {
        Banner.cache2 = cache2;
    }
}
