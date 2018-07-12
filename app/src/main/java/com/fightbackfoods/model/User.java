package com.fightbackfoods.model;

import android.content.Context;
import android.content.SharedPreferences;


import com.fightbackfoods.Api;
import com.fightbackfoods.utils.TextUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {


    private static final String USER_SHARED_PREF = "usr_dtl";
    @SerializedName("user_id")
    @Expose
    private long userId;


    @SerializedName("user_detail_id")
    @Expose
    private long userDetailId;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("middle_name")
    @Expose
    private String middleName;


    @SerializedName("gender_id")
    @Expose
    private Long genderId;



    @SerializedName("weight")
    @Expose
    private int weight;

    @SerializedName("weight_unit_id")
    @Expose
    private int weightUnitId;


    @SerializedName("height")
    @Expose
    private int height;

    @SerializedName("height_unit_id")
    @Expose
    private int heightUnitId;

    @SerializedName("zip_code")
    @Expose
    private String zipCode;


    @SerializedName("cancer_type_id")
    @Expose
    private String cancerTypeId;


    @SerializedName("cancer_stage_id")
    @Expose
    private String cancerStageId;

    @SerializedName("mobility_id")
    @Expose
    private String mobilityId;


    @SerializedName("treatment_id")
    @Expose
    private String treatmentId;


    @SerializedName("food_restriction_id")
    @Expose
    private String foodRestrictionId;


    @SerializedName("birthday")
    @Expose
    private String birthday;                        //yyyy-MM-dd

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("created_at")
    @Expose
    private long createdAt;


    @SerializedName("updated_at")
    @Expose
    private long updatedAt;

    @SerializedName("email")
    @Expose
    private String email;

    public User() {

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserDetailId() {
        return userDetailId;
    }

    public void setUserDetailId(long userDetailId) {
        this.userDetailId = userDetailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCancerTypeId() {
        return cancerTypeId;
    }

    public void setCancerTypeId(String cancerTypeId) {
        this.cancerTypeId = cancerTypeId;
    }

    public String getCancerStageId() {
        return cancerStageId;
    }

    public void setCancerStageId(String cancerStageId) {
        this.cancerStageId = cancerStageId;
    }

    public String getMobilityId() {
        return mobilityId;
    }

    public void setMobilityId(String mobilityId) {
        this.mobilityId = mobilityId;
    }

    public String getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(String treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getFoodRestrictionId() {
        return foodRestrictionId;
    }

    public void setFoodRestrictionId(String foodRestrictionId) {
        this.foodRestrictionId = foodRestrictionId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        if(avatar!=null){
            if(!avatar.contains("http"))avatar ="http://"+avatar;
        }
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updated_at) {
        this.updatedAt = updated_at;
    }

    public int getWeightUnitId() {
        return weightUnitId;
    }

    public void setWeightUnitId(int weightUnitId) {
        this.weightUnitId = weightUnitId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeightUnitId() {
        return heightUnitId;
    }

    public void setHeightUnitId(int heightUnitId) {
        this.heightUnitId = heightUnitId;
    }

    public User(String email, String name, String avatar) {
        this.firstName = name;
        this.avatar = avatar;

    }



    /**
     * Setter for the User that is currently logged in to the application.
     * @param user The user that is currently logged in to the application.
     */
    public static void setCurrentUser(User user) {
        SharedPreferences sharedPref =  Api.getApplicationContext().getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("first_name", user.getFirstName());
        editor.putString("email", user.getEmail());
        editor.putLong("user_id", user.getUserId());
        editor.putLong("user_detail_id", user.getUserDetailId());

        editor.putString("last_name", user.getLastName());
        editor.putString("middle_name", user.getMiddleName());

        editor.putString("avatar", user.getAvatar());

        editor.putString("zip_code", user.getZipCode());

        try {
            editor.putLong("gender_id", user.getGenderId());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        editor.putString("cancer_stage_id", user.getCancerStageId());

        editor.putLong("created_at", user.getCreatedAt());
        editor.putLong("updated_at",user.getUpdatedAt());
        editor.putString("birthday", user.getBirthday());
        editor.putString("cancer_type_id", user.getCancerTypeId());
        editor.putString("mobility_id", user.getMobilityId());
        editor.putString("food_restriction_id", user.getFoodRestrictionId());
        editor.putString("treatment_id", user.getTreatmentId());

        editor.apply();
    }

    /**
     * Getter for the User that is currently logged in to the application.
     * @return The User that is currently logged in to the application.
     */
    public static User getCurrentUser()
    {
        try {
            SharedPreferences sharedPref = Api.getApplicationContext().getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE);
            return User.fromSharedPref(sharedPref);

        }catch (NullPointerException e){
            return new User();
        }

    }
    private static User fromSharedPref(SharedPreferences sharedPref){

        User user = new User();

       user.setEmail(sharedPref.getString("email", ""));
        user.setFirstName(sharedPref.getString("first_name",""));
        user.setLastName(sharedPref.getString("last_name",""));
        user.setMiddleName(sharedPref.getString("middle_name",""));
        user.setBirthday(sharedPref.getString("birthday",""));
        user.setGenderId(sharedPref.getLong("gender_id",0));
        user.setZipCode(sharedPref.getString("zip_code",""));
        user.setAvatar(sharedPref.getString("avatar",""));
        user.setUserId(sharedPref.getLong("user_id", 0));
        user.setUserDetailId(sharedPref.getLong("user_detail_id", 0));
        user.setCancerStageId(sharedPref.getString("cancer_stage_id", ""));
        user.setCancerTypeId(sharedPref.getString("cancer_type_id", ""));
        user.setMobilityId(sharedPref.getString("mobility_id", ""));
        user.setTreatmentId(sharedPref.getString("treatment_id", ""));
        user.setFoodRestrictionId(sharedPref.getString("food_restriction_id", ""));
        user.setCreatedAt(sharedPref.getLong("created_at", 0));
        user.setUpdatedAt(sharedPref.getLong("updated_at", 0));
        return user;
    }
    public static long getCurrentUserId() {
        final SharedPreferences sharedPref = Api.getApplicationContext().getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE);
        return sharedPref.getLong("user_id", 0);
    }
    /**
     * Getter for the User that is currently logged in to the application.
     * @return The User that is currently logged in to the application.
     */
    public static void clearPref()   {
        SharedPreferences.Editor editor = getPref();

        editor.clear();
        editor.apply();

    }

    private static SharedPreferences.Editor getPref() {
        return Api.getApplicationContext().getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE).edit();
    }

    public String getSimpleName() {
        String s = getFirstName() + "";
        s += getMiddleName()==null? "": " " +getMiddleName();
        return s + " " +getLastName();
    }

    public void setAvatar(String imgUrl, boolean b) {
        this.setAvatar(imgUrl);
        if(b){
            SharedPreferences.Editor editor = getPref();
            editor.putString("avatar", imgUrl);
            editor.apply();
        }
    }
}
