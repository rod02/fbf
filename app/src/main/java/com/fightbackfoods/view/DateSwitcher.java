package com.fightbackfoods.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.fightbackfoods.R;

import butterknife.ButterKnife;

public class DateSwitcher extends RelativeLayout {
    public DateSwitcher(@NonNull Context context) {
        this(context, null);
    }

    public DateSwitcher(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_date_page, this, true);
        ButterKnife.bind(this);

    }


}
