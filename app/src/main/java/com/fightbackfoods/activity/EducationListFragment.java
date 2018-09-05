package com.fightbackfoods.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fightbackfoods.R;
import com.fightbackfoods.api.ResponseArticle;
import com.fightbackfoods.interfaces.OnFragmentInteractionListener;
import com.fightbackfoods.interfaces.SerializableListener;
import com.fightbackfoods.model.EducationItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EducationListFragment extends MyFragment {

    private static final String TAG = EducationListFragment.class.getSimpleName();


    private RecyclerView recyclerView;
    private MyEducationRecyclerViewAdapter adapter;
    private List<EducationItem> list;
    public EducationListFragment() {
    }


    public static EducationListFragment newInstance(int id) {
        EducationListFragment fragment = new EducationListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, 1);
        args.putInt(TAG_KEY, id);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_education_list, container, false);
        EducationItem.get(new Callback<ResponseArticle.Category>() {
            @Override
            public void onResponse(Call<ResponseArticle.Category> call, Response<ResponseArticle.Category> response) {
                Log.d(TAG, "onresponse "+response.toString());
                try {
                    if(response.isSuccessful()){
                        ResponseArticle.Category rs = response.body();
                        if(!rs.isSuccessful()){
                            Log.d(TAG, "onresponse not successful");
                            return;
                        }
                        EducationItem.setCache(rs.getCategories());
                        bindList();
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseArticle.Category> call, Throwable t) {
                t.printStackTrace();
            }
        });
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter= new MyEducationRecyclerViewAdapter(EducationItem.getCache(), mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private void bindList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter= new MyEducationRecyclerViewAdapter(EducationItem.getCache(), mListener);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
