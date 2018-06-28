package com.example.admin.week1projectflick.adapter;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class BindingAdapterUtils {
    public static void loadImage(Context context, ImageView imageView, String url){
        RequestOptions requestOptions=new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(100));
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }
}
