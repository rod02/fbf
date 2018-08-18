package com.fightbackfoods.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fightbackfoods.R;
import com.fightbackfoods.activity.MainActivity;
import com.fightbackfoods.api.ResponseBanner;
import com.fightbackfoods.interfaces.SerializableListener;
import com.fightbackfoods.model.Banner;
import com.fightbackfoods.model.Banner;
import com.fightbackfoods.utils.Validate;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BannerFeatured extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = BannerFeatured.class.getSimpleName();
    @BindView(R.id.slider)
    SliderLayout slider;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.srl)
    SwipeRefreshLayout swipeRefreshLayout;
    public SerializableListener getListener() {
        return listener;
    }

    public void setListener(SerializableListener listener) {
        this.listener = listener;
    }

    public Callback<ResponseBanner> getCallback() {
        return callback;
    }

    public void setCallback(Callback<ResponseBanner> callback) {
        this.callback = callback;
    }

    private SerializableListener listener;

    public BannerFeatured(Context context) {
        super(context);
        init();

    }
    public BannerFeatured(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BannerFeatured(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_banner_featured, this, true);
        ButterKnife.bind(this);

        // setBackgroundResource(R.drawable.bg_container_shadow);
      //  setOrientation(VERTICAL);

        setLayout();
    }

    private void setLayout() {
        //pb.setVisibility(View.VISIBLE);
       fetchBanners();
        Context context = getContext();
        if(context instanceof MainActivity ){
            listener = (MainActivity) context;
        }
        slider.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        slider.setScrollTimeInSec(5); //set scroll delay in seconds :
       // setSliderViews(Banner.getFeaturedCache());
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                swipeRefreshLayout.setRefreshing(true);
                //fetchBanners();
                // Fetching data from server
                try {
                    //loadRecyclerViewData();
                }catch (NullPointerException e){

                }


            }
        });
    }

    private void fetchBanners() {
        Log.d(TAG, "get articles");

        Banner.fetch(callback);
    }


    private void setSliderViews(List<Banner> featuredCache) {

        if(featuredCache==null && featuredCache.isEmpty())return;
        Log.d(TAG, "featured articles size " +featuredCache.size());
        
        for (final Banner a: featuredCache) {
            SliderView sliderView = new SliderView(getContext());
            String imagePath = a.getImg_url();
            if(!Validate.isNullString(imagePath)){
                imagePath= Validate.path(imagePath);
                sliderView.setImageUrl(imagePath);
                Log.d(TAG, "featured article " +a.getName() );
                Log.d(TAG, "featured article " +imagePath );

            }else{
                sliderView.setImageUrl("http://www.csum.edu/image/image_gallery?uuid=1478318c-a254-4b2f-95c5-435844baff89&groupId=4329369");
            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription(a.getName());
            sliderView.setDescriptionTextSize(20f);
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Log.d(TAG,"onSliderClick " );
                    if(listener!=null && !Banner.getCache().isEmpty()){

                        listener.onClick(a, (View) BannerFeatured.this);
                    }else
                        Toast.makeText(getContext(), "Banner " + a.getName(), Toast.LENGTH_SHORT).show();

                }
            });
           // tvContent.setText(a.getContent());
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

    Callback<ResponseBanner> callback = new Callback<ResponseBanner>() {
        @Override
        public void onResponse(Call<ResponseBanner> call, Response<ResponseBanner> response) {
            try {
                Log.d(TAG, "fetch articles onResponse "+response.toString() );
               // pb.setVisibility(View.GONE);
                
                swipeRefreshLayout.setRefreshing(false);
                if(response.isSuccessful()) {
                    ResponseBanner rs = response.body();
                    Log.d(TAG, "featured articles set" );
                    Banner.setCache(rs.getBanners());

                    setSliderViews(Banner.getCache());
                    /*if(rs.isSuccessful()){

                    }else{
                        Log.d(TAG, "fetch articles failed2 "+ rs.getMessage() );

                    }*/
                }else{
                    Log.d(TAG, "fetch articles failed1" );

                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseBanner> call, Throwable t) {

            t.printStackTrace();
            try {
               // pb.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

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

    @Override
    public int getId(){
        return R.id.banner;
    }


    
}