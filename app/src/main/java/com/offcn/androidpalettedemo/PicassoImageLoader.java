package com.offcn.androidpalettedemo;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImageLoader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * ==============================
 * 作者：lushan
 * 创建时间：2020-02-21
 * 描述:
 * ===============================
 */
public class PicassoImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
                Picasso.get()
                .load(Uri.fromFile(new File(path)))//
                .placeholder(R.mipmap.ic_launcher)//
                .error(R.mipmap.ic_launcher)//
                .resize(width, height)//
                .centerInside()//
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
                .into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {

    }

    @Override
    public void clearMemoryCache() {
        //这里是清除缓存的方法,根据需要自己实现
    }
}