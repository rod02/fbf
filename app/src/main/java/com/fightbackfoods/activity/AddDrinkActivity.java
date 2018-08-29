package com.fightbackfoods.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fightbackfoods.R;
import com.fightbackfoods.adapter.SpinnerSimpleAdapter;
import com.fightbackfoods.api.ResponseDrink;
import com.fightbackfoods.model.Drink;
import com.fightbackfoods.model.Item;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDrinkActivity extends BaseActivity2 implements View.OnClickListener {

    private static final String TAG =AddDrinkActivity.class.getSimpleName() ;
    @BindView(R.id.btn_250)
    Button btn250;
    @BindView(R.id.btn_500)
    Button btn500;
    @BindView(R.id.btn_1000)
    Button btn1000;
    @BindView(R.id.et_value)
    EditText etValue;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.iv_save)
    ImageView ivSave;
    @BindView(R.id.sp_unit)
    Spinner spUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);
        setupWindowAnimations();
        setupLayout();
    }

    private void setupLayout() {
        btn250.setOnClickListener(this);
        btn500.setOnClickListener(this);
        btn1000.setOnClickListener(this);
        ivSave.setOnClickListener(this);
        spUnit.setSelection(1);

        Drink.unitsGet(new Callback<ResponseDrink.Units>(){

            @Override
            public void onResponse(Call<ResponseDrink.Units> call, Response<ResponseDrink.Units> response) {
                Log.d(TAG, "drink units onResponse "+response.toString());
                if(response.isSuccessful()){
                    ResponseDrink.Units rs= response.body();
                    if(rs.isSuccessful()){
                        List<Drink.Units> list = rs.getUnits();
                        list.add(0, new Drink.Units());
                        spUnit.setAdapter(new SpinnerSimpleAdapter((List) list));
                        spUnit.setSelection(1);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDrink.Units> call, Throwable t) {
                t.printStackTrace();
            }
        } );
    }

    private void setupWindowAnimations() {
        getWindow().setEnterTransition(enterTransition());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_250:
                etValue.setText("250");
                spUnit.setSelection(1);
                break;
            case R.id.btn_500:
                etValue.setText("500");
                spUnit.setSelection(1);

                break;
            case R.id.btn_1000:
                etValue.setText("1000");
                spUnit.setSelection(1);

                break;
            case R.id.iv_save:
                save();
                break;
        }
    }

    private void save() {
        int value = Integer.parseInt(etValue.getText().toString().trim());
        if(value==0)return;
        String unitId = ((com.fightbackfoods.interfaces.Item) spUnit.getSelectedItem()).getId();
        Drink.add(value, unitId, new Callback<ResponseDrink>() {
            @Override
            public void onResponse(Call<ResponseDrink> call, Response<ResponseDrink> response) {
                Log.d(TAG, "drink units onResponse "+response.toString());
                if(response.isSuccessful()){
                    ResponseDrink rs= response.body();
                    if(rs.isSuccessful()){
                        // Toast.makeText(AddFoodActivity.this, "save",Toast.LENGTH_SHORT).show();
                        // onBackPressed();
                        done();

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDrink> call, Throwable t) {
                t.printStackTrace();
            }
        });

        /*
        Toast.makeText(this, "save",Toast.LENGTH_SHORT).show();
        onBackPressed();*/
    }


    private void done() {
        toolbar.setVisibility(View.GONE);
        Toast.makeText(AddDrinkActivity.this, "save",Toast.LENGTH_SHORT).show();

        Visibility returnTransition = returnTransition();
        getWindow().setReturnTransition(returnTransition);
        try {
            LocalBroadcastManager l = LocalBroadcastManager.getInstance(this);
            Intent intent = new Intent();
            intent.setAction(FoodFragment.ACTION_MY_DRINKS_NEW_ITEM);
            l.sendBroadcast(intent);

        }catch (NullPointerException e){
            e.printStackTrace();
        }
        finishAfterTransition();

    }
    @Override
    public void setupToolbar() {
        super.setupToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Visibility returnTransition = returnTransition();
                getWindow().setReturnTransition(returnTransition);

                finishAfterTransition();
                */
                onBackPressed();
            }
        });
        //toolbar.setTitle(getString(R.string.back));


    }

}
