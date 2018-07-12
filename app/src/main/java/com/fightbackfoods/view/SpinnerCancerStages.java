package com.fightbackfoods.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.fightbackfoods.Api;
import com.fightbackfoods.adapter.SpinnerSimpleAdapter;
import com.fightbackfoods.api.ResponseCancerStages;
import com.fightbackfoods.api.ResponseCancerType;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpinnerCancerStages extends android.support.v7.widget.AppCompatSpinner {
    private static final String TAG = SpinnerCancerStages.class.getSimpleName() ;
    private int retryCount=0;
    public SpinnerCancerStages(Context context) {
        super(context);
    }

    public SpinnerCancerStages(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Api.getInstance().getCancerStages(callback);
    }

    Callback<ResponseCancerStages> callback = new Callback<ResponseCancerStages>() {
        @Override
        public void onResponse(Call<ResponseCancerStages> call, Response<ResponseCancerStages> response) {
            Log.d(TAG, "onResponse: "+response.toString());

            try {
                if(!response.message().equalsIgnoreCase("ok")){
                    if(retryCount<=Api.MAX_RETRY_COUNT)
                    init();
                    retryCount++;
                    return;
                }
                ResponseCancerStages rsp = response.body();
                if (!rsp.getStatus().equalsIgnoreCase("success")){
                    Log.d(TAG, "onResponse error from ws");
                    return;

                }

                setAdapter(new SpinnerSimpleAdapter((List)rsp.getCancerStages()));
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseCancerStages> call, Throwable t) {
            t.printStackTrace();

        }
    };

}
