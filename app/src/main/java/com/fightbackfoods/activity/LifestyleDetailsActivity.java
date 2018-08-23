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
import com.fightbackfoods.view.LifestyleScoreDetail;

import java.io.Serializable;

import butterknife.BindView;

public class LifestyleDetailsActivity extends BaseActivity2 implements SerializableListener {
    private static final String TAG = LifestyleDetailsActivity.class.getSimpleName();
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_SUB_CATEGORY = "sub_category";
    private static final String KEY_ITEM = "item";

    @BindView(R.id.banner)
    BannerFeatured BannerFeatured;

    @BindView(R.id.lsd)
    LifestyleScoreDetail lsd;


    private String category = "2";
    private String subCategory = "1";

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
        Bundle extra = getIntent().getExtras();
        subCategory = extra.getString(KEY_SUB_CATEGORY, null);
        BannerFeatured.setListener((SerializableListener) this);
        BannerFeatured.setCategory(category,subCategory);
        lsd.setSubCategory(subCategory);

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

    public static void open(BaseActivity activity, String category, String subCategory, Serializable serializable) {
        Intent intent = new Intent(activity, LifestyleDetailsActivity.class);
        intent.putExtra(KEY_CATEGORY, category);
        intent.putExtra(KEY_SUB_CATEGORY, subCategory);
        intent.putExtra(KEY_ITEM, serializable);
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
