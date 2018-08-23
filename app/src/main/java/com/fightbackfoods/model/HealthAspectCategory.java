package com.fightbackfoods.model;

import com.fightbackfoods.Api;
import com.fightbackfoods.api.ResponseHealthAspect;
import com.fightbackfoods.interfaces.Item;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class HealthAspectCategory implements Item {
    private static final String MENTAL = "1";
    private static final String PHYSICAL = "2";


    @SerializedName("health_aspect_category_id")
    String id;

    @SerializedName("health_aspect_id")
    String healthAspectId;


    @SerializedName("name")
    String name;

    private static List<HealthAspectCategory> physical_Cache = new ArrayList<>();

    private static List<HealthAspectCategory> mental_Cache = new ArrayList<>();


    public HealthAspectCategory() {
    }

    public static List<HealthAspectCategory> getPhysical_Cache() {
        return physical_Cache;
    }

    public static void setPhysical_Cache(List<HealthAspectCategory> physical_Cache) {
        HealthAspectCategory.physical_Cache = physical_Cache;
    }

    public static List<HealthAspectCategory> getMental_Cache() {
        return mental_Cache;
    }

    public static void setMental_Cache(List<HealthAspectCategory> mental_Cache) {
        HealthAspectCategory.mental_Cache = mental_Cache;
    }



    @Override
    public String getDescription() {
        return getName();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHealthAspectId() {
        return healthAspectId;
    }

    public void setHealthAspectId(String healthAspectId) {
        this.healthAspectId = healthAspectId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void get(Callback<ResponseHealthAspect> callback) {
        Api.getInstance().getHealthAspectCategories(callback);
    }


    public static void setCache(List<HealthAspectCategory> list) {
        try{
            physical_Cache = new ArrayList<>();
            mental_Cache = new ArrayList<>();
            for (HealthAspectCategory h: list){
                if(h.getHealthAspectId().equals(MENTAL))
                    mental_Cache.add(h);
                else physical_Cache.add(h);
            }

        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
