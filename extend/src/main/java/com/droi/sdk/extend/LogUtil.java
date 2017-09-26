package com.droi.sdk.extend;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by chenpei on 2017/9/26.
 */

public class LogUtil {

    private static final String CORE_EXTEND_TAG = "CoreExtend";

    public static void v(String msg) {
        if (isLevelPrint(Log.VERBOSE)) {
            Log.v(CORE_EXTEND_TAG, msg);
        }
    }

    public static void d(String msg) {
        if (isLevelPrint(Log.DEBUG)) {
            Log.d(CORE_EXTEND_TAG, msg);
        }
    }

    public static void i(String msg) {
        if (isLevelPrint(Log.INFO)) {
            Log.i(CORE_EXTEND_TAG, msg);
        }
    }

    public static void w(String msg) {
        if (isLevelPrint(Log.WARN)) {
            Log.w(CORE_EXTEND_TAG, msg);
        }
    }

    public static void e(String msg) {
        if (isLevelPrint(Log.ERROR)) {
            Log.e(CORE_EXTEND_TAG, msg);
        }
    }

    public static void e(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        e(errors.toString());
    }

    public static void w(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        w(errors.toString());
    }

    public static void d(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        d(errors.toString());
    }

    private static boolean isLevelPrint(int level) {
        int threshold = Log.WARN;
        if (BuildConfig.DEBUG) {
            threshold = Log.VERBOSE;
        }
        return (threshold <= level);
    }
}
