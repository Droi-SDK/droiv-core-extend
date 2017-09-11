package com.droi.sdk.extend.module;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenpei on 2017/9/8.
 */

public class DroiResult extends JSONObject {

    public int Code;

    public JSONObject Result;

    public JSONArray ArrayResult;

    public int Count = -1;

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Code", Code);
            if (Count != -1) {
                jsonObject.put("Count", Count);
            }
            if (Result != null) {
                jsonObject.put("Result", Result);
            } else if (ArrayResult != null) {
                jsonObject.put("Result", ArrayResult);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("Code", Code);
        if (Count != -1) {
            map.put("Count", Count);
        }
        if (Result != null) {
            map.put("Result", Result.toString());
        } else if (ArrayResult != null) {
            map.put("Result", ArrayResult.toString());
        }
        return map;
    }
}
