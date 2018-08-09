package com.fightbackfoods.adapter;

import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.fightbackfoods.interfaces.Item;
import com.fightbackfoods.model.Gender;

import java.util.ArrayList;
import java.util.List;

public class SpinnerSimpleAdapter implements SpinnerAdapter {

    private List<Item> items = new ArrayList<>();
    private int layoutResId = android.R.layout.simple_list_item_1;
    private int textViewResid = android.R.id.text1;

    public SpinnerSimpleAdapter(List<Item> items) {
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
    public Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        try {
            return Long.parseLong(getItem(position).getId());

        }catch (NullPointerException e){
            return position;
        }catch (NumberFormatException e){
            return position;
        }
    }

    public String getItemIdAt(int position){
        try {
            return getItem(position).getId();

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
        return bind(position,parent);
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
            Item item = getItem(position);
            switch (position){
                case 0:
                    tv.setText("");
                    tv.setHint(R.string.choose);
                    break;

                default:
                    if(item.getName()==null && item.getName().equals("")){
                        tv.setText(item.getDescription());
                    }
                    else tv.setText(getItem(position).getName());
                    break;
            }


        }catch (NullPointerException e){

        }

        return view;
    }
}
