package com.fightbackfoods.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.fightbackfoods.interfaces.Item;
import com.fightbackfoods.model.UserDiet;

import java.util.Dictionary;
import java.util.List;

public class MyFoodAdapter extends RecyclerView.Adapter<MyFoodAdapter.ViewHolder> {

    private final List<UserDiet> data;
    private OnItemClick onItemClick;

    public MyFoodAdapter(List<UserDiet> data) {
        this(data,null);
    }
    public MyFoodAdapter(List<UserDiet> data, OnItemClick onItemClick){
        this.data = data;
        this.onItemClick = onItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_food, parent, false);
        return new ViewHolder(view, this.onItemClick);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        UserDiet item  = data.get(position);
        holder.tvName.setText(item.getFoodName());
        holder.tvQty.setText(item.getServingsCal());
        holder.item=item;
    }
    public void updateItems() {
        notifyItemRangeChanged(0, getItemCount());
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public UserDiet getItem(int position){
        return data.get(position);
    }
    public void add(List<UserDiet> items) {
        int previousDataSize = this.data.size();
        this.data.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        UserDiet item;
        TextView tvQty;
        TextView tvScore;


        public ViewHolder(View view, final OnItemClick onItemClickListener) {
            super(view);
            this.tvName = (TextView) view.findViewById(R.id.tv_my_food_name);
            this.tvQty = (TextView) view.findViewById(R.id.tv_my_food_qty);
            this.tvScore = (TextView) view.findViewById(R.id.tv_my_food_score);

            //this.tvScore.setTextColor(this.tv2.getContext().getResources().getColor(R.color.light_grey));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick( item, getAdapterPosition(), 0);
                    }
                }
            });
        }
    }
}
