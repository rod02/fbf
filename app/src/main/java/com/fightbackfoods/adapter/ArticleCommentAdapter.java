package com.fightbackfoods.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.fightbackfoods.activity.MainActivity;
import com.fightbackfoods.view.LoadingFeedItemView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";
    public static final String ACTION_LIKE_IMAGE_CLICKED = "action_like_image_button";

    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;

    private final List<FeedItem> feedItems = new ArrayList<>();

    private Context context;
    private OnCommentClickListener onCommentClickListener;


    private int itemsCount = 0;
    private int lastAnimatedPosition = -1;
    private int avatarSize;

    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;

    private boolean showLoadingView = false;

    public ArticleCommentAdapter(Context context) {
        this.context = context;
    }
    public ArticleCommentAdapter(Context context, OnCommentClickListener listener) {
        this.context = context;
        this.onCommentClickListener = listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_comment_article, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        //runEnterAnimation(viewHolder.itemView, position);
        bindView((ViewHolder) viewHolder, feedItems.get(position));

    }

    public void bindView(ViewHolder holder, FeedItem feedItem) {
        final int position = holder.getAdapterPosition();
        if(feedItem.content==null){
            switch (position % 3) {
                case 0:
                    holder.tvContent.setText("Lorem ipsum dolor sit amet, consectetur adipisicing elit.");
                    break;
                case 1:
                    holder.tvContent.setText("Cupcake ipsum dolor sit amet bear claw.");
                    break;
                case 2:
                    holder.tvContent.setText("Cupcake ipsum dolor sit. Amet gingerbread cupcake. Gummies ice cream dessert icing marzipan apple pie dessert sugar plum.");
                    break;
            }
        }else{
            holder.tvContent.setText(feedItem.content);

        }

        holder.btnLike.setText(feedItem.isLiked ? R.string.liked : R.string.like);
        holder.tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCommentClickListener==null) return;
                onCommentClickListener.onReplyClick(v, position);
            }
        });
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCommentClickListener==null) return;
                onCommentClickListener.onLikeClick(v, position);
            }
        });
        holder.ivUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCommentClickListener==null) return;
                onCommentClickListener.onProfileClick(v);
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
        if (showLoadingView && position == 0) {
            return VIEW_TYPE_LOADER;
        } else {
            return VIEW_TYPE_DEFAULT;
        }
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public void updateItems(boolean animated) {
        feedItems.clear();
        feedItems.addAll(Arrays.asList(
                new FeedItem(33, false),
                new FeedItem(1, false)

        ));
        if (animated) {
            notifyItemRangeInserted(0, feedItems.size());
        } else {
            notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(OnCommentClickListener onCommentClickListener) {
        this.onCommentClickListener = onCommentClickListener;
    }

    public void showLoadingView() {
        showLoadingView = true;
        notifyItemChanged(0);
    }

    public void addItem(FeedItem feedItem) {
        feedItems.add(0, feedItem);
        notifyItemInserted(0);

    }

    public void setAnimationsLocked(boolean animationsLocked) {
        this.animationsLocked = animationsLocked;
    }

    public void setDelayEnterAnimation(boolean delayEnterAnimation) {
        this.delayEnterAnimation = delayEnterAnimation;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /*
        @BindView(R.id.iv_send)
        ImageView btnComments;*/
        @BindView(R.id.tv_like)
        TextView btnLike;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_reply)
        TextView tvReply;
        @BindView(R.id.ivUserProfile)
        ImageView ivUserProfile;
        /*@BindView(R.id.vImageRoot)
        FrameLayout vImageRoot;*/

        FeedItem feedItem;

        public ViewHolder (View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public FeedItem getFeedItem() {
            return feedItem;
        }
    }


    public static class FeedItem {
        public int likesCount;
        public boolean isLiked;
        public String content;


        public FeedItem(String content, int likesCount, boolean isLiked) {
            this.likesCount = likesCount;
            this.isLiked = isLiked;
            this.content = content;
        }
        public FeedItem(int likesCount, boolean isLiked) {
            this.likesCount = likesCount;
            this.isLiked = isLiked;
            //this.content = content;
        }
    }


    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                        }
                    })
                    .start();
        }
    }

    public void updateItems() {
        itemsCount = getItemCount();
        notifyDataSetChanged();
    }

    public interface OnCommentClickListener {
        abstract void onCommentsClick(View v, int position);

        abstract void onMoreClick(View v, int position);
        abstract void onLikeClick(View v, int position);
        abstract void onReplyClick(View v, int position);
        abstract void onProfileClick(View v);
    }
}
