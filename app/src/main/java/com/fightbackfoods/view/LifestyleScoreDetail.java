package com.fightbackfoods.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.fightbackfoods.R;
import com.fightbackfoods.activity.MainActivity;
import com.fightbackfoods.adapter.LifestyleGoalsAdapter;
import com.fightbackfoods.api.ResponseBanner;
import com.fightbackfoods.api.ResponseTip;
import com.fightbackfoods.interfaces.SerializableListener;
import com.fightbackfoods.model.Banner;
import com.fightbackfoods.model.Category;
import com.fightbackfoods.model.LifestyleGoal;
import com.fightbackfoods.model.Tip;
import com.fightbackfoods.utils.Validate;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LifestyleScoreDetail extends FrameLayout  {
    private static final String TAG = LifestyleScoreDetail.class.getSimpleName();

    /*
    @BindView(R.id.cv)
    CardView cv;
*/

    @Nullable
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @Nullable
    @BindView(R.id.tv_earn_badge)
    TextView tvEarnBadge;
    String category ="1";
    String subCategory =null;

    public LifestyleScoreDetail(Context context) {
        super(context);
       // init();

    }
    public LifestyleScoreDetail(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray styledAttrs = getContext().obtainStyledAttributes(attrs, R.styleable.BannerFeatured);
        category = styledAttrs.getString(R.styleable.BannerFeatured_category);
        subCategory = styledAttrs.getString(R.styleable.BannerFeatured_subCategory);

        //init();
    }

    public LifestyleScoreDetail(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       // init();
    }


    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_lifestyle_detail_score, this, true);
        ButterKnife.bind(this);

        // setBackgroundResource(R.drawable.bg_container_shadow);
      //  setOrientation(VERTICAL);

        setLayout();
    }

    private void setLayout() {
        Context context = getRootView().getContext();
        rvList.setLayoutManager(new LinearLayoutManager(context));
        rvList.setAdapter(new LifestyleGoalsAdapter(LifestyleGoal.dummy()));
        tvEarnBadge.setText(getResources().getString(R.string.you_earned_n_badge, subCategory));
    }

    public void bindToItem(int feedItem) {
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubCategory(String subCategory) {
        //this.subCategory=subCategory;
        removeAllViews();
        int style = R.style.Mind_Lifestyle ;
        this.subCategory = getResources().getString(R.string.mind);
        if(subCategory.equals("2")) {
            style = R.style.Body_Lifestyle;
            this.subCategory = getResources().getString(R.string.body);
        } else if(subCategory.equals("3")){
            style= R.style.Soul_Lifestyle;
            this.subCategory = getResources().getString(R.string.soul);
        }
        init(style);

    }

    private void init(int style) {
        Context context = new ContextThemeWrapper(getRootView().getContext(),style);
        LayoutInflater.from(context).inflate(R.layout.view_lifestyle_detail_score, this, true);
        ButterKnife.bind(this);
        setLayout();
    }
}