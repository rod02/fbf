package com.fightbackfoods.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.fightbackfoods.interfaces.OnFragmentInteractionListener;
import com.fightbackfoods.model.EducationItem;

import java.util.List;

public class MyEducationRecyclerViewAdapter extends RecyclerView.Adapter<MyEducationRecyclerViewAdapter.ViewHolder> {

    private final List<EducationItem> mValues;
    private final OnFragmentInteractionListener mListener;

    public MyEducationRecyclerViewAdapter(List<EducationItem> items, OnFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_education_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Context mContext = holder.mView.getContext();
        holder.mItem = mValues.get(position);
        //MyGlide.load(mContext,holder.mItem.getImageUrl(),holder.ivThumb);

        holder.tvName.setText(holder.mItem.getName());
        holder.tvDesc.setText(holder.mItem.getDescription());
        holder.tvTotal.setText(mContext.getResources().getQuantityString(R.plurals.articles,
                holder.mItem.getTotal(), holder.mItem.getTotal()));
        holder.ivThumb.setImageResource(holder.mItem.getImageRes());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView ivThumb;
        public final TextView tvName;
        public final TextView tvDesc;
        public final TextView tvTotal;

        public EducationItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvDesc = (TextView) view.findViewById(R.id.tv_desc);
            tvTotal = (TextView) view.findViewById(R.id.tv_total);
            ivThumb = (ImageView) view.findViewById(R.id.iv_thumb);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvName.getText() + "'";
        }
    }
}
