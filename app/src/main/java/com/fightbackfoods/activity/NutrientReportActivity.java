package com.fightbackfoods.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.View;

import com.fightbackfoods.R;
import com.fightbackfoods.model.Food;

public class NutrientReportActivity extends BaseActivity2 {
    private static final String TAG =NutrientReportActivity.class.getSimpleName() ;

    public static void open(Food food, BaseActivity baseActivity) {
        Intent intent = new Intent(baseActivity, NutrientReportActivity.class);
       // intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        baseActivity.transitionTo(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient_report);
        setupWindowAnimations();
        setupLayout();
    }

    private void setupLayout() {
    }


    private void setupWindowAnimations() {
        getWindow().setEnterTransition(enterTransition());
    }

    @Override
    public void setupToolbar() {
        super.setupToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Visibility returnTransition = returnTransition();
                getWindow().setReturnTransition(returnTransition);

                finishAfterTransition();
            }
        });
        toolbar.setTitle(getString(R.string.title_nutrient_report));
    }
}
