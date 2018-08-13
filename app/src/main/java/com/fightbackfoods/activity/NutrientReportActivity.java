package com.fightbackfoods.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.transition.Visibility;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.fightbackfoods.adapter.NutrientWeightAdapter;
import com.fightbackfoods.api.ResponseNutrients;
import com.fightbackfoods.model.Food;
import com.fightbackfoods.model.Nutrients;
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


    @BindView(R.id.tv_food_name)
    TextView tvName;
    @BindView(R.id.tv_no_of_servings)
    TextView tvNoServings;
    @BindView(R.id.tv_servings_size)
    TextView tvServingSize;

    @BindView(R.id.rv_list)
    RecyclerView rvList;


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
                    setList(nutrients);

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

    private void setList(List<Nutrients> nutrients) {
        final NutrientWeightAdapter adapter = new NutrientWeightAdapter((List)nutrients);
        rvList.setAdapter(adapter);
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
                toolbar.setVisibility(View.GONE);
                Visibility returnTransition = returnTransition();
                getWindow().setReturnTransition(returnTransition);

                finishAfterTransition();
            }
        });
        toolbar.setTitle(getString(R.string.title_nutrient_report));
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
