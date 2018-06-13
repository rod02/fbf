package com.fightbackfoods.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.fightbackfoods.Api;
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
    private long updated_at;

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

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
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

        editor.putString("last_name", user.getLastName());
        editor.putString("avatar", user.getAvatar());
      //  editor.putLong("gender", TextUtils.parseLong(user.getGenderId()));
        editor.putLong("createdAt", user.getCreatedAt());
        editor.putLong("updatedAt",user.getUpdated_at());

        editor.apply();
    }

    /**
     * Getter for the User that is currently logged in to the application.
     * @return The User that is currently logged in to the application.
     */
    public static User getCurrentUser()
    {
        final SharedPreferences sharedPref = Api.getApplicationContext().getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE);

        return User.fromSharedPref(sharedPref);
    }
    private static User fromSharedPref(SharedPreferences sharedPref){

        User user = new User();

       user.setEmail(sharedPref.getString("email", ""));
        user.setFirstName(sharedPref.getString("first_name",""));
        user.setLastName(sharedPref.getString("last_name",""));
        user.setAvatar(sharedPref.getString("avatar",""));
        user.setUserId(sharedPref.getLong("user_id", 0));

        return user;
    }
    public static String getCurrentUserId() {
        final SharedPreferences sharedPref = Api.getApplicationContext().getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE);
        return sharedPref.getString("fbid", "");
    }
    /**
     * Getter for the User that is currently logged in to the application.
     * @return The User that is currently logged in to the application.
     */
    public static void clearPref()   {
        final SharedPreferences sharedPref = Api.getApplicationContext().getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();

    }

}
