package com.fightbackfoods.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.fightbackfoods.interfaces.Item;

import java.util.List;

public class NutrientWeightAdapter extends RecyclerView.Adapter<NutrientWeightAdapter.ViewHolder> {

    private final List<Item> data;
    private OnItemClick onItemClick;

    public NutrientWeightAdapter(List<Item> data) {
        this(data,null);
    }
    public NutrientWeightAdapter(List<Item> data, OnItemClick onItemClick){
        this.data = data;
        this.onItemClick = onItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nutrient_weight, parent, false);
        return new ViewHolder(view, this.onItemClick);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Item item  = data.get(position);
        holder.tvName.setText(item.getName());
        holder.tvWeight.setText(item.getDescription());
        holder.item=item;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Item getItem(int position){
        return data.get(position);
    }
    public void add(List<Item> items) {
        int previousDataSize = this.data.size();
        this.data.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        Item item;
        TextView tvWeight;

        public ViewHolder(View view, final OnItemClick onItemClickListener) {
            super(view);
            this.tvName = (TextView) view.findViewById(R.id.tv_nutrient_name);
            this.tvWeight = (TextView) view.findViewById(R.id.tv_weight);
            this.tvWeight.setTextColor(this.tvWeight.getContext().getResources().getColor(R.color.light_grey));
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
