package com.fightbackfoods.activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.fightbackfoods.R;
import com.squareup.picasso.Callback;

public class ImageProgressCallback implements Callback {
    ProgressBar pb;
    ImageView iv;

    public  ImageProgressCallback(ImageView imageView, ProgressBar progressBar){
        this.pb = progressBar;
        this.iv = imageView;
    }

    @Override
    public void onSuccess() {
        if(pb==null) return;
        Log.d("imageCallback"," sucess");
        pb.setVisibility(View.GONE);

    }

    @Override
    public void onError() {
        iv.setImageResource(R.drawable.no_image);
        if (pb==null) return;
        pb.setVisibility(View.GONE);

    }

}
