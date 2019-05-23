package com.ebrightmoon.common.util;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * 作者：create by  Administrator on 2018/12/17
 * 邮箱：2315813288@qq.com
 * 在application onCreate中
 * //设置全局默认字体样式
 * //        FontUtils.setDefaultFont(this, "SERIF", "font/Roboto.otf");
 * //        FontUtils.setDefaultFont(this, "MONOSPACE", "font/DroidSans.ttf");
 */
public class FontUtils {

    private FontUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 设置自定义字体
     *
     * @param context
     * @param staticTypefaceFieldName 需要替换的系统字体样式
     * @param fontAssetName           替换后的字体样式
     */
    public static void setDefaultFont(Context context, String staticTypefaceFieldName, String fontAssetName) {
        // 根据路径得到Typeface
        Typeface regular = Typeface.createFromAsset(context.getAssets(), fontAssetName);
        // 设置全局字体样式
        replaceFont(staticTypefaceFieldName, regular);
    }

    private static void replaceFont(String staticTypefaceFieldName, final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            //替换系统字体样式
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
