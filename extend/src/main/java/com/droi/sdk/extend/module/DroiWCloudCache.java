package com.droi.sdk.extend.module;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCloudCache;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

/**
 * Created by chenpei on 2017/9/11.
 */

public class DroiWCloudCache extends WXModule {

    @JSMethod(uiThread = false)
    public void set(String key, String value, JSCallback jsCallback) {
        DroiError droiError = DroiCloudCache.set(key, value);
        DroiResult result = new DroiResult();
        result.Code = droiError.getCode();
        if (jsCallback != null) {
            jsCallback.invoke(result.toMap());
        }
    }

    @JSMethod(uiThread = false)
    public void get(String key, JSCallback jsCallback) {
        DroiError droiError = new DroiError();
        String value = DroiCloudCache.get(key, droiError);
        DroiResult result = new DroiResult();
        result.Code = droiError.getCode();
        result.StringResult = value;
        if (jsCallback != null) {
            jsCallback.invoke(result.toMap());
        }
    }

    @JSMethod(uiThread = false)
    public void delete(String key, JSCallback jsCallback) {
        DroiError droiError = DroiCloudCache.remove(key);
        DroiResult result = new DroiResult();
        result.Code = droiError.getCode();
        if (jsCallback != null) {
            jsCallback.invoke(result.toMap());
        }
    }
}
