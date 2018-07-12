package com.fightbackfoods.adapter;

import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.fightbackfoods.model.Gender;

import java.util.ArrayList;
import java.util.List;

public class SpinnerGenderAdapter implements SpinnerAdapter {

    private List<Gender> genders = new ArrayList<>();
    private int layoutResId = android.R.layout.simple_list_item_1;
    private int textViewResid = android.R.id.text1;

    public SpinnerGenderAdapter(List<Gender> genders) {
        this.genders = genders;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(position,parent);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return genders.size();
    }

    @Override
    public Gender getItem(int position) {
        return genders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position,parent);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        try{
            return genders.isEmpty();
        }catch (NullPointerException e){

        }
        return true;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
    private View createItemView(int position, ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        TextView tv = (TextView) view.findViewById(textViewResid);
        tv.setText(getItem(position).getName());
        return view;
    }
}
