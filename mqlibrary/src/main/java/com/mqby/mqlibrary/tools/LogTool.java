package com.mqby.mqlibrary.tools;

import android.util.Log;

/**
 * @author MaQiang
 * @time 2018/6/9 0009 20:37
 * @QQ 1033785970
 * @class 日志工具类
 */
public class LogTool {
    private static final String TAG = "LogTool";
    //类名
    private static String className;
    //方法名
    private static String methodName;
    //行数
    private static int lineNumber;
    private static boolean isDebug = true;

    public static void isDebug(boolean debug) {
        isDebug = debug;
    }

    private LogTool() {
        /* Protect from instantiations */
    }


    private static String createLog(String log) {
        return methodName + "(" + className + ":" + lineNumber + ") = " + log;
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message) {
        if (!isDebug) {
            return;
        }
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }


    public static void i(String message) {
        if (!isDebug) {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void d(String message) {
        if (!isDebug) {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void v(String message) {
        if (!isDebug) {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }

    public static void w(String message) {
        if (!isDebug) {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

    public static void wtf(String message) {
        if (!isDebug) {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(className, createLog(message));
    }
}
