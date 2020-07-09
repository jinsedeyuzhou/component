package com.ebrightmoon.common.binding.viewadapter.image;


import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.ebrightmoon.common.widget.imageloader.ILoader;
import com.ebrightmoon.common.widget.imageloader.LoaderManager;


/**
 *
 */
public final class ViewAdapter {
    @BindingAdapter(value = {"url", "placeholderRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            LoaderManager.getLoader().loadNet(imageView,url, ILoader.Options.defaultOptions());
        }
    }
}

