package com.fightbackfoods.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.fightbackfoods.interfaces.Item;
import com.fightbackfoods.model.Nutrients;
import com.fightbackfoods.utils.Validate;

import java.util.List;

public class NutrientWeightAdapter extends RecyclerView.Adapter<NutrientWeightAdapter.ViewHolder> {

    private final List<Nutrients> data;
    private OnItemClick onItemClick;

    private int serving;
    private String label;


    public NutrientWeightAdapter(List<Nutrients> data) {
        this(data,null);
    }
    public NutrientWeightAdapter(List<Nutrients> data, OnItemClick onItemClick){
        this.data = data;
        this.onItemClick = onItemClick;
    }
    public NutrientWeightAdapter(List<Nutrients> data, int serving, String label){
        this.data = data;
        this.serving = serving;
        this.label = label;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nutrient_weight, parent, false);
        return new ViewHolder(view, this.onItemClick);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Nutrients item  = data.get(position);
        holder.tvName.setText(item.getName());
        holder.tvWeight.setText(item.getValue(label, serving));
        holder.item=item;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Item getItem(int position){
        return data.get(position);
    }
    public void add(List<Nutrients> items) {
        int previousDataSize = this.data.size();
        this.data.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    public int getServing() {
        return serving;
    }

    public void setServing(int serving) {
        if(serving==this.serving)return;
        this.serving = serving;
        notifyDataSetChanged();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        if(Validate.isNullString(label))return;
        if(label.equals(this.label)) return;
        this.label = label;
        notifyDataSetChanged();
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
