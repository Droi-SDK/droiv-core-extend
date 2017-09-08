package com.droi.sdk.example;

import android.app.Application;

import com.droi.sdk.core.Core;

/**
 * Created by chenpei on 2017/9/8.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Core.initialize(this);
    }
}
