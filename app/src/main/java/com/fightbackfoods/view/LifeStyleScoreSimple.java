package com.fightbackfoods.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.fightbackfoods.R;

import butterknife.ButterKnife;


public class LifeStyleScoreSimple extends FrameLayout {


    public LifeStyleScoreSimple(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_lifestyle_score_simple, this, true);
        ButterKnife.bind(this);
    }


    public LifeStyleScoreSimple(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LifeStyleScoreSimple(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LifeStyleScoreSimple(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

/*    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }*/
}