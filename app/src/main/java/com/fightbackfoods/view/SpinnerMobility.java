package com.fightbackfoods.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.fightbackfoods.Api;
import com.fightbackfoods.adapter.SpinnerSimpleAdapter;
import com.fightbackfoods.api.ResponseMobility;
import com.fightbackfoods.api.ResponseTreatment;
import com.fightbackfoods.model.Mobility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpinnerMobility extends android.support.v7.widget.AppCompatSpinner {
    private static final String TAG = SpinnerMobility.class.getSimpleName() ;
    private int retryCount=0;
    public SpinnerMobility(Context context) {
        super(context);
    }

    public SpinnerMobility(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Api.getInstance().getMobilities(callback);
    }

    Callback<ResponseMobility> callback = new Callback<ResponseMobility>() {
        @Override
        public void onResponse(Call<ResponseMobility> call, Response<ResponseMobility> response) {
            Log.d(TAG, "onResponse: "+response.toString());

            try {
                if(!response.message().equalsIgnoreCase("ok")){
                    if(retryCount<=Api.MAX_RETRY_COUNT)
                    init();
                    retryCount++;
                    return;
                }
                ResponseMobility rsp = response.body();
                if (!rsp.getStatus().equalsIgnoreCase("success")){
                    Log.d(TAG, "onResponse error from ws");
                    return;

                }

                rsp.getMobilities().add(0,new Mobility());
                setAdapter(new SpinnerSimpleAdapter((List)rsp.getMobilities()));
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseMobility> call, Throwable t) {
            t.printStackTrace();

        }
    };

}
