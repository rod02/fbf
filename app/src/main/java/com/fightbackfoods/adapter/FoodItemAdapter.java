package com.fightbackfoods.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.fightbackfoods.model.FoodItem;
import com.fightbackfoods.utils.RoundedTransformation;
import com.google.android.gms.vision.text.Text;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FoodItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int itemsCount = 0;
    private int lastAnimatedPosition = -1;

    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;

    List<FoodItem> mItems = new ArrayList<>();

    public FoodItemAdapter(Context context, List<FoodItem> list) {
        this.context = context;
        this.mItems = list;
        itemsCount= mItems.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_food_list, parent, false);
        return new FoodItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        runEnterAnimation(viewHolder.itemView, position);
        FoodItemViewHolder holder = (FoodItemViewHolder) viewHolder;
        FoodItem item = mItems.get(position);
        holder.tvFoodName.setText(item.getName());
        holder.tvFoodQty.setText(item.getQty());
        holder.tvFoodValue.setText(item.getValue());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateItems() {
        itemsCount = 10;
        notifyDataSetChanged();
    }

    public void addItem() {
        itemsCount++;
        notifyItemInserted(itemsCount - 1);
    }

    public void setAnimationsLocked(boolean animationsLocked) {
        this.animationsLocked = animationsLocked;
    }

    public void setDelayEnterAnimation(boolean delayEnterAnimation) {
        this.delayEnterAnimation = delayEnterAnimation;
    }

    public static class FoodItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_food_name)
        TextView tvFoodName;
        @BindView(R.id.tv_food_qty)
        TextView tvFoodQty;
        @BindView(R.id.tv_food_value)
        TextView tvFoodValue;
        @BindView(R.id.cv_item_food)
        CardView cvItemFood;

        public FoodItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
