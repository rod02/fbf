package com.fightbackfoods.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.fightbackfoods.interfaces.Item;
import com.fightbackfoods.model.Goal;
import com.fightbackfoods.model.LifestyleGoal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LifestyleGoalsAdapter extends RecyclerView.Adapter<LifestyleGoalsAdapter.ViewHolder> {

    private final List<LifestyleGoal> data;
    private OnItemClick onItemClick;

    public LifestyleGoalsAdapter(List<LifestyleGoal> data) {
        this(data,null);
    }
    public LifestyleGoalsAdapter(List<LifestyleGoal> data, OnItemClick onItemClick){
        this.data = data;
        this.onItemClick = onItemClick;
    }

    public void clear(){
        try {

            int itemCount = getItemCount();
            data.clear();
            notifyItemRangeRemoved(0 , itemCount);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_lifestyle, parent, false);
        return new ViewHolder(view, this.onItemClick);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        LifestyleGoal item  = data.get(position);
        holder.bindView(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public LifestyleGoal getItem(int position){
        return data.get(position);
    }
    public void add(List<LifestyleGoal> items) {
        int previousDataSize = this.data.size();
        this.data.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_point)
        TextView tvPoint;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.iv_star)
        ImageView ivStar;

        LifestyleGoal item;


        public ViewHolder(View view, final OnItemClick onItemClickListener) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick( item, getAdapterPosition(), 0);
                    }
                }
            });
        }

        public void bindView(LifestyleGoal lsg) {
            this.item = lsg;
            int adapterPosition = getAdapterPosition();
            tvTitle.setText(lsg.getTitle());
            tvPoint.setText(lsg.getPoints());
            tvDate.setText(lsg.getCreatedAt());
            ivStar.setVisibility(lsg.isStar()? View.VISIBLE:View.GONE);


        }

    }
}
