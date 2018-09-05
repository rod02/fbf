package com.fightbackfoods.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fightbackfoods.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProgressNutrientSimple extends FrameLayout {
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_val_percent)
    TextView tvPercent;
    @BindView(R.id.tv_target)
    TextView tvTarget;

    public ProgressNutrientSimple(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_progress_nutrient_simple, this, true);
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


    public ProgressNutrientSimple(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressNutrientSimple(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProgressNutrientSimple(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}