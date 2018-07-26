package com.fightbackfoods.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fightbackfoods.R;
import com.fightbackfoods.interfaces.OnFragmentInteractionListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FoodFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = FoodFragment.class.getSimpleName();

    Unbinder unbinder;

    @BindView(R.id.btn_add_food)
    Button btnAddFood;

    private OnFragmentInteractionListener mListener;

    public FoodFragment() {
        // Required empty public constructor
    }

    public static FoodFragment newInstance(String param1, String param2) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

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
                default:
                    break;

        }
    }


    public void addFood(View v) {
        Intent i = new Intent(getActivity(), AddFoodActivity.class);
        ((BaseActivity)getActivity()).transitionTo(i);


    }


}
