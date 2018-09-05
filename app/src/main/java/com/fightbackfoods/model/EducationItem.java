package com.fightbackfoods.model;

import com.fightbackfoods.Api;
import com.fightbackfoods.R;
import com.fightbackfoods.api.ResponseArticle;
import com.fightbackfoods.interfaces.Item;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;


public class EducationItem implements Item {

    @SerializedName("article_category_id")
    String id;
    @SerializedName("article_type_id")
    String articleTypeId;

    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("count")
    int count;
    private String imageUrl;
    private int imageRes;

    private static List<EducationItem> cache = dummy();

    public EducationItem(String id, String name, String description, int total, int imageRes) {
       this.id = id;
        this.name = name;
        this.description = description;
        this.count = total;
        this.imageRes = imageRes;
    }

    public static List<EducationItem> dummy(){
        List<EducationItem> list = new ArrayList<>();
        list.add(new EducationItem("1","Food and Nutrition", "Your best asset is your health", 402, R.drawable.raw_educ1 ));
        list.add(new EducationItem("2","Mind Body and Soul", "Peace of mind is the answer", 233, R.drawable.raw_educ2 ));
        list.add(new EducationItem("3","Community", "Learn and live with harmony", 142, R.drawable.raw_educ3 ));
        list.add(new EducationItem("4","Documentaries", null, 213, R.drawable.raw_educ4 ));
        list.add(new EducationItem("5","Success Stories", null, 652, R.drawable.raw_educ5 ));

        return list;
    }

    public static List<EducationItem> getCache() {
        return cache;
    }

    public static void setCache(List<EducationItem> cache) {
        EducationItem.cache = cache;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticleTypeId() {
        return articleTypeId;
    }

    public void setArticleTypeId(String articleTypeId) {
        this.articleTypeId = articleTypeId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageRes() {
       this.imageRes = fromCategoryId();
        return imageRes;
    }

    private int fromCategoryId() {
        switch (id){
            case Article.Category.FOOD:
                return R.drawable.raw_educ1;

            case Article.Category.LIFESTYLE:
                return R.drawable.raw_educ2;

            case Article.Category.COMMUNITY:
                return R.drawable.raw_educ3;

            case Article.Category.DOCUMENTARIES:
                return R.drawable.raw_educ4;

            case Article.Category.STORIES:
                return R.drawable.raw_educ5;
            default:
                return R.drawable.raw_educ1;

        }
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public static void get(Callback<ResponseArticle.Category> callback){
        Api.getInstance().articlesEducation(callback);

    }
}
