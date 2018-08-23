package com.fightbackfoods.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.util.Log;

import com.fightbackfoods.Api;
import com.fightbackfoods.R;
import com.fightbackfoods.adapter.SpinnerSimpleAdapter;
import com.fightbackfoods.api.ResponseCancerType;
import com.fightbackfoods.api.ResponseHealthAspect;
import com.fightbackfoods.model.CancerType;
import com.fightbackfoods.model.HealthAspectCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpinnerHealthAspectCategory extends AppCompatSpinner {
    private static final String TAG = SpinnerHealthAspectCategory.class.getSimpleName() ;
    private int retryCount=0;
    String category = "1";

    public SpinnerHealthAspectCategory(Context context) {
        super(context);
    }

    public SpinnerHealthAspectCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray styledAttrs = getContext().obtainStyledAttributes(attrs, R.styleable.HealthAspect);
        category = styledAttrs.getString(R.styleable.HealthAspect_healthCategory);
        init();
    }

    private void init() {
        HealthAspectCategory.get(callback);
        setupLayout();
    }

    private void setupLayout() {
        if(category.equals("1"))
            setList(HealthAspectCategory.getMental_Cache());
        else
            setList(HealthAspectCategory.getPhysical_Cache());


    }

    private void setList(List<HealthAspectCategory> list) {
        if(list==null || list.isEmpty()) return;
        list.add(0,new HealthAspectCategory());
        setAdapter(new SpinnerSimpleAdapter((List)list));
    }

    Callback<ResponseHealthAspect> callback = new Callback<ResponseHealthAspect>() {
        @Override
        public void onResponse(Call<ResponseHealthAspect> call, Response<ResponseHealthAspect> response) {
            Log.d(TAG, "onResponse: "+response.toString());

            try {
                if(!response.message().equalsIgnoreCase("ok")){
                    if(retryCount<=Api.MAX_RETRY_COUNT)
                    init();
                    retryCount++;
                    return;
                }
                ResponseHealthAspect rsp = response.body();
                if (!rsp.getStatus().equalsIgnoreCase("success")){
                    Log.d(TAG, "onResponse error from ws");
                    return;

                }
                List<HealthAspectCategory> list = rsp.getHealthAspectCategories();
                HealthAspectCategory.setCache(list);
                setupLayout();
                //list.add(0,new HealthAspectCategory());
                //setAdapter(new SpinnerSimpleAdapter((List)list));
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseHealthAspect> call, Throwable t) {
            t.printStackTrace();

        }
    };

}
