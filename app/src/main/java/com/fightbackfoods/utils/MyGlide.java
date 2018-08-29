package com.fightbackfoods.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.fightbackfoods.R;


public class MyGlide {

    final static RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.no_image)
            .centerCrop()
            //.dontAnimate()
            .error(R.drawable.no_image);

    public static void load(Context context, String url, ImageView imageView){
        try {
            com.bumptech.glide.Glide.with(context).load(url)
                    .apply(options).into(imageView);
        }catch (Exception e){
            Log.d("MyGlide", "error");
        }
    }
}
