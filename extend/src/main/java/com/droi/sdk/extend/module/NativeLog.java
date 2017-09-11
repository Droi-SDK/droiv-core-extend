package com.droi.sdk.extend.module;

import android.util.Log;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenpei on 2017/9/11.
 */

public class NativeLog extends WXModule {

    @JSMethod()
    public void printLog(String msg) {
//        try {
//            //JSONObject jsonObject = new JSONObject(msg);
////            String resultString = jsonObject.optString("Result");
////            JSONArray jsonArray = new JSONArray(resultString);
//            //JSONArray jsonArray =  jsonObject.optJSONArray("Result");
//            //Log.e("chenpei", jsonArray.toString());
//            //Log.e("chenpei",jsonArray.get(0).toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        Log.e("chenpei", msg);
    }
}
