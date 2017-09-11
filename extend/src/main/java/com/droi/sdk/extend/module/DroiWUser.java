package com.droi.sdk.extend.module;

import android.util.Log;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenpei on 2017/9/8.
 */

public class DroiWUser extends WXModule {

    DroiUser.OtpType getOtpType(String otpType) {
        if (otpType.toUpperCase().equals("PHONE")) {
            return DroiUser.OtpType.PHONE;
        } else {
            return DroiUser.OtpType.EMAIL;
        }
    }

    @JSMethod(uiThread = false)
    public void loginOTP(String phoneNum, String otpType, String otp, JSCallback jsCallback) {
        DroiError droiError = new DroiError();
        DroiUser.loginOTP(phoneNum, getOtpType(otpType), otp, droiError);
        if (droiError.isOk()) {
            Log.i("chenpei", "成功");
        } else {
            Log.e("chenpei", "失败" + droiError.toString());
        }
        DroiResult result = new DroiResult();
        result.Code = droiError.getCode();
        DroiUser droiUser = DroiUser.getCurrentUser();
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
        }
        if (jsCallback != null) {
            jsCallback.invoke(result.toMap());
        }
    }

    @JSMethod()
    public void requestOTP(String phoneNum, String otpType, final JSCallback jsCallback) {
        DroiUser.requestOTPInBackground(phoneNum, getOtpType(otpType), new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                if (droiError.isOk()) {
                    Log.i("chenpei", "成功");
                } else {
                    Log.e("chenpei", "失败" + droiError.toString());
                }
                DroiResult result = new DroiResult();
                result.Code = droiError.getCode();
                if (jsCallback != null) {
                    jsCallback.invoke(result.toMap());
                }
            }
        });
    }
}
