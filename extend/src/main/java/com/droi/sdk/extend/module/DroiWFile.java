package com.droi.sdk.extend.module;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.extend.LogUtil;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * Created by chenpei on 2017/9/12.
 */

public class DroiWFile extends WXModule {

    @JSMethod(uiThread = false)
    public void save(String filePath, JSCallback jsCallback) {
        File file = new File(filePath);
        final DroiFile droiFile = new DroiFile(file);
        DroiError droiError = droiFile.save();
        DroiResult result = new DroiResult();
        result.Code = droiError.getCode();
        try {
            if (droiError.isOk()) {
                result.Result = new JSONObject(droiFile.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (jsCallback != null) {
                jsCallback.invoke(result.toMap());
            }
            if (droiError.isOk()) {
                LogUtil.i("success");
            } else {
                LogUtil.e("failed:" + droiError.toString());
            }
        }
    }

    @JSMethod(uiThread = false)
    public void update(String id, String filePath, JSCallback jsCallback) {
        DroiCondition condition = DroiCondition.eq("_Id", id);
        DroiQuery query = DroiQuery.Builder.newBuilder().query("_File").where(condition).build();
        DroiError droiError = new DroiError();
        DroiFile droiFile;
        DroiResult result = new DroiResult();
        List<DroiFile> list = query.runQuery(droiError);
        if (droiError.isOk()) {
            if (list.size() == 1) {
                droiFile = list.get(0);
                File file = new File(filePath);
                droiError = droiFile.update(file);
                try {
                    result.Result = new JSONObject(droiFile.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                droiError.setCode(1030301);
            }
        }
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

    @JSMethod(uiThread = false)
    public void delete(String id, JSCallback jsCallback) {
        DroiCondition condition = DroiCondition.eq("_Id", id);
        DroiQuery query = DroiQuery.Builder.newBuilder().query("_File").where(condition).build();
        DroiError droiError = new DroiError();
        DroiFile droiFile;
        DroiResult result = new DroiResult();
        List<DroiFile> list = query.runQuery(droiError);
        if (droiError.isOk()) {
            if (list.size() == 1) {
                droiFile = list.get(0);
                droiError = droiFile.delete();
            } else {
                droiError.setCode(1030301);
            }
        }
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
}
