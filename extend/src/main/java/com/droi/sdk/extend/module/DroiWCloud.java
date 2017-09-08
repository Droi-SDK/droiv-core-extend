package com.droi.sdk.extend.module;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCloud;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenpei on 2017/9/8.
 */

public class DroiWCloud extends WXModule {

    @JSMethod(uiThread = false)
    public void callRestApi(String apiKey, String apiPath, String methodString,
                            String params, String token, JSCallback jsCallback) throws JSONException {
        DroiCloud.Method method;
        switch (methodString.toUpperCase()) {
            case "GET":
                method = DroiCloud.Method.GET;
                break;
            case "POST":
                method = DroiCloud.Method.POST;
                break;
            case "DELETE":
                method = DroiCloud.Method.DELETE;
                break;
            case "PUT":
                method = DroiCloud.Method.DELETE;
                break;
            case "PATCH":
                method = DroiCloud.Method.DELETE;
                break;
            default:
                return;
        }

        DroiError droiError = new DroiError();
        String resultString = DroiCloud.callRestApi(apiKey, apiPath, method, params, droiError);
        DroiResult result = new DroiResult();
        result.Code = droiError.getCode();
        try {
            result.Result = new JSONObject(resultString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            result.ArrayResult = new JSONArray(resultString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsCallback.invoke(result);
    }
}