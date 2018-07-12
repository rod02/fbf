package com.fightbackfoods.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.fightbackfoods.Api;
import com.fightbackfoods.adapter.SpinnerGenderAdapter;
import com.fightbackfoods.adapter.SpinnerSimpleAdapter;
import com.fightbackfoods.api.ResponseCancerType;
import com.fightbackfoods.api.ResponseGender;
import com.fightbackfoods.interfaces.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpinnerCancerType extends android.support.v7.widget.AppCompatSpinner {
    private static final String TAG = SpinnerCancerType.class.getSimpleName() ;
    private int retryCount=0;
    public SpinnerCancerType(Context context) {
        super(context);
    }

    public SpinnerCancerType(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Api.getInstance().getCancerTypes(callback);
    }

    Callback<ResponseCancerType> callback = new Callback<ResponseCancerType>() {
        @Override
        public void onResponse(Call<ResponseCancerType> call, Response<ResponseCancerType> response) {
            Log.d(TAG, "onResponse: "+response.toString());

            try {
                if(!response.message().equalsIgnoreCase("ok")){
                    if(retryCount<=Api.MAX_RETRY_COUNT)
                    init();
                    retryCount++;
                    return;
                }
                ResponseCancerType rsp = response.body();
                if (!rsp.getStatus().equalsIgnoreCase("success")){
                    Log.d(TAG, "onResponse error from ws");
                    return;

                }

                setAdapter(new SpinnerSimpleAdapter((List)rsp.getCancerTypes()));
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseCancerType> call, Throwable t) {
            t.printStackTrace();

        }
    };

}
