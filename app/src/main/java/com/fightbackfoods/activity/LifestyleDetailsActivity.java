package com.fightbackfoods.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fightbackfoods.R;
import com.fightbackfoods.interfaces.SerializableListener;
import com.fightbackfoods.model.Banner;
import com.fightbackfoods.view.BannerFeatured;
import com.fightbackfoods.view.LifeStyleItemScore;

import java.io.Serializable;

import butterknife.BindView;

public class LifestyleDetailsActivity extends BaseActivity2 implements SerializableListener {
    private static final String TAG = LifestyleDetailsActivity.class.getSimpleName();

    @BindView(R.id.banner)
    BannerFeatured BannerFeatured;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifestyle_details);
        setupWindowAnimations();


        setupLayout();
    }

    private void setupWindowAnimations() {
        getWindow().setEnterTransition(enterTransition());
    }

    private void setupLayout() {
        BannerFeatured.setListener((SerializableListener) this);

    }
    @Override
    public void onClick(Serializable s, View v) {
        Log.d(TAG,"onclick serial");
        final Intent intent = new Intent(this, BannerActivity.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        Banner banner = (Banner) s;

        Log.d(TAG, "onClick "+ String.valueOf(banner ==null));
        Log.d(TAG, "onClick "+ banner.getName());
        //ArticleActivity.setArticle(article);
        intent.putExtra(ARG_DRAWING_START_LOCATION, startingLocation[1]);
        intent.putExtra(BannerActivity.KEY_EXTRA_BANNER,  banner);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public static void open(BaseActivity2 activity) {
        Intent intent = new Intent(activity, LifestyleDetailsActivity.class);
       // intent.putExtra(KEY_ITEM_FOOD, food);
        activity.transitionTo(intent);
    }

    @Override
    public void setupToolbar() {
        super.setupToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Visibility returnTransition = returnTransition();
                getWindow().setReturnTransition(returnTransition);

                finishAfterTransition();
                */
                onBackPressed();
            }
        });
        //toolbar.setTitle(getString(R.string.back));


    }
}
