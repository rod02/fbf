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

import com.fightbackfoods.R;
import com.fightbackfoods.adapter.MyDrinkAdapter;
import com.fightbackfoods.adapter.OnItemClick;
import com.fightbackfoods.api.ResponseDiet;
import com.fightbackfoods.api.ResponseDrink;
import com.fightbackfoods.model.Drink;
import com.fightbackfoods.model.UserDiet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyDrinks extends FrameLayout {
    private static final String TAG = MyDrinks.class.getSimpleName();

    @BindView(R.id.rv_drinks)
    RecyclerView rvDrinks;

    String date = "";

    public MyDrinks(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.container_my_drinks, this, true);
        ButterKnife.bind(this);
        setLayout();


    }



    private void setLayout() {
       fetch(date);
    }

    private void fetch(String date) {
        Drink.get(date, new Callback<ResponseDrink>() {
            @Override
            public void onResponse(Call<ResponseDrink> call, Response<ResponseDrink> response) {
                Log.d(TAG, "onResponse "+response.toString());
                try{
                    if(!response.isSuccessful()) {
                        Log.d(TAG, "onResponse not successful");

                        return;
                    }
                    ResponseDrink rs= response.body();
                    Log.d(TAG, "onResponse size "+rs.getDrinks().size());

                    //setUserDrinks(rs.getDrinks(), (OnItemClick<Drink>) getContext());
                    setUserDrinks(rs.getDrinks(),null);

                }catch (NullPointerException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseDrink> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    public MyDrinks(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyDrinks(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyDrinks(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    MyDrinkAdapter adapter;
    public void setUserDrinks(List<Drink> list, OnItemClick<Drink> listener){
        adapter = new MyDrinkAdapter(list,listener);
        rvDrinks.setAdapter(adapter);
        rvDrinks.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDrinks.setHasFixedSize(true);//every item of the RecyclerView has a fix size
        rvDrinks.post(new Runnable() {
            @Override
            public void run() {
                adapter.updateItems();
            }
        });
    }

    public void refresh() {
        fetch(date);
    }
}