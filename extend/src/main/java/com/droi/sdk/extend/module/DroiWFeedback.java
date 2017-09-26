package com.droi.sdk.extend.module;

import android.app.Activity;

import com.droi.sdk.extend.LogUtil;
import com.droi.sdk.feedback.DroiFeedback;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

/**
 * Created by chenpei on 2017/9/20.
 */

public class DroiWFeedback extends WXModule {

    @JSMethod(uiThread = false)
    public void callFeedback(JSCallback jsCallback) {
        boolean success = false;
        if (mWXSDKInstance.getContext() instanceof Activity) {
            DroiFeedback.callFeedback((Activity) mWXSDKInstance.getContext());
            success = true;
        }
        if (jsCallback != null) {
            jsCallback.invoke(success);
        }
        if (success) {
            LogUtil.i("success");
        } else {
            LogUtil.e("failed");
        }
    }
}
