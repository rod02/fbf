package com.fightbackfoods.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.fightbackfoods.R;
import com.fightbackfoods.activity.BaseActivity2;
import com.fightbackfoods.activity.LifestyleDetailsActivity;
import com.fightbackfoods.activity.NutrientReportActivity;
import com.fightbackfoods.model.Food;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LifeStyleItemScore extends FrameLayout {
    private static final String TAG = LifeStyleItemScore.class.getSimpleName();
    @BindView(R.id.pb)
    ProgressBar pb;


    public LifeStyleItemScore(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_lifestyle_item_score, this, true);
        ButterKnife.bind(this);
        setLayout();
    }

    private void setLayout() {
        pb.post(new Runnable() {
            @Override
            public void run() {
                ProgressBarAnimation anim = new ProgressBarAnimation(pb, 0, 60);
                anim.setDuration(1000);
                pb.startAnimation(anim);
                setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onclick");
                        LifestyleDetailsActivity.open((BaseActivity2) getContext());
                    }
                });
            }
        });

    }

    @Override
    public void setOnClickListener(OnClickListener l){
        super.setOnClickListener(l);
    }


    public LifeStyleItemScore(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LifeStyleItemScore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LifeStyleItemScore(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
/*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }*/
}