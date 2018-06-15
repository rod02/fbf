package com.fightbackfoods;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fightbackfoods.interfaces.ApiConnect;
import com.fightbackfoods.interfaces.Response;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    ApiConnect apiConnect;
    Retrofit retrofit ;
    private static Context applicationContext;

    private static final long TIMEOUT_CONNECT = 60;

    private static final String BASE_URL = "http://fightbackfoods.eautobid.com/api/";

    public Api(@NonNull Context context) {
        this.applicationContext = context;

       /* HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);*/

        OkHttpClient client = new OkHttpClient.Builder()

            //    .addInterceptor(interceptor)
                .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiConnect = retrofit.create(ApiConnect.class);
    }



    public static void initialized(Context applicationContext) {
        if(isInitialized()){
            return;
        }
        instance = new Api(applicationContext);
    }

    static boolean isInitialized() {
        return instance!=null;
    }

    private static Api instance;

    public static Api getInstance() {
        if(instance!=null)
            return instance;
        return new Api(Api.getApplicationContext());
    }

    public static Context getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }


    public void login(String email, String password, Callback<Response> callback){
        Call<Response> add = apiConnect.login(email, password);

        add.enqueue(callback);
    }

    public void resetPassword(String email, Callback<Response> callback) {
        apiConnect.resetPassword(email).enqueue(callback);
    }

    public void signUp(Map<String, String> user, Callback<Response> callback) {
        apiConnect.signUp(user).enqueue(callback);
    }

    public void updateAvatar(String userId, String image64, Callback<JSONObject> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("b64img", image64);
        apiConnect.updateAvatar(map).enqueue(callback);
    }

    public void updateAvatar(Map<String, String> map, Callback<JSONObject> callback) {
        apiConnect.updateAvatar(map).enqueue(callback);
    }
}
