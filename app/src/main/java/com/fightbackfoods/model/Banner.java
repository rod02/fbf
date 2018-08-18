package com.fightbackfoods.model;

import com.fightbackfoods.Api;
import com.fightbackfoods.api.ResponseBanner;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Callback;

public class Banner implements Serializable {
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
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    private static List<Banner> cache = new ArrayList<>();

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    public static void fetch(Callback<ResponseBanner> callback) {
        Map<String, String> map = Token.toMap();
        map.put("category", Category.FOODTRACKER);
        Api.getInstance().banners(map,callback);
    }

    public static class Category {
        public String id;
        public static final String EDUCATION ="3";
        public static final String FOODTRACKER ="1";
        public static final String LIFESTYLE ="2";
        public static final String COMMUNITY ="4";

        public static final String RECIPE ="5";

    }

}
