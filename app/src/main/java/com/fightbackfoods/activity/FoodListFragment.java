package com.fightbackfoods.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fightbackfoods.R;
import com.fightbackfoods.adapter.MyFoodAdapter;
import com.fightbackfoods.adapter.OnItemClick;
import com.fightbackfoods.api.ResponseDiet;
import com.fightbackfoods.model.FoodItem;
import com.fightbackfoods.model.UserDiet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodListFragment extends Fragment {

    private static final String TAG = FoodListFragment.class.getSimpleName();

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static final int RECENT = 0;
    public static final int FREQUENT = 0;
    public static final int MY_FOODS = 0;
    public static final int MEALS = 0;
    public static final int RECIPES = 0;


    LocalBroadcastManager localBroadcastManager;
    BroadcastReceiver receiver;

    Unbinder unbinder;

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    OnItemClick<UserDiet> onItemClickListener;

    int section;

    public FoodListFragment() {
    }

    public static FoodListFragment newInstance(int sectionNumber) {
        FoodListFragment fragment = new FoodListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance(OnItemClick<UserDiet> listener) {
        FoodListFragment fragment = new FoodListFragment();
        fragment.setOnItemClickListener(listener);
        return fragment;
    }

    public OnItemClick<UserDiet> getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClick<UserDiet> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            section = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FoodFragment.ACTION_MY_FOOD_NEW_ITEM);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "broadcast received ");
                if(intent.getAction().equals(FoodFragment.ACTION_MY_FOOD_NEW_ITEM)){
                    try {
                        fetch();

                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }

            }
        };
        localBroadcastManager.registerReceiver(receiver, intentFilter);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);
        unbinder = ButterKnife.bind(this, view);
       // populateList(FoodItem.createDummy(),rvList);
        fetch();
        return view;
    }

    private void fetch() {
        switch (section){
            case RECENT:
                fetch("");
                break;
        }
    }

    private void fetch(String date) {
        UserDiet.get(date,callback);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
           // mListener = null;
            localBroadcastManager.unregisterReceiver(receiver);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            unbinder.unbind();
            localBroadcastManager.unregisterReceiver(receiver);
            //mListener = null;

        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private Callback<ResponseDiet> callback = new Callback<ResponseDiet>() {
        @Override
        public void onResponse(Call<ResponseDiet> call, Response<ResponseDiet> response) {
            try {
                Log.d(TAG, "onResponse diet "+ response.toString());
                ResponseDiet rs= response.body();
                Log.d(TAG, "onResponse size "+rs.getUserDiet().size());

                setUserDiets(rs.getUserDiet(), onItemClickListener);

            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseDiet> call, Throwable t) {

        }
    };

    private void populateList(List<FoodItem> list, RecyclerView rv) {

       /* final FoodItemAdapter adapter = new FoodItemAdapter(getActivity(), list, onItemClickListener);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);//every item of the RecyclerView has a fix size
        rv.post(new Runnable() {
            @Override
            public void run() {
                adapter.updateItems();
            }
        });*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    public static Fragment newInstance(int section, OnItemClick<UserDiet> listener) {
        FoodListFragment fragment = new FoodListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, section);
        fragment.setArguments(args);
        fragment.setOnItemClickListener(listener);
        return fragment;
    }
    MyFoodAdapter adapter;
    public void setUserDiets(List<UserDiet> list, OnItemClick<UserDiet> listener){
         adapter = new MyFoodAdapter(list,listener);
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setHasFixedSize(true);//every item of the RecyclerView has a fix size
        rvList.post(new Runnable() {
            @Override
            public void run() {
                adapter.updateItems();
            }
        });
    }
}
