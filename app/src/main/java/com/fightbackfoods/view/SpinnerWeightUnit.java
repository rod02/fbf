package com.fightbackfoods.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.fightbackfoods.Api;
import com.fightbackfoods.adapter.SpinnerSimpleAdapter;
import com.fightbackfoods.api.ResponseHeightUnit;
import com.fightbackfoods.api.ResponseWeightUnit;
import com.fightbackfoods.model.WeightUnit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpinnerWeightUnit extends android.support.v7.widget.AppCompatSpinner {
    private static final String TAG = SpinnerWeightUnit.class.getSimpleName() ;
    private int retryCount=0;
    public SpinnerWeightUnit(Context context) {
        super(context);
    }

    public SpinnerWeightUnit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Api.getInstance().getWeightUnits(callback);
    }

    Callback<ResponseWeightUnit> callback = new Callback<ResponseWeightUnit>() {
        @Override
        public void onResponse(Call<ResponseWeightUnit> call, Response<ResponseWeightUnit> response) {
            Log.d(TAG, "onResponse: "+response.toString());

            try {
                if(!response.message().equalsIgnoreCase("ok")){
                    if(retryCount<=Api.MAX_RETRY_COUNT)
                    init();
                    retryCount++;
                    return;
                }
                ResponseWeightUnit rsp = response.body();
                if (!rsp.getStatus().equalsIgnoreCase("success")){
                    Log.d(TAG, "onResponse error from ws");
                    return;

                }

                rsp.getWeightUnits().add(0,new WeightUnit());
                setAdapter(new SpinnerSimpleAdapter((List)rsp.getWeightUnits()));
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseWeightUnit> call, Throwable t) {
            t.printStackTrace();

        }
    };

}
