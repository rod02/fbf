package com.fightbackfoods.model;

import com.fightbackfoods.Api;
import com.fightbackfoods.api.ResponseArticle;
import com.fightbackfoods.utils.Validate;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Callback;

public class Article extends Item {

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

    private static HashMap<String, List<Article>> cacheMap = new HashMap<>();

    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public String getDescription() {
        return getFeaturedImage();
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

    public static void get(String type, String category, Callback<ResponseArticle> callback) {
        try {
            Map<String, String> map = Token.toMap();
            map.put("type", type);
            if(category!=null) map.put("category", category);
            Api.getInstance().articlesV2(map, callback);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public String getImageUrl() {
        String imagePath = Validate.path(getFeaturedImage());
        if(Validate.isNullString(imagePath))
            imagePath ="";
      /*  if(!Validate.isNullString(imagePath)) {
            imagePath = Validate.path(imagePath);
        }*/
        return imagePath;
    }

    public static void getComments(Callback<ResponseArticle> callback) {
    }

    public static void setCache(String category, List<Article> articles) {
        cacheMap.put(category, articles);
    }

    public static List<Article> getCache(String category) {
       return cacheMap.get(category);
    }

    public static class TYPE {
        public String id;
        public static final String EDUCATION ="1";
        public static final String RECIPE ="2";

    }


    public static class Category extends EducationItem {


        public static final String FOOD ="1";
        public static final String LIFESTYLE ="2";

        public static final String COMMUNITY ="3";
        public static final String DOCUMENTARIES ="4";
        public static final String STORIES ="5";



        public Category(String id,String name, String description, int total, int imageRes) {
            super(id, name, description, total, imageRes);
        }

    }
}
