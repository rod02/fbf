package com.fightbackfoods.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.fightbackfoods.Utils;
import com.fightbackfoods.model.Article;

import butterknife.BindView;

public class ArticleActivity extends BaseActivity2 {
    private static final String TAG =ArticleActivity.class.getSimpleName() ;

    @BindView(R.id.contentRoot)
    LinearLayout contentRoot;
    @BindView(R.id.root)
    ConstraintLayout root;

    @BindView(R.id.tv_content)
    TextView tvContent;

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
                    }
                })
                .start();
    }

    private void animateContent() {
        try {
            Article article = (Article) getIntent().getExtras().getSerializable("article");
            tvContent.setText(Html.fromHtml(article.getContent()));

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
}
