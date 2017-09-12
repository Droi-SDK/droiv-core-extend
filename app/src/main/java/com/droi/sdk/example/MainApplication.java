package com.droi.sdk.example;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.droi.sdk.core.Core;
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.example.adapter.ImageAdapter;
import com.droi.sdk.extend.module.DroiWCloud;
import com.droi.sdk.extend.module.DroiWCloudCache;
import com.droi.sdk.extend.module.DroiWFile;
import com.droi.sdk.extend.module.DroiWObject;
import com.droi.sdk.extend.module.DroiWUser;
import com.droi.sdk.extend.module.NativeLog;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.common.WXException;

/**
 * Created by chenpei on 2017/9/8.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Core.initialize(this);
        DroiUser droiUser = DroiUser.getCurrentUser();
        Log.i("chenpei", droiUser.toString());
        WXSDKEngine.initialize(this,
                new InitConfig.Builder().setImgAdapter(new ImageAdapter()
        ).build());
        try {
            WXSDKEngine.registerModule("droiobject", DroiWObject.class);
            WXSDKEngine.registerModule("droiuser", DroiWUser.class);
            WXSDKEngine.registerModule("droifile", DroiWFile.class);
            WXSDKEngine.registerModule("droicloud", DroiWCloud.class);
            WXSDKEngine.registerModule("droicloudcache", DroiWCloudCache.class);
            WXSDKEngine.registerModule("nativelog", NativeLog.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                // The demo code of calling 'notifyTrimMemory()'
                if (false) {
                    // We assume that the application is on an idle time.
                    WXSDKManager.getInstance().notifyTrimMemory();
                }
                // The demo code of calling 'notifySerializeCodeCache()'
                if (false) {
                    WXSDKManager.getInstance().notifySerializeCodeCache();
                }
            }
        });

    }
}
