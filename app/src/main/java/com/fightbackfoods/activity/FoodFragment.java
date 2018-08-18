package com.fightbackfoods.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fightbackfoods.R;
import com.fightbackfoods.adapter.OnItemClick;
import com.fightbackfoods.interfaces.OnFragmentInteractionListener;
import com.fightbackfoods.interfaces.SerializableListener;
import com.fightbackfoods.model.UserDiet;
import com.fightbackfoods.view.BannerFeatured;
import com.google.android.gms.vision.text.Line;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FoodFragment extends MyFragment implements View.OnClickListener {
    private static final String TAG = FoodFragment.class.getSimpleName();

    Unbinder unbinder;

    @BindView(R.id.btn_add_food)
    Button btnAddFood;

    @BindView(R.id.btn_add_drink)
    Button btnAddDrink;
    @BindView(R.id.article_preview)
    BannerFeatured blogPreviewLayout;
    @BindView(R.id.ll_daily_score)
    LinearLayout layDailyScoreSummary;
    @BindView(R.id.ll_weekly_score)
    LinearLayout layWeeklyScoreSummary;


    private OnFragmentInteractionListener mListener;

    public FoodFragment() {
        // Required empty public constructor
    }

    public static FoodFragment newInstance(int id) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putInt(TAG_KEY, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pos = getArguments().getInt(TAG_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupLayout();

        return view;
    }

    private void setupLayout() {
        btnAddFood.setOnClickListener(this);
        btnAddDrink.setOnClickListener(this);
        blogPreviewLayout.setListener((SerializableListener) getActivity());
        layDailyScoreSummary.setOnClickListener(this);
        layWeeklyScoreSummary.setOnClickListener(this);

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            unbinder.unbind();
        }catch (NullPointerException e){

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_food:
                addFood(v);
                break;
            case R.id.btn_add_drink:
                addDrink(v);
                break;
            case R.id.ll_daily_score:
                summary(v);
                break;
            case R.id.ll_weekly_score:
                summary(v);
                break;
                default:
                    break;

        }
    }


    public void addFood(View v) {
        Intent i = new Intent(getActivity(), AddFoodActivity.class);
        ((BaseActivity)getActivity()).transitionTo(i);
    }
    public void addDrink(View v) {
        Intent i = new Intent(getActivity(), AddDrinkActivity.class);
        ((BaseActivity)getActivity()).transitionTo(i);
    }

    public void summary(View v) {
        Intent i = new Intent(getActivity(), SummaryActivity.class);
        ((BaseActivity)getActivity()).transitionTo(i);
    }



}
