package com.woodys.demos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.woodys.tools.keyboard.KeyboardWatcher;
import com.woodys.tools.keyboard.callback.OnKeyboardChangeListener;

/**
 * A login screen that offers login via email/password.
 */
public class Login3Activity extends Activity {

    // UI references.
    private KeyboardWatcher keyboardWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);

        keyboardWatcher = KeyboardWatcher.get().init(this, getWindow().getDecorView(), new OnKeyboardChangeListener() {
            @Override
            public void onKeyboardShow() {
                Toast.makeText(getApplication(), "键盘显示了！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onKeyboardHidden() {
                Toast.makeText(getApplication(), "键盘隐藏了！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyboardWatcher.release();
    }
}

