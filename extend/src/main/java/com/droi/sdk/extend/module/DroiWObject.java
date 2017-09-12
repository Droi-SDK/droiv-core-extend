package com.droi.sdk.extend.module;

import android.text.TextUtils;
import android.util.Log;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiReferenceObject;
import com.droi.sdk.extend.Utils;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            DroiObject droiObject = fromJson(tableName, object);
            DroiError droiError = droiObject.save();
            if (droiError.isOk()) {
                // TODO
            } else {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsCallback != null) {
            jsCallback.invoke(result.toMap());
        }
    }

    private static DroiObject fromJson(String tableName, JSONObject json) {
        DroiObject droiObject = null;
        try {
            //String tableName = json.getString("_TableName");
            droiObject = DroiObject.create(tableName);
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = json.get(key);
                if (value instanceof JSONObject && ((JSONObject) value).has("_DataType")) {
                    JSONObject jsonObject = (JSONObject) value;
                    //String dataType = jsonObject.optString("_DataType");
                    JSONObject object = jsonObject.optJSONObject("_Object");
                    String myTableName = jsonObject.getString("_TableName");
                    // TODO
                    switch (myTableName){
                        case "_File":
                            break;
                        case "_User":
                            break;
                        default:
                            break;
                    }
                    DroiObject droiObject1 = fromJson(myTableName, object);
                    DroiReferenceObject referenceObject = new DroiReferenceObject();
                    referenceObject.setDroiObject(droiObject1);
                    value = referenceObject;
                }
                droiObject.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return droiObject;
    }

    @JSMethod(uiThread = false)
    public void delete(String id, String tableName, JSCallback jsCallback) {
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
        // TODO droiObject 没有查询到
        result.Code = droiError.getCode();
        jsCallback.invoke(result.toMap());
    }

    @JSMethod(uiThread = false)
    public void query(String tableName, String whereClause, String options, JSCallback jsCallback) {
        Log.i("chenpei", "options:" + options);
        Log.i("chenpei", "whereClause:" + whereClause);
        Log.i("chenpei", "enter");
        DroiCondition cond = null;
        DroiQuery.Builder builder = DroiQuery.Builder.newBuilder().query(tableName);
        builder = parseOption(builder, options);
        try {
            if (!TextUtils.isEmpty(whereClause)) {
                JSONObject object = new JSONObject(whereClause);
                cond = parseCondition(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (cond != null) {
            builder = builder.where(cond);
        }
        DroiQuery query = builder.build();

        JSONObject optionJSONObject;
        boolean isCount = false;
        try {
            if (!TextUtils.isEmpty(options)) {
                optionJSONObject = new JSONObject(options);
                isCount = optionJSONObject.optBoolean("count", false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (isCount) {
            DroiError droiError = new DroiError();
            int count = query.count(droiError);
            DroiResult result = new DroiResult();
            result.Code = droiError.getCode();
            result.Count = count;
            if (jsCallback != null) {
                jsCallback.invoke(result.toString());
            }
            if (droiError.isOk()) {
                Log.i("chenpei", "size:" + count);
            } else {
                Log.e("chenpei", "失败" + droiError.toString());
            }
        } else {
            DroiError droiError = new DroiError();
            List<DroiObject> list = query.runQuery(droiError);
            DroiResult result = new DroiResult();
            result.Code = droiError.getCode();
            result.ArrayResult = Utils.listToJSONArray(list);
            result.Count = list.size();
            if (jsCallback != null) {
                jsCallback.invoke(result.toMap());
            }
            if (droiError.isOk()) {
                Log.i("chenpei", list.toString());
            } else {
                Log.e("chenpei", "失败" + droiError.toString());
            }
        }
    }

    private DroiCondition parseCondition(JSONObject object) {
        DroiCondition cond = null;
        Iterator<String> keys = object.keys();
        if (keys.hasNext()) {
            String key = keys.next();
            if ("$or".equals(key) || "$and".equals(key)) {
                JSONArray jsonArray = object.optJSONArray(key);
                JSONObject object0 = jsonArray.optJSONObject(0);
                DroiCondition cond0 = parseCondition(object0);
                if (cond0 == null) {
                    return null;
                }
                for (int i = 1; i < jsonArray.length(); i++) {
                    JSONObject objectN = jsonArray.optJSONObject(i);
                    DroiCondition condN = parseCondition(objectN);
                    if (condN != null) {
                        if ("$or".equals(key)) {
                            cond = cond0.or(condN);
                        } else {
                            cond = cond0.and(condN);
                        }
                    }
                }
                return cond;
            } else {
                JSONObject jsonObject = object.optJSONObject(key);
                if (jsonObject != null) {
                    Iterator<String> ops = jsonObject.keys();
                    if (ops.hasNext()) {
                        String op = ops.next();
                        Object value = jsonObject.opt(op);
                        cond = DroiCondition.cond(key, op, value);
                        return cond;
                    }
                } else {
                    String value = object.optString(key);
                    cond = DroiCondition.cond(key, "$eq", value);
                    return cond;
                }
            }
        }
        return null;
    }

    private DroiQuery.Builder parseOption(DroiQuery.Builder builder, String options) {
        if (TextUtils.isEmpty(options)) {
            return builder;
        }
        try {
            JSONObject object = new JSONObject(options);
            // offset
            int offset = object.optInt("offset", 0);
            builder = builder.offset(offset);
            // limit
            int limit = object.optInt("limit", 0);
            builder = builder.limit(limit);
            // order
            String orderString = object.optString("order");
            String[] orders = orderString.split(",");
            for (String orderName : orders) {
                if (orderName.startsWith("-")) {
                    builder = builder.orderBy(orderName.substring(1), false);
                } else {
                    builder = builder.orderBy(orderName, true);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return builder;
    }
}
