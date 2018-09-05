package com.fightbackfoods.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.fightbackfoods.Utils;
import com.fightbackfoods.model.Article;
import com.fightbackfoods.utils.Validate;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

public class ArticleActivity extends BaseActivity2 {
    private static final String TAG =ArticleActivity.class.getSimpleName() ;

    @BindView(R.id.contentRoot)
    LinearLayout contentRoot;
    @BindView(R.id.root)
    ConstraintLayout root;

    @BindView(R.id.tv_content)
    TextView tvContent;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_created_by)
    TextView tvCreatedBy;
    @BindView(R.id.iv_image)
    ImageView imageView;

    @BindView(R.id.fl_image)
    FrameLayout lay_image;
    @BindView(R.id.pb_image)
    ProgressBar pbImage;
    @BindView(R.id.ns)
    NestedScrollView scrollView;

    public Article getArticle() {
        return article;
    }

    public static void setArticle(Article article) {
        ArticleActivity.article = article;
    }

    private static Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        drawingStartLocation = getIntent().getIntExtra(ARG_DRAWING_START_LOCATION, 0);
        if (savedInstanceState == null) {
            contentRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    contentRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                    startIntroAnimation();
                    return true;
                }
            });
        }

    }

    @Override
    protected void bindViews(){
        super.bindViews();
        Article article = (Article) getIntent().getExtras().getSerializable("article");
        tvContent.setText(Html.fromHtml(article.getContent()));
        tvTitle.setText(article.getTitle());
        tvDate.setText(article.getCreatedAt());
        tvCreatedBy.setText(article.getCreatedBy());
        pbImage.setVisibility(View.VISIBLE);
        lay_image.setVisibility(View.VISIBLE);
        Log.d(TAG, "imagepath "+article.getImageUrl());
        try {
            if(!Validate.isNullString(article.getImageUrl()))
                Picasso.with(this)
                        .load(article.getImageUrl())
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d(TAG, "image success ");
                                pbImage.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                Log.d(TAG, "image error ");
                                lay_image.setVisibility(View.GONE);
                                pbImage.setVisibility(View.GONE);
                            }
                        });
            else         lay_image.setVisibility(View.GONE);

        }catch (IllegalArgumentException e){
            e.printStackTrace();
            lay_image.setVisibility(View.GONE);
            pbImage.setVisibility(View.GONE);
        }

    }
    private void startIntroAnimation() {
        ViewCompat.setElevation(getToolbar(), 0);
        contentRoot.setScaleY(0.1f);
        contentRoot.setPivotY(drawingStartLocation);
        tvContent.setTranslationY(200);
        contentRoot.animate()
                .scaleY(1)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewCompat.setElevation(getToolbar(), Utils.dpToPx(8));
                        animateContent();
                        scrollView.getParent().requestChildFocus(scrollView, scrollView);

                    }
                })
                .start();
    }

    private void animateContent() {
        try {
           /* Article article = (Article) getIntent().getExtras().getSerializable("article");
            tvContent.setText(Html.fromHtml(article.getContent()));*/

        }catch (NullPointerException e){
            e.printStackTrace();
            tvContent.setText(R.string.article_content_sample3);
        }
        tvContent.animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(200)
                .start();
    }

    @Override
    public void onBackPressed() {
        ViewCompat.setElevation(getToolbar(), 0);
        contentRoot.animate()
                .translationY(Utils.getScreenHeight(this))
                .setDuration(600)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ArticleActivity.super.onBackPressed();
                        overridePendingTransition(0, 0);
                        ArticleActivity.article = null;
                    }
                })
                .start();
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
        toolbar.setTitle(getString(R.string.back));
    }

   /* @Override
    public void onProfileClick(View v) {
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        UserProfileActivity.startUserProfileFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }*/
}
