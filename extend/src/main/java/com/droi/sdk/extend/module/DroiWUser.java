package com.droi.sdk.extend.module;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

/**
 * Created by chenpei on 2017/9/8.
 */

public class DroiWUser extends WXModule {

    public void login(String username, String password, JSCallback jsCallback) {
        DroiError droiError = new DroiError();
        DroiUser user = DroiUser.login(username, password, droiError);
        if (droiError.isOk()) {
            jsCallback.invoke(user.toString());
        } else {
            
        }
    }
}
