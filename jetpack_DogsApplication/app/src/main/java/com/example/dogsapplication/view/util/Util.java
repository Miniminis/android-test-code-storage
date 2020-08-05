package com.example.dogsapplication.view.util;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dogsapplication.R;

public class Util {

    public static void loadImage(ImageView imageView, String url, CircularProgressDrawable circularProgressDrawable) {
        /*CircularProgressDrawable circularProgressDrawable : 각 이미지 마다 progress bar 설정해주는 것 : 로딩되고 있음을 유저에게 알림! */

        //이미지가 로딩될때 option 설정
        RequestOptions options = new RequestOptions()
                .placeholder(circularProgressDrawable)  //progress bar 표시 : placeholder
                .error(R.mipmap.ic_dog_launcher_foreground);     //이미지 불러오기 실패시 띄울 대체 이미지 설정

        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(options)
                .load(url)
                .into(imageView);
    }

    /* 리스트에서 각 이미지마다 크기 등 세밀한 설정 */
    public static CircularProgressDrawable getProgressDrawable(Context context) {
        CircularProgressDrawable cpd = new CircularProgressDrawable(context);
        cpd.setStrokeWidth(10f);
        cpd.setCenterRadius(50f);
        cpd.start();
        return cpd;
    }

    @BindingAdapter("android:imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        loadImage(imageView, url, getProgressDrawable(imageView.getContext()));
    }
}

