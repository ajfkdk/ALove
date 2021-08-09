package com.example.myapp_ui;

import android.app.Application;

import com.hjq.toast.ToastUtils;

public class loveApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化 Toast 框架
        ToastUtils.init(this);
    }
}
