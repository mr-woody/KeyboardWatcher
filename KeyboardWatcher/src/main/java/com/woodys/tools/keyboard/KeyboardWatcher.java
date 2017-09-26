package com.woodys.tools.keyboard;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.Pair;
import android.view.View;
import android.view.ViewTreeObserver;

import com.woodys.tools.keyboard.callback.OnKeyboardStateChangeListener;
import com.woodys.tools.keyboard.utils.DisplayMetricsUtils;

/**
 * Created by woodys on 2017/9/21.
 */

public class KeyboardWatcher{
    private GlobalLayoutListener globalLayoutListener;
    private Context context;
    private View decorView;

    private KeyboardWatcher(){}

    public static KeyboardWatcher get(){
        return new KeyboardWatcher();
    }

    /**
     * 监听键盘的状态变化
     * @param context
     * @param decorView
     * @param listener
     * @return
     */
    public KeyboardWatcher init(Context context,View decorView,OnKeyboardStateChangeListener listener){
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

    public Pair<Boolean,Integer> isKeyboardShowing(Context context,View decorView) {
        Rect outRect = new Rect();
        //指当前Window实际的可视区域大小，通过decorView获取到程序显示的区域，包括标题栏，但不包括状态栏。
        decorView.getWindowVisibleDisplayFrame(outRect);
        int displayScreenHeight = DisplayMetricsUtils.getDisplayScreenHeight(context);

        //如果屏幕高度和Window可见区域高度差值大于0，则表示软键盘显示中，否则软键盘为隐藏状态。
        int heightDifference = displayScreenHeight - outRect.bottom;
        return new Pair(heightDifference >0,heightDifference);
    }


    public class GlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        private boolean isKeyboardShow = false;

        private OnKeyboardStateChangeListener onKeyboardStateChangeListener;

        public GlobalLayoutListener(OnKeyboardStateChangeListener onKeyboardStateChangeListener){
            this.isKeyboardShow = false;
            this.onKeyboardStateChangeListener = onKeyboardStateChangeListener;
        }

        @Override
        public void onGlobalLayout() {
            if(null != onKeyboardStateChangeListener && null != decorView){
                Pair<Boolean,Integer> pair = isKeyboardShowing(context, decorView);
                if (pair.first) {
                    onKeyboardStateChangeListener.onKeyboardStateChange(isKeyboardShow = true,pair.second);
                }else if (isKeyboardShow){
                    onKeyboardStateChangeListener.onKeyboardStateChange(isKeyboardShow = false,pair.second);
                }
            }
        }
    }

}
