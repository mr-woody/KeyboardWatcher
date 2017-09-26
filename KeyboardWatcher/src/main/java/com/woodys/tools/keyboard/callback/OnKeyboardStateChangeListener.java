package com.woodys.tools.keyboard.callback;

/**
 * Created by woodys on 2017/9/21.
 *
 */

public interface OnKeyboardStateChangeListener {
    /**
     * 监听键盘状态变化监听
     * @param isShow 是否显示
     * @param heightDifference 界面变化的高度差
     */
    void onKeyboardStateChange(boolean isShow, int heightDifference);
}
