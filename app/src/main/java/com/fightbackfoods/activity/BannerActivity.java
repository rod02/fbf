package com.fightbackfoods.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewCompat;
import android.text.Html;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fightbackfoods.R;
import com.fightbackfoods.Utils;
import com.fightbackfoods.model.Banner;

import butterknife.BindView;

public class BannerActivity extends BaseActivity2 {
    private static final String TAG =BannerActivity.class.getSimpleName() ;
    public static final String KEY_EXTRA_BANNER =BannerActivity.class.getSimpleName() ;

    @BindView(R.id.contentRoot)
    LinearLayout contentRoot;
    @BindView(R.id.root)
    ConstraintLayout root;

    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.webView)
    WebView webView;
    public Banner getBanner() {
        return banner;
    }

    public static void setBanner(Banner banner) {
        BannerActivity.banner = banner;
    }

    private static Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

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
    private boolean isRedirected;
    ProgressDialog progressDialog;

    private void animateContent() {
        try {
            Banner banner = (Banner) getIntent().getExtras().getSerializable(KEY_EXTRA_BANNER);
            webView.getSettings().setJavaScriptEnabled(true); // enable javascript


            webView.setWebViewClient(new WebViewClient() {

                @SuppressWarnings("deprecation")
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(BannerActivity.this, description, Toast.LENGTH_SHORT).show();
                    showProgress(false);
                }
                @TargetApi(android.os.Build.VERSION_CODES.M)
                @Override
                public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                    // Redirect to deprecated method, so you can use it in all SDK versions
                    onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                }
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    isRedirected = true;
                    return false;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    isRedirected = false;
                }

                public void onLoadResource (WebView view, String url) {
                    if (!isRedirected) {
                       //showProgress(true);
                    }

                }
                public void onPageFinished(WebView view, String url) {
                    try{
                        isRedirected=true;

                        /*if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }*/



                    }catch(Exception exception){
                        exception.printStackTrace();
                    }
                }

            });

            webView .loadUrl(banner.getLink());
           // tvContent.setText(Html.fromHtml(banner.getContent()));

        }catch (NullPointerException e){
            e.printStackTrace();
            //tvContent.setText(R.string.banner_content_sample3);
        }
       /* tvContent.animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(200)
                .start();*/
    }

    private void showProgress(boolean b) {
        try {

            if (progressDialog == null) {
                if(!b) return;
                progressDialog = new ProgressDialog(BannerActivity.this);
                progressDialog.setMessage("Loading...");
            }
            if(b)
            progressDialog.show();
            else progressDialog.dismiss();



        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
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
                        BannerActivity.super.onBackPressed();
                        overridePendingTransition(0, 0);
                        BannerActivity.banner = null;
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
