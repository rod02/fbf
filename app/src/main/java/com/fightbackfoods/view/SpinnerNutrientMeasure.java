package com.fightbackfoods.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Adapter;
import android.widget.SpinnerAdapter;

import com.fightbackfoods.Api;
import com.fightbackfoods.R;
import com.fightbackfoods.adapter.NutrientMeasurementAdapter;
import com.fightbackfoods.adapter.NutrientWeightAdapter;
import com.fightbackfoods.adapter.SpinnerSimpleAdapter;
import com.fightbackfoods.api.ResponseWeightUnit;
import com.fightbackfoods.model.Measure;
import com.fightbackfoods.model.WeightUnit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpinnerNutrientMeasure extends android.support.v7.widget.AppCompatSpinner {
    private static final String TAG = SpinnerNutrientMeasure.class.getSimpleName() ;
    private int retryCount=0;
    public SpinnerNutrientMeasure(Context context) {
        super(context);
    }

    public SpinnerNutrientMeasure(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
    }

    public int getId(){
        return R.id.sp_nutrient_measure;
    }

    public Measure getSelectedMeasure(){
        return ((NutrientMeasurementAdapter) getAdapter()).getItem(getSelectedItemPosition());
    }

    public String getSelectedLabel() {
        return getSelectedMeasure().getLabel();
    }
}
