package com.fightbackfoods.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fightbackfoods.R;
import com.fightbackfoods.Utils;
import com.fightbackfoods.activity.MainActivity;
import com.fightbackfoods.activity.NutrientReportActivity;
import com.fightbackfoods.api.ResponseArticle;
import com.fightbackfoods.model.Article;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BlogPreviewLayout extends SliderLayout {
    private static final String TAG = BlogPreviewLayout.class.getSimpleName();

    public ArticleListener getArticleListener() {
        return articleListener;
    }

    public void setArticleListener(ArticleListener articleListener) {
        this.articleListener = articleListener;
    }

    public Callback<ResponseArticle> getCallback() {
        return callback;
    }

    public void setCallback(Callback<ResponseArticle> callback) {
        this.callback = callback;
    }

    private ArticleListener articleListener;

    public BlogPreviewLayout(Context context) {
        super(context);
        setLayout();
    }
    public BlogPreviewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayout();
    }

    public BlogPreviewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayout();
    }

    private void setLayout() {
        Article.fetchAll(callback);
        Context context = getContext();
        if(context instanceof MainActivity ){
            articleListener = (MainActivity) context;
        }
        setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        setScrollTimeInSec(3); //set scroll delay in seconds :

        setSliderViews();
    }
    private void setSliderViews() {

        for (int i = 0; i <= 3; i++) {

            SliderView sliderView = new SliderView(getContext());

            switch (i) {
                case 0:
                    sliderView.setImageUrl("http://www.csum.edu/image/image_gallery?uuid=1478318c-a254-4b2f-95c5-435844baff89&groupId=4329369");
                    break;
                case 1:
                    sliderView.setImageUrl("https://www.mortonhealth.com/wp-content/uploads/2018/07/Healthy-Life-graphic-070918.jpg");
                    break;
                case 2:
                    sliderView.setImageUrl("https://theholistichomestead1.files.wordpress.com/2015/01/what-is-health.jpg");
                    break;
                case 3:
                    sliderView.setImageUrl("http://www.shedoesthecity.com/wp-content/uploads/files/2015/08/5-Questions-to-Ask-Yourself-When-Building-a-Health-Plan.jpg");
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            //  ivFeedCenter.setImageResource(adapterPosition % 2 == 0 ? R.drawable.raw_educ1 : R.drawable.raw_educ3);

            sliderView.setDescription(i % 2 == 0 ?
                    getResources().getString(R.string.article_content_sample1) :
                    getResources().getString(R.string.article_content_sample2));
            final int finalI = i;

            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Log.d(TAG,"onSliderClick " +finalI + 1);
                    if(articleListener!=null && !Article.getCache().isEmpty()){
                        articleListener.onClick(Article.getCache().get(0), (View) BlogPreviewLayout.this);
                    }else
                        Toast.makeText(getContext(), "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();

                }
            });

            //at last add this view in your layout :
            addSliderView(sliderView);
        }
    }

    public void bindToItem(int feedItem) {
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ButterKnife.bind(this);
    }

    Callback<ResponseArticle> callback = new Callback<ResponseArticle>() {
        @Override
        public void onResponse(Call<ResponseArticle> call, Response<ResponseArticle> response) {
            try {
                Log.d(TAG, "fetch articles onResponse" );
                if(response.isSuccessful()) {
                    ResponseArticle rs = response.body();
                    if(rs.isSuccessful()){
                        Article.setCache(rs.getArticles());
                    }else{
                        Log.d(TAG, "fetch articles failed2 "+ rs.getMessage() );

                    }
                }else{
                    Log.d(TAG, "fetch articles failed1" );

                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseArticle> call, Throwable t) {
            t.printStackTrace();
        }
    };

    public interface ArticleListener {
        void onClick(Article article, View v);
    }
}