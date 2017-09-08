package com.droi.sdk.example;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.droi.sdk.extend.module.DroiWObject;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.common.WXPerformance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity implements IWXRenderListener {

    WXSDKInstance mWXSDKInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.registerRenderListener(this);
        mWXSDKInstance.render(getApplication().getPackageName(),
                WXFileUtils.loadAsset("index.js", this), null, null, WXRenderStrategy.APPEND_ASYNC);
        //mWXSDKInstance.render(WXFileUtils.loadAsset("index.js", this));
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        setContentView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {

    }

    public void onBackPressed() {
        Log.e("USER ACTION", "BACK");
        WXSDKManager.getInstance().fireEvent(mWXSDKInstance.getInstanceId(), "_root", "androidback");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }
    }
}


//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        DroiWObject object = new DroiWObject();
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
//        JSONObject where = new JSONObject();
//        try {
//            JSONArray jsonArray = new JSONArray();
//
//            JSONObject s1 = new JSONObject();
//            s1.put("author","潜水员");
//            jsonArray.put(s1);
//            JSONObject options = new JSONObject();
//            options.put("order","count,-_CreationTime");
//            object.query("TestBlog",null,options.toString());
//            where.put("$and",jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
