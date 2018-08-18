package com.fightbackfoods;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fightbackfoods.api.Response;
import com.fightbackfoods.api.ResponseArticle;
import com.fightbackfoods.api.ResponseBanner;
import com.fightbackfoods.api.ResponseCancerStages;
import com.fightbackfoods.api.ResponseCancerType;
import com.fightbackfoods.api.ResponseDiet;
import com.fightbackfoods.api.ResponseFoodByGroup;
import com.fightbackfoods.api.ResponseFoodGroup;
import com.fightbackfoods.api.ResponseFoodList;
import com.fightbackfoods.api.ResponseHeightUnit;
import com.fightbackfoods.api.ResponseMobility;
import com.fightbackfoods.api.ResponseNutrients;
import com.fightbackfoods.api.ResponseTreatment;
import com.fightbackfoods.api.ResponseVisibility;
import com.fightbackfoods.api.ResponseWeightUnit;
import com.fightbackfoods.interfaces.ApiConnect;
import com.fightbackfoods.api.ResponseUser;
import com.fightbackfoods.api.ResponseGender;
import com.fightbackfoods.model.Token;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {


    ApiConnect apiConnect;
    Retrofit retrofit ;
    private static Context applicationContext;
    private Map<String, String> optionsToken = getOptionsToken();
    private Map<String, String> getOptionsToken(){
        Map<String, String> map = new HashMap<>();
        map.put("userId", "1");
        map.put("token", "5aba5b26e93dbasd");
        return map;
    }

    private static final long TIMEOUT_CONNECT = 60;
    public static final int MAX_RETRY_COUNT = 3;

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
               // .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();
        apiConnect = retrofit.create(ApiConnect.class);
    }



    public static void initialized(Context applicationContext) {
        if(isInitialized()){
            return;
        }
        instance = new Api(applicationContext);
    }

    public static boolean isInitialized() {
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


    public void login(String email, String password, Callback<ResponseUser> callback){
        Call<ResponseUser> add = apiConnect.login(email, password);

        add.enqueue(callback);
    }

    public void resetPassword(String email, Callback<ResponseUser> callback) {
        apiConnect.resetPassword(email).enqueue(callback);
    }

    public void signUp(Map<String, String> user, Callback<ResponseUser> callback) {
        apiConnect.signUp(user).enqueue(callback);
    }

    public void updateAvatar(String userId, String image64, Callback<ResponseUser> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("b64img", image64);
        apiConnect.updateAvatar(map).enqueue(callback);
    }

    public void updateAvatar(Map<String, String> map, Callback<ResponseUser> callback) {
        apiConnect.updateAvatar(map).enqueue(callback);
    }

    public void fbSignIn(Map<String, String> map, Callback<ResponseUser> callback) {
        apiConnect.fbSignIn(map).enqueue(callback);

    }
    public void getGenders(Callback<ResponseGender> callback) {
        apiConnect.getGenders(optionsToken).enqueue(callback);

    }

    public void getCancerTypes(Callback<ResponseCancerType> callback) {
        apiConnect.getCancerTypes(optionsToken).enqueue(callback);
    }

    public void getCancerStages(Callback<ResponseCancerStages> callback) {
        apiConnect.getCancerStages(optionsToken).enqueue(callback);
    }

    public void getTreatments(Callback<ResponseTreatment> callback) {
        apiConnect.getTreatments(optionsToken).enqueue(callback);
    }

    public void getMobilities(Callback<ResponseMobility> callback) {
        apiConnect.getMobilities(optionsToken).enqueue(callback);
    }

    public void getHeightUnits(Callback<ResponseHeightUnit> callback) {
        apiConnect.getHeightUnits(optionsToken).enqueue(callback);
    }

    public void getVisibilities(Callback<ResponseVisibility> callback) {
        apiConnect.getVisibilities(optionsToken).enqueue(callback);
    }

    public void getWeightUnits(Callback<ResponseWeightUnit> callback) {
        apiConnect.getWeightUnits(optionsToken).enqueue(callback);
    }

    public void fetchFoodGroups(Callback<ResponseFoodGroup> callback) {
        apiConnect.fetchFoodGroups(Token.toMap()).enqueue(callback);
    }

    public void foodSearch(Map<String, String> map, Callback<ResponseFoodList> callback) {
        apiConnect.foodSearch(map).enqueue(callback);
    }
    public void foodGroupSearch(Map<String, String> map, Callback<ResponseFoodByGroup> callback) {
        apiConnect.foodGroupSearch(map).enqueue(callback);
    }

    public void articleFetchAll(Callback<ResponseArticle> callback) {
        apiConnect.articleFetchAll(Token.toMap()).enqueue(callback);

    }

    public void foodReport(Map<String, String> map, Callback<ResponseNutrients> callback) {
        apiConnect.foodReport(map).enqueue(callback);
    }

    public void articles(Map<String, String> map, Callback<ResponseArticle> callback) {
        apiConnect.articles(map).enqueue(callback);
    }

    public void userDietAdd(Map<String, String> map, Callback<ResponseDiet> callback) {
        apiConnect.userDietAdd(map).enqueue(callback);

    }

    public void userDiet(Map<String, String> map, Callback<ResponseDiet> callback) {
        apiConnect.userDiet(map).enqueue(callback);
    }

    public void banners(Map<String, String> map, Callback<ResponseBanner> callback) {
        apiConnect.banners(map).enqueue(callback);
    }
}
