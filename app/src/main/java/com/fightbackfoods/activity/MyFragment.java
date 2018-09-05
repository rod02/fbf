package com.fightbackfoods.activity;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.fightbackfoods.interfaces.OnFragmentInteractionListener;

public class MyFragment extends Fragment {
    protected static final String TAG_KEY = "key";
    protected static final String ARG_COLUMN_COUNT = "column-count";
    protected int mColumnCount = 1;
    protected int pos = 0;
    protected OnFragmentInteractionListener mListener;


    public int getPos() {
        pos=getArguments().getInt(TAG_KEY, 0);
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
