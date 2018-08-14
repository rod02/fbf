package com.fightbackfoods.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.Visibility;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.fightbackfoods.R;
import com.fightbackfoods.adapter.OnItemClick;
import com.fightbackfoods.adapter.SimpleAdapter;
import com.fightbackfoods.api.ResponseFoodByGroup;
import com.fightbackfoods.api.ResponseFoodList;
import com.fightbackfoods.interfaces.Item;
import com.fightbackfoods.model.Food;
import com.fightbackfoods.view.SpinnerFoodGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFoodActivity extends BaseActivity2 implements OnItemClick<Item> {
    public static final String ARG_DRAWING_START_LOCATION = "arg_drawing_start_location";
    private static final String TAG =AddFoodActivity.class.getSimpleName() ;
    private static final long REQUEST_DELAY = 3000 ;
    private int drawingStartLocation;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @BindView(R.id.main_content)
    RelativeLayout mainContent;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @BindView(R.id.sp_food_group)
    SpinnerFoodGroup spFoodGroup;
    private int offset;
    private String query ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        setupWindowAnimations();

        setupTabs();
        setupLayout();
    }

    private void setupLayout() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged "+s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged "+s);
                query=etSearch.getText().toString();
                search();
                //etSearch.postDelayed(sendQuery, REQUEST_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged "+s);

            }
        });

        spFoodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                search();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private Runnable sendQuery = new Runnable() {
        @Override
        public void run() {
            search();
        }
    };

    private Callback<ResponseFoodList> callback = new Callback<ResponseFoodList>() {
        @Override
        public void onResponse(Call<ResponseFoodList> call, Response<ResponseFoodList> response) {
            try {
                Log.d(TAG, "search onresponse "+ response.toString());
                if(!response.isSuccessful())return;
                ResponseFoodList resp = response.body();
//                if(!resp.isSuccessful()) return;
                Log.d(TAG, "search onresponse listSize "+ resp.getFoodList().size());
                final SimpleAdapter adapter = new SimpleAdapter((List)resp.getFoodList(),AddFoodActivity.this);
                rvList.setAdapter(adapter);
                rvList.setLayoutManager(new LinearLayoutManager(AddFoodActivity.this));
                rvList.setHasFixedSize(true);//every item of the RecyclerView has a fix size
                rvList.post(new Runnable() {
                    @Override
                    public void run() {
                       // adapter.updateItems();
                    }
                });

            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseFoodList> call, Throwable t) {
            try {
                t.printStackTrace();

            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    };

    private Callback<ResponseFoodByGroup> callbackGroup = new Callback<ResponseFoodByGroup>() {
        @Override
        public void onResponse(Call<ResponseFoodByGroup> call, Response<ResponseFoodByGroup> response) {
            try {
                Log.d(TAG, "search onresponse "+ response.toString());
                if(!response.isSuccessful())return;
                ResponseFoodByGroup resp = response.body();
                if(!resp.isSuccessful()) return;
                Log.d(TAG, "search onresponse listSize "+ resp.getListSize());
               // if(resp.getListSize()==0)return;
                final SimpleAdapter adapter = new SimpleAdapter((List)resp.getList(),AddFoodActivity.this);
                rvList.setAdapter(adapter);
                rvList.setLayoutManager(new LinearLayoutManager(AddFoodActivity.this));
                rvList.setHasFixedSize(true);//every item of the RecyclerView has a fix size
                rvList.post(new Runnable() {
                    @Override
                    public void run() {
                        // adapter.updateItems();
                    }
                });

            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseFoodByGroup> call, Throwable t) {
            try {
                t.printStackTrace();

            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    };

    private void search() {
        switch (spFoodGroup.getSelectedItemPosition()){
            case 0:
                if(query.equals(""))return;
                Food.search(query,offset,callback);
                break;
            default:
                try {
                    Food.searchByGroup(query,offset, spFoodGroup.getSelectedId(),callbackGroup);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setupWindowAnimations() {
        getWindow().setEnterTransition(enterTransition());
    }

    private void setupTabs() {

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager, true);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_food, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Item item, int position, long id) {
        Log.d(TAG, "onItemClick  "+ item.getName());
        hideKeyboard();
        openNutrientReports((Food) item);

    }

    private void openNutrientReports(Food food){
        NutrientReportActivity.open(food,this);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        OnItemClick<Item> listener;
        public SectionsPagerAdapter(FragmentManager fm, OnItemClick<Item> onItemClickListener) {
            super(fm);
            this.listener = onItemClickListener;
        }

        @Override
        public Fragment getItem(int position) {
            return FoodListFragment.newInstance(listener);
        }

        @Override
        public int getCount() {
            return 5;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.recent);
                case 1:
                    return getString(R.string.frequent);
                case 2:
                    return getString(R.string.my_foods);
                case 3:
                    return getString(R.string.meals);
                case 4:
                    return getString(R.string.recipes);
            }
            return null;
        }
    }

    @Override
    public Transition enterTransition() {
        Explode enterTransition = new Explode();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        return enterTransition;
    }

    @Override
    public Visibility returnTransition() {
        Visibility enterTransition = new Explode();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        return enterTransition;
    }


    @OnClick(R.id.btn_quick_add)
    public void quickAdd(View v) {
        Log.d(TAG, "quickAdd");
    }
}
