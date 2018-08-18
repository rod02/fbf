package com.fightbackfoods.model;

import com.fightbackfoods.Api;
import com.fightbackfoods.api.ResponseArticle;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Callback;

public class Article implements Serializable {

    private static List<Article> featuredCache = new ArrayList<>();

    @SerializedName("article_id")
    String id;
    @SerializedName("title")
    String title;
    @SerializedName("sub_title")
    String subtitle;
    @SerializedName("content")
    String content;
    @SerializedName("featured_image")
    String featuredImage;
    @SerializedName("article_type_id")
    String typeId;
    @SerializedName("article_rating_id")
    private String ratingId;
    @SerializedName("created_by")
    private String createdBy;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
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

    public static void featured(Callback<ResponseArticle> callback) {
        try {
            Map<String, String> map = Token.toMap();
            map.put("articleTypeId", TYPE.EDUCATION);
            map.put("featured", "1");

          //  if(featuredCache.isEmpty())
                Api.getInstance().articles(map, callback);
        }catch (NullPointerException e){
            e.printStackTrace();
            featuredCache = new ArrayList<>();
            Api.getInstance().articleFetchAll(callback);
        }

    }

    public static void setCache(List<Article> articles) {
        try {
            featuredCache.clear();
            featuredCache.addAll(articles);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public static List<Article> getFeaturedCache() {
        return featuredCache;
    }

    public static void setFeaturedCache(List<Article> featuredCache) {
        Article.featuredCache = featuredCache;
    }

    public static class TYPE {
        public String id;
        public static final String EDUCATION ="1";
        public static final String RECIPE ="2";

    }

}
