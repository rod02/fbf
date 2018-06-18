package com.fightbackfoods.interfaces;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiConnect {


    @FormUrlEncoded
    @POST("login")
    Call<Response> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/password/reset")
    Call<Response> resetPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("signup")
    Call<Response> signUp(@FieldMap Map<String ,String> user);


    @FormUrlEncoded
    @POST("user/avatar/update")
    Call<Response> updateAvatar(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("fb/signin")
    Call<Response> fbSignIn(@FieldMap Map<String, String> map);
}
