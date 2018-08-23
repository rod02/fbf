package com.fightbackfoods.model;

import android.support.v7.widget.AppCompatSpinner;

import com.facebook.CallbackManager;
import com.fightbackfoods.Api;
import com.fightbackfoods.App;
import com.fightbackfoods.R;
import com.fightbackfoods.api.ResponseDiet;
import com.fightbackfoods.utils.TextUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Map;

import retrofit2.Callback;

public class UserDiet {
    @SerializedName("user_diet_id")
    private String id;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("meal_accordance_id")
    private String meal_accordance_id;
    @SerializedName("food_id")
    private String food_id;
    @SerializedName("measure_label")
    private String measure_label;
    @SerializedName("serving")
    private String serving;
    @SerializedName("drink_id")
    private String drink_id;
    @SerializedName("is_removed")
    private String is_removed;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("food_info")
    private FoodInfo foodInfo;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMeal_accordance_id() {
        return meal_accordance_id;
    }

    public void setMeal_accordance_id(String meal_accordance_id) {
        this.meal_accordance_id = meal_accordance_id;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public String getMeasure_label() {
        if(measure_label ==null)return "piece";
        return measure_label;
    }

    public void setMeasure_label(String measure_label) {
        this.measure_label = measure_label;
    }

    public String getServing() {
        if(serving==null)return "1";
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public String getDrink_id() {
        return drink_id;
    }

    public void setDrink_id(String drink_id) {
        this.drink_id = drink_id;
    }

    public String getIs_removed() {
        return is_removed;
    }

    public void setIs_removed(String is_removed) {
        this.is_removed = is_removed;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public FoodInfo getFoodInfo() {
        return foodInfo;
    }

    public void setFoodInfo(FoodInfo foodInfo) {
        this.foodInfo = foodInfo;
    }

    public String getFoodName() {
        return getFoodInfo().getName();
    }

    public String getCalories(){
        try {
            return getFoodInfo().getNutrients().getCalories().getValue(getMeasure_label(), Integer.parseInt(getServing()));
        }catch (NullPointerException e){
            e.printStackTrace();
        }
       return "100 Cal";
    }

    public String getServingsCal() {
        try {
            return Api.getApplicationContext().getResources()
                    .getString(R.string.serving_cal_n, getServing(), getCalories());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return "2 servings / 100 Cal";

    }

    public static void get(String date, Callback<ResponseDiet> callback) {
        if(date==null || date.equals(""))date = TextUtils.getDateFormatToday();
        Map<String, String> map = Token.toMap();
        map.put("date", date); //yyyy-MM-dd
        Api.getInstance().userDiet(map, callback);


    }

    public Food getFood() {
        return new Food(getFood_id(),getFoodName());
    }
}
