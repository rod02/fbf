package com.fightbackfoods.adapter;

import android.content.res.Resources;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.fightbackfoods.interfaces.Item;
import com.fightbackfoods.model.Measure;
import com.fightbackfoods.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class NutrientMeasurementAdapter implements SpinnerAdapter {

    private List<Measure> items = new ArrayList<>();
    private int layoutResId = android.R.layout.simple_list_item_1;
    private int textViewResid = android.R.id.text1;

    public NutrientMeasurementAdapter(List<Measure> items) {
        this.items = items;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return bind(position,parent);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        try {
            return items.size();

        }catch (NullPointerException e){
            return 0;
        }
    }

    @Override
    public Measure getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        try {
            return position;

        }catch (NullPointerException e){
            return position;
        }catch (NumberFormatException e){
            return position;
        }
    }

    public String getItemIdAt(int position){
        try {
            return getItem(position).getLabel();

        }catch (NullPointerException e){
            return "0";
        }
    }
    public String getSelectedLabel(int position){
        try {
            return getItem(position).getLabel();

        }catch (NullPointerException e){
            return "0";
        }
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        try {
            TextView tv = (TextView) view.findViewById(textViewResid);
            Measure item = getItem(position);
            Resources res = tv.getResources();
            tv.setText(res.getString(R.string.measures_label_n_unit_n_value,
                    item.getLabel(), item.getEqv(),  item.getEunit()));
            tv.setTextColor(res.getColor(R.color.colorAccent));
            tv.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
           // tv.setGravity(Gravity.CENTER_VERTICAL);

        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return view;
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
            return items.isEmpty();
        }catch (NullPointerException e){

        }
        return true;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
    private View bind(int position, ViewGroup parent){

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        try {
            TextView tv = (TextView) view.findViewById(textViewResid);
            Measure item = getItem(position);
            Resources res = tv.getResources();
            tv.setText(res.getString(R.string.measures_label_n_unit_n_value,
                    item.getLabel(), item.getEqv(),  item.getEunit()));
            // tv.setTextColor(res.getColor(R.color.colorAccent));

        }catch (NullPointerException e){

        }

        return view;
    }
}
