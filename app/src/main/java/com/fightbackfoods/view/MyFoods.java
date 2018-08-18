package com.fightbackfoods.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.fightbackfoods.Api;
import com.fightbackfoods.App;
import com.fightbackfoods.R;
import com.fightbackfoods.adapter.MyFoodAdapter;
import com.fightbackfoods.adapter.OnItemClick;
import com.fightbackfoods.api.ResponseDiet;
import com.fightbackfoods.model.UserDiet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFoods extends FrameLayout {
    private static final String TAG = MyFoods.class.getSimpleName();

    @BindView(R.id.rv_foods)
    RecyclerView rvFoods;


    public MyFoods(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.container_my_foods, this, true);
        ButterKnife.bind(this);
        setLayout();

    }

    private void setLayout() {
        UserDiet.get("", new Callback<ResponseDiet>() {
            @Override
            public void onResponse(Call<ResponseDiet> call, Response<ResponseDiet> response) {
                Log.d(TAG, "onResponse "+response.toString());
                if(!response.isSuccessful()) {
                    Log.d(TAG, "onResponse not successful");

                    return;
                }
                ResponseDiet rs= response.body();
                Log.d(TAG, "onResponse size "+rs.getUserDiet().size());

                setUserDiets(rs.getUserDiet(), (OnItemClick<UserDiet>) getContext());
            }

            @Override
            public void onFailure(Call<ResponseDiet> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    public MyFoods(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyFoods(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyFoods(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    MyFoodAdapter adapter;
    public void setUserDiets(List<UserDiet> list, OnItemClick<UserDiet> listener){
        adapter = new MyFoodAdapter(list,listener);
        rvFoods.setAdapter(adapter);
        rvFoods.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFoods.setHasFixedSize(true);//every item of the RecyclerView has a fix size
        rvFoods.post(new Runnable() {
            @Override
            public void run() {
                adapter.updateItems();
            }
        });
    }
}