package com.droi.sdk.extend.module;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.extend.LogUtil;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenpei on 2017/9/8.
 */

public class DroiWUser extends WXModule {

    private DroiUser.OtpType getOtpType(String otpType) {
        if (otpType.toUpperCase().equals("PHONE")) {
            return DroiUser.OtpType.PHONE;
        } else {
            return DroiUser.OtpType.EMAIL;
        }
    }

    @JSMethod(uiThread = false)
    public void loginOTP(String phoneNum, String otpType, String otp, JSCallback jsCallback) {
        DroiError droiError = new DroiError();
        DroiUser droiUser = DroiUser.loginOTP(phoneNum, getOtpType(otpType), otp, droiError);
        DroiResult result = new DroiResult();
        result.Code = droiError.getCode();
        try {
            if (droiError.isOk()) {
                JSONObject data = new JSONObject(droiUser.toString());
                JSONObject resultJSONObject = new JSONObject();
                resultJSONObject.put("Data", data);
                resultJSONObject.put("Token", droiUser.getSessionToken());
                //resultJSONObject.put("ExpiredAt",droiUser.getExpiredAt());
                result.Result = resultJSONObject;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (jsCallback != null) {
                jsCallback.invoke(result.toMap());
            }
            if (droiError.isOk()) {
                LogUtil.i("success:" + droiUser.toString());
            } else {
                LogUtil.e("failed:" + droiError.toString());
            }
        }
    }

    @JSMethod()
    public void requestOTP(String phoneNum, String otpType, final JSCallback jsCallback) {
        DroiUser.requestOTPInBackground(phoneNum, getOtpType(otpType), new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                DroiResult result = new DroiResult();
                result.Code = droiError.getCode();
                if (jsCallback != null) {
                    jsCallback.invoke(result.toMap());
                }
                if (droiError.isOk()) {
                    LogUtil.i("success");
                } else {
                    LogUtil.e("failed:" + droiError.toString());
                }
            }
        });
    }

    @JSMethod()
    public void getCurrentUser(JSCallback jsCallback) {
        DroiUser droiUser = DroiUser.getCurrentUser();
        DroiResult result = new DroiResult();
        result.Code = 0;
        JSONObject data;
        JSONObject resultJSONObject = null;
        try {
            data = new JSONObject(droiUser.toString());
            resultJSONObject = new JSONObject();
            resultJSONObject.put("Data", data);
            resultJSONObject.put("Token", droiUser.getSessionToken());
            //resultJSONObject.put("ExpiredAt",droiUser.getExpiredAt());
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            result.Result = resultJSONObject;
            if (jsCallback != null) {
                jsCallback.invoke(result.toMap());
            }
            if (resultJSONObject != null) {
                LogUtil.i("success:" + resultJSONObject);
            } else {
                LogUtil.e("failed");
            }
        }
    }
}
