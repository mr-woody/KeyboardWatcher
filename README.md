# KeyboardWatcher

最完善的键盘弹出和键盘隐藏监听处理。


##Gradle 引用

compile 'com.woodys.tools.keyboard:KeyboardWatcher:1.0.1'

###示例代码

```
    private KeyboardWatcher keyboardWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        keyboardWatcher = KeyboardWatcher.get().init(this, getWindow().getDecorView(), new OnKeyboardStateChangeListener() {
            @Override
            public void onKeyboardStateChange(boolean isShow, int heightDifference) {
                if(isShow){
                    Toast.makeText(getApplication(), "键盘显示了！高度差："+heightDifference, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplication(), "键盘隐藏了！高度差："+heightDifference, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyboardWatcher.release();
    }
```

