package com.fightbackfoods.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderAdapter;
import com.fightbackfoods.R;
import com.fightbackfoods.Utils;
import com.fightbackfoods.activity.MainActivity;
import com.fightbackfoods.activity.NutrientReportActivity;
import com.fightbackfoods.api.ResponseArticle;
import com.fightbackfoods.interfaces.SerializableListener;
import com.fightbackfoods.model.Article;
import com.fightbackfoods.utils.Validate;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArticleFeatured extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = ArticleFeatured.class.getSimpleName();
    @BindView(R.id.slider)
    SliderLayout slider;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.srl)
    SwipeRefreshLayout swipeRefreshLayout;
    public SerializableListener getArticleListener() {
        return articleListener;
    }

    public void setArticleListener(SerializableListener articleListener) {
        this.articleListener = articleListener;
    }

    public Callback<ResponseArticle> getCallback() {
        return callback;
    }

    public void setCallback(Callback<ResponseArticle> callback) {
        this.callback = callback;
    }

    private SerializableListener articleListener;

    public ArticleFeatured(Context context) {
        super(context);
        init();

    }
    public ArticleFeatured(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArticleFeatured(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_article_featured, this, true);
        ButterKnife.bind(this);

        // setBackgroundResource(R.drawable.bg_container_shadow);
      //  setOrientation(VERTICAL);

        setLayout();
    }

    private void setLayout() {
        pb.setVisibility(View.VISIBLE);
        Log.d(TAG, "get articles");

        Article.featured(callback);
        Context context = getContext();
        if(context instanceof MainActivity ){
            articleListener = (MainActivity) context;
        }
        slider.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        slider.setScrollTimeInSec(3); //set scroll delay in seconds :

       // setSliderViews(Article.getFeaturedCache());
        swipeRefreshLayout.setOnRefreshListener(this);

    }





    private void setSliderViews(List<Article> featuredCache) {

        if(featuredCache==null && featuredCache.isEmpty())return;
        Log.d(TAG, "featured articles size " +featuredCache.size());

        for (final Article a: featuredCache) {
            SliderView sliderView = new SliderView(getContext());
            String imagePath = a.getFeaturedImage();
            if(!Validate.isNullString(imagePath)){
                imagePath= Validate.path(imagePath);
                sliderView.setImageUrl(imagePath);
                Log.d(TAG, "featured article " +a.getTitle() );
                Log.d(TAG, "featured article " +imagePath );

            }else{
                sliderView.setImageUrl("http://www.csum.edu/image/image_gallery?uuid=1478318c-a254-4b2f-95c5-435844baff89&groupId=4329369");
            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription(a.getTitle());
            sliderView.setDescriptionTextSize(20f);
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Log.d(TAG,"onSliderClick " );
                    if(articleListener!=null && !Article.getFeaturedCache().isEmpty()){
                        articleListener.onClick(a, (View) ArticleFeatured.this);
                    }else
                        Toast.makeText(getContext(), "Article " + a.getTitle(), Toast.LENGTH_SHORT).show();

                }
            });
           // tvContent.setText(a.getContent());
            //at last add this view in your layout :
            slider.addSliderView(sliderView);
        }
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
                    if(articleListener!=null && !Article.getFeaturedCache().isEmpty()){
                        articleListener.onClick(Article.getFeaturedCache().get(0), (View) ArticleFeatured.this);
                    }else
                        Toast.makeText(getContext(), "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();

                }
            });

            //at last add this view in your layout :
            slider.addSliderView(sliderView);
        }
    }

    public void bindToItem(int feedItem) {
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    Callback<ResponseArticle> callback = new Callback<ResponseArticle>() {
        @Override
        public void onResponse(Call<ResponseArticle> call, Response<ResponseArticle> response) {
            try {
                Log.d(TAG, "fetch articles onResponse "+response.toString() );
                pb.setVisibility(View.GONE);
               // swipeRefreshLayout.setEnabled(true);
                if(response.isSuccessful()) {
                    ResponseArticle rs = response.body();
                    if(rs.isSuccessful()){
                        Log.d(TAG, "featured articles set" );
                        Article.setFeaturedCache(rs.getArticles());

                        setSliderViews(Article.getFeaturedCache());
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
            try {
                pb.setVisibility(View.GONE);

            }catch (NullPointerException e){

            }
        }
    };

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override public void run() {

                swipeRefreshLayout.setRefreshing(false);

            }

        }, 3000);

    }


}