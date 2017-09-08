package com.droi.sdk.extend.module;

import android.text.TextUtils;
import android.util.Log;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.taobao.weex.annotation.JSMethod;
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
    public void query(String tableName, String whereClause, String options) {
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
            if (options != null) {
                optionJSONObject = new JSONObject(options);
                isCount = optionJSONObject.optBoolean("count", false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (isCount) {
            query.countInBackground(new DroiCallback<Integer>() {
                @Override
                public void result(Integer integer, DroiError droiError) {
                    if (droiError.isOk()) {
                        // 成功！
                        Log.i("chenpei", "size:" + integer);
                    } else {
                        Log.e("chenpei", "失败" + droiError.toString());
                    }
                }
            });
        } else {
            query.runQueryInBackground(new DroiQueryCallback<DroiObject>() {
                @Override
                public void result(List<DroiObject> list, DroiError droiError) {
                    if (droiError.isOk()) {
                        // 成功！
                        Log.i("chenpei", list.toString());
                    } else {
                        Log.e("chenpei", "失败" + droiError.toString());
                    }
                }
            });
        }
        // TODO: 2017/9/8 改为同步方法
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
            String orderString = object.getString("order");
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
