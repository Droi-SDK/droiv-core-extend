package com.droi.sdk.extend.module;

import android.util.Log;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiReferenceObject;
import com.droi.sdk.extend.Utils;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chenpei on 2017/9/8.
 */

public class DroiWObject extends WXModule {

    @JSMethod(uiThread = false)
    public void save(String tableName, String body, JSCallback jsCallback) {
        DroiResult result = new DroiResult();
        try {
            JSONObject object = new JSONObject(body);
            DroiObject droiObject = droiObjectFromJson(tableName, object);
            if (droiObject != null) {
                DroiError droiError = droiObject.save();
                result.Code = droiError.getCode();
                result.Result = new JSONObject(droiObject.toString());
                if (droiError.isOk()) {
                    Log.i("chenpei", "成功:");
                } else {
                    Log.e("chenpei", "失败" + droiError.toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsCallback != null) {
            jsCallback.invoke(result.toMap());
        }
    }

    private static DroiObject droiObjectFromJson(String tableName, JSONObject json) {
        DroiObject object;
        try {
            //String tableName = json.getString("_TableName");
            object = DroiObject.create(tableName);
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = json.get(key);
                if (value instanceof JSONObject && ((JSONObject) value).has("_DataType")) {
                    JSONObject valueObject = (JSONObject) value;
                    JSONObject jsonObject = valueObject.optJSONObject("_Object");
                    if (jsonObject != null) {
                        String myTableName = valueObject.optString("_TableName");
                        DroiReferenceObject referenceObject = new DroiReferenceObject();
                        switch (myTableName) {
                            case "_File":
                                DroiFile droiFile = droiFileFromJson(jsonObject);
                                referenceObject.setDroiObject(droiFile);
                                break;
                            case "_User":
                                break;
                            default:
                                DroiObject droiObject = droiObjectFromJson(myTableName, jsonObject);
                                referenceObject.setDroiObject(droiObject);
                                break;
                        }
                        value = referenceObject;
                    } else if (valueObject.has("_Id")) {
                        value = Utils.toMap(valueObject);
                    } else {
                        return null;
                    }
                }
                object.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return object;
    }

    private static DroiFile droiFileFromJson(JSONObject json) {
        String filePath = json.optString("filePath");
        File file = new File(filePath);
        return new DroiFile(file);
    }

    @JSMethod(uiThread = false)
    public void delete(String tableName, String id, JSCallback jsCallback) {
        DroiCondition condition = DroiCondition.eq("_Id", id);
        DroiQuery query = DroiQuery.Builder.newBuilder().query(tableName).where(condition).build();
        DroiError droiError = new DroiError();
        DroiObject droiObject;
        DroiResult result = new DroiResult();
        List<DroiObject> list = query.runQuery(droiError);
        if (droiError.isOk() && list.size() == 1) {
            droiObject = list.get(0);
            droiError = droiObject.delete();
        }
        result.Code = droiError.getCode();
        jsCallback.invoke(result.toMap());
    }
}
