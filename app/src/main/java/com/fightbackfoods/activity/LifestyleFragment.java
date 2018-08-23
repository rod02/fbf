package com.fightbackfoods.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fightbackfoods.R;
import com.fightbackfoods.interfaces.OnFragmentInteractionListener;
import com.fightbackfoods.interfaces.SerializableListener;
import com.fightbackfoods.view.BannerFeatured;
import com.fightbackfoods.view.LifeStyleItemScore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class LifestyleFragment extends MyFragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    Unbinder unbinder;

    @BindView(R.id.article_preview)
    BannerFeatured BannerFeatured;

    @BindView(R.id.mind)
    LifeStyleItemScore mind;
    @BindView(R.id.body)
    LifeStyleItemScore body;
    @BindView(R.id.soul)
    LifeStyleItemScore soul;




    public LifestyleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            pos = getArguments().getInt(TAG_KEY);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_lifestyle, container, false);
        unbinder = ButterKnife.bind(this, view);
        BannerFeatured.setListener((SerializableListener) getActivity());
        mind.setOnClickListener(this);
        body.setOnClickListener(this);
        soul.setOnClickListener(this);
        return view;
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





    public static LifestyleFragment newInstance(int id) {
        LifestyleFragment fragment = new LifestyleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, 1);
        args.putInt(TAG_KEY, id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
       // LifestyleDetailsActivity.open((BaseActivity2) getContext());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            unbinder.unbind();
        }catch (NullPointerException e){

        }
    }
}
