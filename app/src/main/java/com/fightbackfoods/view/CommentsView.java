package com.fightbackfoods.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fightbackfoods.R;
import com.fightbackfoods.activity.CommentsActivity;
import com.fightbackfoods.activity.MainActivity;
import com.fightbackfoods.activity.UserProfileActivity;
import com.fightbackfoods.adapter.ArticleCommentAdapter;
import com.fightbackfoods.adapter.CommentsItemAnimator;
import com.fightbackfoods.adapter.FeedAdapter;
import com.fightbackfoods.adapter.FeedItemAnimator;
import com.fightbackfoods.api.ResponseArticle;
import com.fightbackfoods.interfaces.SerializableListener;
import com.fightbackfoods.model.Article;
import com.fightbackfoods.utils.Image;
import com.fightbackfoods.utils.Validate;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommentsView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener,
        SendCommentButton.OnSendClickListener {
    private static final String TAG = CommentsView.class.getSimpleName();

    @Nullable
    @BindView(R.id.pb)
    ProgressBar pb;
    /*
    @BindView(R.id.srl)
    SwipeRefreshLayout swipeRefreshLayout;*/
    @BindView(R.id.et_comment)
    EditText etComment;/*
    @BindView(R.id.iv_send)
    ImageView ivSend;*/

    @BindView(R.id.btnSendComment)
    SendCommentButton btnSendComment;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_show_more)
    TextView tvShowMore;


    ArticleCommentAdapter adapter;
    ArticleCommentAdapter.OnCommentClickListener onCommentClickListener;


    public Callback<ResponseArticle> getCallback() {
        return callback;
    }

    public void setCallback(Callback<ResponseArticle> callback) {
        this.callback = callback;
    }

    private SerializableListener articleListener;

    public CommentsView(Context context) {
        super(context);
        init();

    }
    public CommentsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommentsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_comments, this, true);
        ButterKnife.bind(this);

        // setBackgroundResource(R.drawable.bg_container_shadow);
      //  setOrientation(VERTICAL);

        setLayout();
    }

    private void setLayout() {
        if(pb!=null) pb.setVisibility(View.VISIBLE);
        Log.d(TAG, "get articles");

        Article.getComments(callback);
        Context context = getRootView().getRootView().getContext();
        if(context instanceof ArticleCommentAdapter.OnCommentClickListener)
            onCommentClickListener= (ArticleCommentAdapter.OnCommentClickListener) context;


        //swipeRefreshLayout.setOnRefreshListener(this);
        btnSendComment.setOnSendClickListener(this);
        setupComments();

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
                if(pb!=null) pb.setVisibility(View.GONE);
               // swipeRefreshLayout.setEnabled(true);
                if(response.isSuccessful()) {
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
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override public void run() {

                //swipeRefreshLayout.setRefreshing(false);

            }

        }, 3000);

    }

    private void setupComments() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvList.setLayoutManager(linearLayoutManager);

        adapter = new ArticleCommentAdapter(getContext(), onCommentClickListener);
       // adapter.setOnItemClickListener(onCommentClickListener);
        rvList.setAdapter(adapter);
        rvList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
            }
        });
        rvList.setItemAnimator(new CommentsItemAnimator());
        adapter.updateItems(true);
    }

    private void showFeedLoadingItemDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              //  rvFeed.smoothScrollToPosition(0);
              //  feedAdapter.showLoadingView();
            }
        }, 500);
    }

    @Override
    public void onSendClickListener(View v) {
        if (validateComment()) {
            adapter.addItem(new ArticleCommentAdapter.FeedItem(etComment.getText().toString(),0, false));
            adapter.setAnimationsLocked(false);
            adapter.setDelayEnterAnimation(false);
//            rvList.smoothScrollBy(0, rvList.getChildAt(0).getHeight() * adapter.getItemCount());

            etComment.setText(null);
            btnSendComment.setCurrentState(SendCommentButton.STATE_DONE);
        }
    }

    private boolean validateComment() {
        if (TextUtils.isEmpty(etComment.getText())) {
            btnSendComment.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_error));
            return false;
        }

        return true;
    }



}