package com.fightbackfoods.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fightbackfoods.R;

import butterknife.BindView;

public class AddDrinkActivity extends BaseActivity2 implements View.OnClickListener {

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
    }

    private void setupWindowAnimations() {
        getWindow().setEnterTransition(enterTransition());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_250:
                etValue.setText("250");
                break;
            case R.id.btn_500:
                etValue.setText("500");
                break;
            case R.id.btn_1000:
                etValue.setText("1000");
                break;
            case R.id.iv_save:
                save();
                break;
        }
    }

    private void save() {
        if(etValue.getText().toString().trim().length()==0)return;
        Toast.makeText(this, "save",Toast.LENGTH_SHORT).show();
        onBackPressed();
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
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(0, 0);
//    }
//
}
