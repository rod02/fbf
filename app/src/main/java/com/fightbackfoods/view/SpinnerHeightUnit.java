package com.fightbackfoods.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.fightbackfoods.Api;
import com.fightbackfoods.adapter.SpinnerSimpleAdapter;
import com.fightbackfoods.api.ResponseCancerType;
import com.fightbackfoods.api.ResponseHeightUnit;
import com.fightbackfoods.model.HeightUnit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpinnerHeightUnit extends android.support.v7.widget.AppCompatSpinner {
    private static final String TAG = SpinnerHeightUnit.class.getSimpleName() ;
    private int retryCount=0;
    public SpinnerHeightUnit(Context context) {
        super(context);
    }

    public SpinnerHeightUnit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Api.getInstance().getHeightUnits(callback);
    }

    Callback<ResponseHeightUnit> callback = new Callback<ResponseHeightUnit>() {
        @Override
        public void onResponse(Call<ResponseHeightUnit> call, Response<ResponseHeightUnit> response) {
            Log.d(TAG, "onResponse: "+response.toString());

            try {
                if(!response.message().equalsIgnoreCase("ok")){
                    if(retryCount<=Api.MAX_RETRY_COUNT)
                    init();
                    retryCount++;
                    return;
                }
                ResponseHeightUnit rsp = response.body();
                if (!rsp.getStatus().equalsIgnoreCase("success")){
                    Log.d(TAG, "onResponse error from ws");
                    return;

                }

                List<HeightUnit> list = rsp.getHeightUnits();
                list.add(0,new HeightUnit());
                setAdapter(new SpinnerSimpleAdapter((List)list));
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseHeightUnit> call, Throwable t) {
            t.printStackTrace();

        }
    };

}
