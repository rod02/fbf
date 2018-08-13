package com.fightbackfoods.interfaces;

import com.fightbackfoods.api.ResponseArticle;
import com.fightbackfoods.api.ResponseCancerStages;
import com.fightbackfoods.api.ResponseCancerType;
import com.fightbackfoods.api.ResponseFoodByGroup;
import com.fightbackfoods.api.ResponseFoodGroup;
import com.fightbackfoods.api.ResponseFoodList;
import com.fightbackfoods.api.ResponseHeightUnit;
import com.fightbackfoods.api.ResponseMobility;
import com.fightbackfoods.api.ResponseNutrients;
import com.fightbackfoods.api.ResponseTreatment;
import com.fightbackfoods.api.ResponseUser;
import com.fightbackfoods.api.ResponseGender;
import com.fightbackfoods.api.ResponseVisibility;
import com.fightbackfoods.api.ResponseWeightUnit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiConnect {


    @FormUrlEncoded
    @POST("login")
    Call<ResponseUser> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/password/reset")
    Call<ResponseUser> resetPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("signup")
    Call<ResponseUser> signUp(@FieldMap Map<String ,String> user);


    @FormUrlEncoded
    @POST("user/avatar/update")
    Call<ResponseUser> updateAvatar(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("fb/signin")
    Call<ResponseUser> fbSignIn(@FieldMap Map<String, String> map);

    @GET("genders")
 //   @FormUrlEncoded
    Call<ResponseGender> getGenders(@QueryMap Map<String, String> map);

    @GET("cancer/types")
    Call<ResponseCancerType> getCancerTypes(@QueryMap Map<String, String> map);

    @GET("cancer/stages")
    Call<ResponseCancerStages> getCancerStages(@QueryMap Map<String, String> map);

    @GET("treatments")
    Call<ResponseTreatment> getTreatments(@QueryMap Map<String, String> map);

    @GET("mobilities")
    Call<ResponseMobility> getMobilities(@QueryMap Map<String, String> map);

    @GET("height-units")
    Call<ResponseHeightUnit> getHeightUnits(@QueryMap Map<String, String> map);

    @GET("weight-units")
    Call<ResponseWeightUnit> getWeightUnits(@QueryMap Map<String, String> map);

    @GET("visibilities")
    Call<ResponseVisibility> getVisibilities(@QueryMap Map<String, String> optionsToken);

    @GET("food/groups")
    Call<ResponseFoodGroup> fetchFoodGroups(@QueryMap Map<String, String> token);

    @GET("food/search")
    Call<ResponseFoodList> foodSearch(@QueryMap Map<String, String> map);

    @GET("food/group/search")
    Call<ResponseFoodByGroup> foodGroupSearch(@QueryMap Map<String, String> map);

    @GET("articles")
    Call<ResponseArticle> articleFetchAll(@QueryMap Map<String, String> map);

    @GET("food/report/v2")
    Call<ResponseNutrients> foodReport(@QueryMap Map<String, String> map);
}
