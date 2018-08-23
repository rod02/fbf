package com.fightbackfoods.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.fightbackfoods.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ImFeelingDialogFragment extends DialogFragment {

    Unbinder unbinder;

    @BindView(R.id.iv_close)
    ImageView ivClose;

    public ImFeelingDialogFragment() {
    }

    public static ImFeelingDialogFragment newInstance(String title) {
        ImFeelingDialogFragment frag = new ImFeelingDialogFragment();
        Bundle args = new Bundle();
        //args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.content_journal_2, container);

        unbinder = ButterKnife.bind(this, view);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
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
