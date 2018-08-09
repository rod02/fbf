package com.fightbackfoods.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.fightbackfoods.interfaces.Item;

import java.util.List;

public class SimpleAdapter  extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {

    private final List<Item> data;
    private OnItemClick onItemClick;

    public SimpleAdapter(List<Item> data) {
        this(data,null);
    }
    public SimpleAdapter(List<Item> data, OnItemClick onItemClick){
        this.data = data;
        this.onItemClick = onItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view, this.onItemClick);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Item item  = data.get(position);
        holder.tvName.setText(item.getName());
        holder.tv2.setText(item.getDescription());
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
        TextView tv2;

        public ViewHolder(View view, final OnItemClick onItemClickListener) {
            super(view);
            this.tvName = (TextView) view.findViewById(android.R.id.text1);
            this.tv2 = (TextView) view.findViewById(android.R.id.text2);
            this.tv2.setTextColor(this.tv2.getContext().getResources().getColor(R.color.light_grey));
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
