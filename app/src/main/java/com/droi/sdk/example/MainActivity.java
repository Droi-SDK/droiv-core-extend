package com.droi.sdk.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.droi.sdk.extend.module.DroiWObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DroiWObject object = new DroiWObject();
//        JSONObject jsonObject = new JSONObject();
//        try {
//            JSONObject s = new JSONObject();
//            s.put("$starts","游记");
//            jsonObject.put("title",s);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        object.query("TestBlog",jsonObject.toString(),null);
//        JSONObject options = null;
//        try {
//            options = new JSONObject();
//            options.put("limit","1");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        object.query("TestBlog",jsonObject.toString(),options.toString());
//
//        try {
//            options.put("offset",0);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        object.query("TestBlog",jsonObject.toString(),options.toString());

        /*JSONObject options = new JSONObject();

        try {
            options = new JSONObject();
            options.put("count",true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject where = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();

            JSONObject s1 = new JSONObject();
            JSONObject c1 = new JSONObject();
            c1.put("$gt",1);
            s1.put("count",c1);
            jsonArray.put(s1);

            JSONObject s2 = new JSONObject();
            JSONObject c2 = new JSONObject();
            c2.put("$starts","游记");
            c2.put("$ends","x");
            s2.put("title",c2);
            jsonArray.put(s2);


            where.put("$and",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        object.query("TestBlog",where.toString(),options.toString());
        */
        JSONObject where = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();

            JSONObject s1 = new JSONObject();
            s1.put("author","潜水员");
            jsonArray.put(s1);
            JSONObject options = new JSONObject();
            options.put("order","count,-_CreationTime");
            object.query("TestBlog",null,options.toString());
            where.put("$and",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
