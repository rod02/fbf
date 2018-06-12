package com.fightbackfoods.interfaces;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiConnect {


    @FormUrlEncoded
    @POST("login")
    Call<Response> login(@Field("email") String email, @Field("password") String password);



}
