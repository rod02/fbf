package com.fightbackfoods.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.fightbackfoods.adapter.FoodItemAdapter;
import com.fightbackfoods.adapter.OnItemClick;
import com.fightbackfoods.interfaces.Item;
import com.fightbackfoods.model.FoodItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FoodListFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";

    Unbinder unbinder;

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    OnItemClick<Item> onItemClickListener;

    public FoodListFragment() {
    }

    public static FoodListFragment newInstance(int sectionNumber) {
        FoodListFragment fragment = new FoodListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance(OnItemClick<Item> listener) {
        FoodListFragment fragment = new FoodListFragment();
        fragment.setOnItemClickListener(listener);
        return fragment;
    }

    public OnItemClick<Item> getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClick<Item> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);
        unbinder = ButterKnife.bind(this, view);
        populateList(FoodItem.createDummy(),rvList);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            unbinder.unbind();
        }catch (NullPointerException e){

        }
    }

    private void populateList(List<FoodItem> list, RecyclerView rv) {

        final FoodItemAdapter adapter = new FoodItemAdapter(getActivity(), list, onItemClickListener);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);//every item of the RecyclerView has a fix size
        rv.post(new Runnable() {
            @Override
            public void run() {
                adapter.updateItems();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }



}
