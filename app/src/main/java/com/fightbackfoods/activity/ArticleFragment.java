package com.fightbackfoods.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fightbackfoods.R;
import com.fightbackfoods.adapter.GridSpacingItemDecoration;
import com.fightbackfoods.adapter.SimpleGridAdapter;
import com.fightbackfoods.api.ResponseArticle;
import com.fightbackfoods.interfaces.OnFragmentInteractionListener;
import com.fightbackfoods.interfaces.SerializableListener;
import com.fightbackfoods.model.Article;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleFragment extends MyFragment {

    private static final String TAG = ArticleFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    @BindView(R.id.rv_list)
    RecyclerView rvList;


    @BindView(R.id.et_search)
    EditText etSearch;


    private SerializableListener mListener;
    Unbinder unbinder;
    SimpleGridAdapter adapter;


    public ArticleFragment() {
        // Required empty public constructor
    }

    public static ArticleFragment newInstance(String param1, int pos) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(TAG_KEY, 5);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_article, container, false);
        unbinder = ButterKnife.bind(this, view);

        Article.get(Article.TYPE.EDUCATION, mParam1, callback);
        setupLayout();
        return view;
    }

    private void setupLayout() {
        setList(Article.getCache(mParam1));
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG,"onTextChanged ["+s+"]");
                if(adapter==null)return;
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    Callback<ResponseArticle> callback = new Callback<ResponseArticle>(){
        @Override
        public void onResponse(Call<ResponseArticle> call, Response<ResponseArticle> response) {
            Log.d(TAG, "article onresponse "+response.toString());
            try {
                if(response.isSuccessful()){
                    ResponseArticle rs = response.body();
                    Article.setCache(mParam1, rs.getArticles());
                    setList(rs.getArticles());
                }


            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<ResponseArticle> call, Throwable t) {
            t.printStackTrace();
        }
    };

    private void setList(List<Article> articles) {

        if(articles==null) articles = new ArrayList<>();
        adapter= new SimpleGridAdapter(getActivity(), (List) articles, mListener);
        rvList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        int spanCount = 3; // 3 columns
        int spacing = 20; // 50px
        boolean includeEdge = true;
        rvList.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        rvList.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SerializableListener) {
            mListener = (SerializableListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SerializableListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
