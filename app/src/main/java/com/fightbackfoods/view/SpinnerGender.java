package com.fightbackfoods.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.fightbackfoods.Api;
import com.fightbackfoods.adapter.SpinnerGenderAdapter;
import com.fightbackfoods.api.ResponseGender;
import com.fightbackfoods.model.Gender;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpinnerGender extends android.support.v7.widget.AppCompatSpinner {
    private static final String TAG = SpinnerGender.class.getSimpleName() ;
    private int retryCount=0;
    public SpinnerGender(Context context) {
        super(context);
    }

    public SpinnerGender(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Api.getInstance().getGenders(callback);
    }

    Callback<ResponseGender> callback = new Callback<ResponseGender>() {
        @Override
        public void onResponse(Call<ResponseGender> call, Response<ResponseGender> response) {
            Log.d(TAG, "onResponse: "+response.toString());

            try {
                if(!response.message().equalsIgnoreCase("ok")){
                    if(retryCount<=Api.MAX_RETRY_COUNT)
                    init();
                    retryCount++;
                    return;
                }
                ResponseGender rspGender = response.body();
                if (!rspGender.getStatus().equalsIgnoreCase("success")){
                    Log.d(TAG, "onResponse error from ws");
                    return;

                }
                List<Gender> genderList = rspGender.getGenders();
                genderList.add(0,new Gender());
                setAdapter(new SpinnerGenderAdapter(genderList));
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseGender> call, Throwable t) {
            t.printStackTrace();

        }
    };

}
