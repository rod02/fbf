package com.fightbackfoods.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.fightbackfoods.Api;
import com.fightbackfoods.adapter.SpinnerSimpleAdapter;
import com.fightbackfoods.api.ResponseFoodGroup;
import com.fightbackfoods.api.ResponseWeightUnit;
import com.fightbackfoods.model.FoodGroup;
import com.fightbackfoods.model.WeightUnit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpinnerFoodGroup extends android.support.v7.widget.AppCompatSpinner {
    private static final String TAG = SpinnerFoodGroup.class.getSimpleName() ;
    private int retryCount=0;
    public SpinnerFoodGroup(Context context) {
        super(context);
    }

    public SpinnerFoodGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Api.getInstance().fetchFoodGroups(callback);
    }

    Callback<ResponseFoodGroup> callback = new Callback<ResponseFoodGroup>() {
        @Override
        public void onResponse(Call<ResponseFoodGroup> call, Response<ResponseFoodGroup> response) {
            Log.d(TAG, "onResponse: "+response.toString());

            try {
                if(!response.message().equalsIgnoreCase("ok")){
                    if(retryCount<=Api.MAX_RETRY_COUNT)
                    init();
                    retryCount++;
                    return;
                }
                ResponseFoodGroup rsp = response.body();
                if (!rsp.getStatus().equalsIgnoreCase("success")){
                    Log.d(TAG, "onResponse error from ws");
                    return;

                }

                rsp.getFoodGroups().add(0,new FoodGroup());
                setAdapter(new SpinnerSimpleAdapter((List)rsp.getFoodGroups()));
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseFoodGroup> call, Throwable t) {
            t.printStackTrace();

        }
    };

    public String getSelectedId() throws NullPointerException{

       return
               ((SpinnerSimpleAdapter) getAdapter()).getItemIdAt(getSelectedItemPosition());
    }
}
