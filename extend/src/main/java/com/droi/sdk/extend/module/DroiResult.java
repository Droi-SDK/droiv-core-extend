package com.droi.sdk.extend.module;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenpei on 2017/9/8.
 */

public class DroiResult extends JSONObject {

    public int Code;

    public JSONObject Result;

    public JSONArray ArrayResult;

    public int Count = -1;

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        try {
            object.put("Code", Code);
            if (Count != -1) {
                object.put("Count", Count);
            }
            if (Result != null) {
                object.put("Result", Result);
            } else if (ArrayResult != null) {
                object.put("Result", ArrayResult);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
