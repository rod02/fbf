package com.fightbackfoods.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.fightbackfoods.Api;
import com.fightbackfoods.adapter.SpinnerSimpleAdapter;
import com.fightbackfoods.api.ResponseCancerType;
import com.fightbackfoods.api.ResponseTreatment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpinnerTreatment extends android.support.v7.widget.AppCompatSpinner {
    private static final String TAG = SpinnerTreatment.class.getSimpleName() ;
    private int retryCount=0;
    public SpinnerTreatment(Context context) {
        super(context);
    }

    public SpinnerTreatment(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Api.getInstance().getTreatments(callback);
    }

    Callback<ResponseTreatment> callback = new Callback<ResponseTreatment>() {
        @Override
        public void onResponse(Call<ResponseTreatment> call, Response<ResponseTreatment> response) {
            Log.d(TAG, "onResponse: "+response.toString());

            try {
                if(!response.message().equalsIgnoreCase("ok")){
                    if(retryCount<=Api.MAX_RETRY_COUNT)
                    init();
                    retryCount++;
                    return;
                }
                ResponseTreatment rsp = response.body();
                if (!rsp.getStatus().equalsIgnoreCase("success")){
                    Log.d(TAG, "onResponse error from ws");
                    return;

                }

                setAdapter(new SpinnerSimpleAdapter((List)rsp.getTreatments()));
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseTreatment> call, Throwable t) {
            t.printStackTrace();

        }
    };

}
