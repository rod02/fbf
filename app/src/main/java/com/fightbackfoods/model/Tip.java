package com.fightbackfoods.model;

import com.fightbackfoods.Api;
import com.fightbackfoods.api.ResponseTip;
import com.fightbackfoods.interfaces.Item;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Callback;

public class Tip {

    @SerializedName("tip_id")
    String id;
    @SerializedName("description")
    String description;
    @SerializedName("category_id")
    String categoryId;
    @SerializedName("sub_category_id")
    String subCategoryId;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("updated_at")
    String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
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

    public static void fetch(Callback<ResponseTip> callback) {
        Map<String, String> map = Token.toMap();
        //map.put("category", category);
        Api.getInstance().tips(map, callback);
    }

    public static void fetch(String categoryId, String subCategoryId, Callback<ResponseTip> callback) {
        Map<String, String> map = Token.toMap();
        if(categoryId!=null) map.put("category", categoryId);
        if(subCategoryId!=null) map.put("subCategory", subCategoryId);
        Api.getInstance().tips(map, callback);
    }

    public static String getRandom(List<Tip> tips) {
        try{

           return tips.get(random.nextInt(tips.size())).getDescription();
        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        if(tips==null || tips.isEmpty())
        return null;
        else return tips.get(0).getDescription();
    }

    final static Random random = new Random();

}
