package com.akshaydeo.timelapse.runtime;

import android.util.Log;

/**
 * Created by akshay.d on 21/09/17.
 */

public class Lap {
    private static final String TAG = "##Lap##";
    private String mClassName;
    private String mMethodName;
    private Exception mException;
    private long mStartTime;
    private long mStopTime;

    Lap(final String className, final String methodName) {
        mClassName = className;
        mMethodName = methodName;
    }

    public String getClassName() {
        return mClassName;
    }

    public String getMethodName() {
        return mMethodName;
    }

    public Exception getException() {
        return mException;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public long getStopTime() {
        return mStopTime;
    }

    void failed(final Exception exception) {
        mException = exception;
    }

    void start() {
        mStartTime = System.nanoTime();
    }

    void stop() {
        mStopTime = System.nanoTime();
    }
}