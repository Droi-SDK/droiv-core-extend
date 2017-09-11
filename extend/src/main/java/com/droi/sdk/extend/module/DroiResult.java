package com.droi.sdk.extend.module;

import com.droi.sdk.extend.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenpei on 2017/9/8.
 */

public class DroiResult {

    public int Code;

    public String StringResult;

    public JSONObject Result;

    public JSONArray ArrayResult;

    public int Count = -1;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("Code", Code);
        if (Count != -1) {
            map.put("Count", Count);
        }
        if (StringResult != null) {
            map.put("Result", StringResult);
        } else if (Result != null) {
            try {
                map.put("Result", Utils.toMap(Result));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (ArrayResult != null) {
            try {
                map.put("Result", Utils.toList(ArrayResult));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
