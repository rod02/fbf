package com.fightbackfoods.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.fightbackfoods.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LifeStyleItemScore extends FrameLayout {
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
            }
        });
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