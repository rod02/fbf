package com.fightbackfoods.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.transition.Visibility;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fightbackfoods.R;
import com.fightbackfoods.adapter.NutrientMeasurementAdapter;
import com.fightbackfoods.adapter.NutrientWeightAdapter;
import com.fightbackfoods.api.ResponseDiet;
import com.fightbackfoods.api.ResponseNutrients;
import com.fightbackfoods.model.Food;
import com.fightbackfoods.model.Measure;
import com.fightbackfoods.model.Nutrients;
import com.fightbackfoods.view.SpinnerNutrientMeasure;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NutrientReportActivity extends BaseActivity2 implements OnChartValueSelectedListener {
    private static final String TAG =NutrientReportActivity.class.getSimpleName() ;
    private static final String KEY_ITEM_FOOD ="item_food" ;

    private PieChart mChart;
    Typeface mTfLight,mTfRegular;


    @BindView(R.id.iv_save)
    ImageView ivSave;
    @BindView(R.id.tv_food_name)
    TextView tvName;
    @BindView(R.id.et_servings)
    EditText etServings;

    @BindView(R.id.sp_nutrient_measure)
    SpinnerNutrientMeasure spNutrientMeasure;

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    NutrientWeightAdapter weightAdapter;
    NutrientMeasurementAdapter measurementAdapter;

    Food food;
    List<Nutrients> nutrients = new ArrayList<>();
    public static void open(Food food, BaseActivity baseActivity) {
        Intent intent = new Intent(baseActivity, NutrientReportActivity.class);
        intent.putExtra(KEY_ITEM_FOOD, food);
        baseActivity.transitionTo(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient_report);
        food= (Food) getIntent().getExtras().get(KEY_ITEM_FOOD);
        setupWindowAnimations();
        setupLayout();
        setupChart();
    }




    private void getNutrientReport() {
        Food.report(food.getNdbno(), new Callback<ResponseNutrients>() {
            @Override
            public void onResponse(Call<ResponseNutrients> call, Response<ResponseNutrients> response) {
                Log.d(TAG,"onResponse "+response.toString() );
                try {
                    ResponseNutrients rs  = response.body();
                    if(!rs.isSuccessful())return; else
                        Log.d(TAG,"onResponse success" );

                    nutrients = rs.getNutrients();
                    setupMeasurements(nutrients);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseNutrients> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setupMeasurements(List<Nutrients> nutrients) {
        measurementAdapter = new NutrientMeasurementAdapter(nutrients.get(0).getMeasures());
        spNutrientMeasure.setAdapter(measurementAdapter);
        setNutrientsWeight(nutrients);
    }

    private void setNutrientsWeight(List<Nutrients> nutrients) {
        weightAdapter = new NutrientWeightAdapter(nutrients,
                Integer.parseInt(etServings.getText().toString()),
                spNutrientMeasure.getSelectedLabel());
        rvList.setAdapter(weightAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(NutrientReportActivity.this));
        rvList.setHasFixedSize(true);//every item of the RecyclerView has a fix size
        rvList.post(new Runnable() {
            @Override
            public void run() {
                // adapter.updateItems();
            }
        });

    }

    private void setupChart() {

        mChart = findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterTextTypeface(mTfLight);
       // mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(90f);
        mChart.setTransparentCircleRadius(61f);

       // mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        setData(4, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);


        Legend l = mChart.getLegend();
        l.setEnabled(false);
       /* l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);*/


        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(8f);
    }

    private void setupLayout() {
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        tvName.setText(food.getName());
        tvName.post(new Runnable() {
            @Override
            public void run() {
                getNutrientReport();
            }
        });
        etServings.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged "+s);
                Log.d(TAG, "beforeTextChanged "+start +" "+ count+ " " +after);

                etServings.append("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged "+s);
                //etSearch.postDelayed(sendQuery, REQUEST_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged "+s);
                String n = s.toString().trim();
                if(n.equals(""))return;
                if(weightAdapter!=null)weightAdapter.setServing(Integer.parseInt(n));

            }
        });
        etServings.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(weightAdapter!=null)weightAdapter.setServing(Integer.parseInt(etServings.getText().toString()));
                    ivSave.requestFocus();
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });
        spNutrientMeasure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Measure m =(  (NutrientMeasurementAdapter) spNutrientMeasure.getAdapter()).getItem(position);
                if(weightAdapter!=null)weightAdapter.setLabel(spNutrientMeasure.getSelectedLabel());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupWindowAnimations() {
        getWindow().setEnterTransition(enterTransition());
    }

    @Override
    public void setupToolbar() {
        super.setupToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });
        toolbar.setTitle(getString(R.string.title_nutrient_report));
        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, " diet add save ");

                save();
            }
        });
    }

    private void save() {
        Log.d(TAG, " save "+food.getId()
                + " \n" +spNutrientMeasure.getSelectedLabel()
                + " \n" +etServings.getText().toString());

        Food.dietAdd(food.getId(), spNutrientMeasure.getSelectedLabel(), etServings.getText().toString(),
                new Callback<ResponseDiet>() {
            @Override
            public void onResponse(Call<ResponseDiet> call, Response<ResponseDiet> response) {
                Log.d(TAG, " diet add onResponse "+response.toString());
                try{
                    if(response.isSuccessful()){
                        ResponseDiet rs = response.body();
                        if(rs.isSuccessful()){
                            Toast.makeText(NutrientReportActivity.this, R.string.diet_add_successful, Toast.LENGTH_SHORT).show();
                            done();
                        }
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseDiet> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void done() {
        toolbar.setVisibility(View.GONE);
        Visibility returnTransition = returnTransition();
        getWindow().setReturnTransition(returnTransition);

        finishAfterTransition();
    }


    protected String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
                    mParties[i % mParties.length],
                    getResources().getDrawable(R.drawable.ic_face_happy)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(6f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
}
