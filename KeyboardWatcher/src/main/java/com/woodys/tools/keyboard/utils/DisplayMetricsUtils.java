package com.woodys.tools.keyboard.utils;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by woodys on 2017/9/21.
 */

public class DisplayMetricsUtils {

    /**
     * 获取的屏幕真实高度(含虚拟键所占空间)
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics realMetric = new DisplayMetrics();
        //API 17之后使用，获取的像素宽高包含虚拟键所占空间，在API 17之前通过反射获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(realMetric);
        }else{
            try {
                @SuppressWarnings("rawtypes")
                Class c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, realMetric);
            } catch (Exception e) { e.printStackTrace();}
        }
        return realMetric.heightPixels;
    }

    /**
     * 获取的当前显示屏幕的高度(不包含虚拟键所占空间)
     * @param context
     * @return
     */
    public static int getDisplayScreenHeight(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        //获取的像素宽高不包含虚拟键所占空间
        DisplayMetrics metric = new DisplayMetrics();
        display.getMetrics(metric);
        return metric.heightPixels;
    }

    /**
     * 获取屏幕的密度
     * @param context
     * @return
     */
    public static float getDisplayScreenDensity(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        //获取的像素宽高不包含虚拟键所占空间
        DisplayMetrics metric = new DisplayMetrics();
        display.getMetrics(metric);
        return metric.density;
    }

    /**
     * 获取虚拟功能键高度
     * @param context
     * @return
     */
    public static int getVirtualBarHeight(Context context){
        return getScreenHeight(context) - getDisplayScreenHeight(context);
    }
}
