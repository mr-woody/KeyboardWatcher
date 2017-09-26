package com.woodys.tools.keyboard;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

import com.woodys.tools.keyboard.callback.OnKeyboardChangeListener;
import com.woodys.tools.keyboard.utils.DisplayMetricsUtils;

/**
 * Created by woodys on 2017/9/21.
 */

public class KeyboardWatcher{
    //默认软键盘最小高度
    private final static int SOFTINPUT_HEIGHT_DEFAULT = 90;
    private GlobalLayoutListener globalLayoutListener;
    private Context context;
    private View decorView;

    private KeyboardWatcher(){}

    public static KeyboardWatcher get(){
        return new KeyboardWatcher();
    }

    public KeyboardWatcher init(Context context,View decorView,OnKeyboardChangeListener listener){
        this.context=context;
        this.decorView=decorView;
        this.globalLayoutListener = new GlobalLayoutListener(listener);
        addSoftKeyboardChangedListener();
        return this;
    }

    /**
     * 释放资源
     */
    public void release() {
        removeSoftKeyboardChangedListener();
        globalLayoutListener = null;
    }

    /**
     * 取消软键盘状态变化监听
     */
    private void removeSoftKeyboardChangedListener() {
        if (globalLayoutListener != null && null!=decorView) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                decorView.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
            }else {
                decorView.getViewTreeObserver().removeGlobalOnLayoutListener(globalLayoutListener);
            }
        }
    }

    /**
     * 注册软键盘状态变化监听
     */
    private void addSoftKeyboardChangedListener() {
        if (globalLayoutListener != null && null!=decorView) {
            removeSoftKeyboardChangedListener();
            decorView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
        }
    }

    /**
     * 判断键盘是否显示
     * @param context
     * @param decorView
     * @return
     */

    public boolean isKeyboardShowing(Context context,View decorView) {
        Rect outRect = new Rect();
        //指当前Window实际的可视区域大小，通过decorView获取到程序显示的区域，包括标题栏，但不包括状态栏。
        decorView.getWindowVisibleDisplayFrame(outRect);
        int displayScreenHeight = DisplayMetricsUtils.getDisplayScreenHeight(context);

        //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态。
        int heightDifference = displayScreenHeight - (outRect.bottom - outRect.top);
        return heightDifference > Math.max(displayScreenHeight/3,SOFTINPUT_HEIGHT_DEFAULT * DisplayMetricsUtils.getDisplayScreenDensity(context));
    }


    public class GlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        private boolean isKeyboardShow = false;
        private OnKeyboardChangeListener onKeyboardChangeListener;

        public GlobalLayoutListener(OnKeyboardChangeListener onKeyboardChangeListener){
            this.isKeyboardShow = false;
            this.onKeyboardChangeListener = onKeyboardChangeListener;
        }

        @Override
        public void onGlobalLayout() {
            if(null != onKeyboardChangeListener && null != decorView){
                if (isKeyboardShowing(context, decorView)) {
                    isKeyboardShow = true;
                    onKeyboardChangeListener.onKeyboardShow();
                }else if (isKeyboardShow){
                    isKeyboardShow = false;
                    onKeyboardChangeListener.onKeyboardHidden();
                }
            }
        }
    }

}
