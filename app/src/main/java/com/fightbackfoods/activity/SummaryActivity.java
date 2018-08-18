package com.fightbackfoods.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fightbackfoods.R;

public class SummaryActivity extends BaseActivity2 {

    private static final String TAG = SummaryActivity.class.getSimpleName();
    private static final String KEY_TITLE = "key_title" ;


    private String title ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
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

        Bundle extra = getIntent().getExtras();
        title = extra.getString(KEY_TITLE, getString(R.string.title_daily_summary));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle(title);


    }
}
