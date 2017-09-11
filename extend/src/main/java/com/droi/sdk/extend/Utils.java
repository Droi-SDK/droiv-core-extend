package com.droi.sdk.extend;

import com.droi.sdk.core.DroiObject;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by chenpei on 2017/9/11.
 */

public class Utils {
    public static JSONArray listToJSONArray(List<DroiObject> list) {
        JSONArray jsonArray = new JSONArray();
        for (DroiObject object : list) {
            jsonArray.put(object);
        }
        return jsonArray;
    }
}
