package com.droi.sdk.extend.module;

import android.util.Log;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

/**
 * Created by chenpei on 2017/9/11.
 */

public class NativeLog extends WXModule {

    @JSMethod()
    public void printLog(String msg) {
        Log.e("CoreExtend", "NativeLog:" + msg);
    }
}
