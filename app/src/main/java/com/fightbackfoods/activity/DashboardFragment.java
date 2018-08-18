package com.fightbackfoods.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.fightbackfoods.R;
import com.fightbackfoods.interfaces.OnFragmentInteractionListener;
import com.fightbackfoods.interfaces.SerializableListener;
import com.fightbackfoods.view.BannerFeatured;
import com.fightbackfoods.view.BannerFeatured;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DashboardFragment extends MyFragment {

    private static final String TAG = DashboardFragment.class.getSimpleName();

    private View.OnClickListener mListener;
    Unbinder unbinder;

    @BindView(R.id.article_preview)
    BannerFeatured BannerFeatured;

    @BindView(R.id.rl_food)
    RelativeLayout rlFood;
    @BindView(R.id.rl_lifestyle)
    RelativeLayout rlLifestyle;
    @BindView(R.id.rl_education)
    RelativeLayout rlEducation;
    @BindView(R.id.rl_community)
    RelativeLayout rlCommunity;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(int id) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putInt(TAG_KEY,id);
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
      //  return inflater.inflate(R.layout.fragment_dashboard, container, false);
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupLayout();
        return view;
    }

    private void setupLayout() {
        BannerFeatured.setListener((SerializableListener) getActivity());
        rlFood.setOnClickListener(mListener);
        rlLifestyle.setOnClickListener(mListener);
        rlEducation.setOnClickListener(mListener);
        rlCommunity.setOnClickListener(mListener);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (View.OnClickListener) context;
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



}
