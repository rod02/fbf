package com.fightbackfoods.model;

import android.os.RemoteCallbackList;

import com.fightbackfoods.Api;
import com.fightbackfoods.api.ResponseJournal;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

import retrofit2.Callback;

public class JournalSuggestion extends  Item{


    @SerializedName("journal_suggestion_id")
    private String id;

    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;

    @SerializedName("alternative_link")
    private String alternativeLink;


    @SerializedName("featured_image_url")
    private String imageUrl;


    @SerializedName("health_aspect_id")
    private String healthAspectId;

    @SerializedName("health_aspect_category_id")
    private String healthAspectCategoryId;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public String getDescription() {
        return content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAlternativeLink() {
        return alternativeLink;
    }

    public void setAlternativeLink(String alternativeLink) {
        this.alternativeLink = alternativeLink;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHealthAspectId() {
        return healthAspectId;
    }

    public void setHealthAspectId(String healthAspectId) {
        this.healthAspectId = healthAspectId;
    }

    public String getHealthAspectCategoryId() {
        return healthAspectCategoryId;
    }

    public void setHealthAspectCategoryId(String healthAspectCategoryId) {
        this.healthAspectCategoryId = healthAspectCategoryId;
    }


    public static void get(String mentalCategory, String physicalCategory, Callback<ResponseJournal> callback) {
        Map<String, String> map = Token.toMap();
        if(mentalCategory!=null) map.put("emotionalStatus", mentalCategory);
        if(physicalCategory!=null) map.put("physicalStatus", physicalCategory);

        Api.getInstance().journalSuggestions(map, callback);
    }
}
