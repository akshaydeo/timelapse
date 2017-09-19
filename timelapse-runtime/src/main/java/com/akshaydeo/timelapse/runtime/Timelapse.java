package com.akshaydeo.timelapse.runtime;

import android.util.Log;

import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Main class containing the timelapse related functionality
 * Author : Akshay Deo
 * Date   : 16/01/17 4:57 PM
 * Email  : akshay.d@media.net
 */

@Aspect
public class Timelapse {
    private static final String TAG = "##Timelapse##";
    /**
     * This map stores all the entries of the timings
     * Key format is : class_name : method_name
     * Value is : long in microseconds
     */
    private ConcurrentHashMap<String, Long> mTimings = new ConcurrentHashMap<>();

    @After("call(* *init(..))")
    public void AfterInit(final JoinPoint point) {
        Log.d(TAG, "after init");
    }

    @Around("within(@com.akshaydeo.timelapse.Measure *)")
    public Object NewLap(final ProceedingJoinPoint joinPoint)
            throws Throwable {
        Log.d(TAG, "Lap started for " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        final Lap lap = new Lap(joinPoint.getSignature().getName());
        lap.start();
        try {
            return joinPoint.proceed();
        } catch (Exception ex) {
            lap.failed(ex);
        } finally {
            lap.stop();
            lap.dumpToFile();
        }
        return null;
    }

    private static class Lap {
        private static final String TAG = "##Lap##";
        private String mMethodName;
        private Exception mException;
        private long mStartTime;
        private long mStopTime;

        Lap(final String methodName) {
            mMethodName = methodName;
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

        void dumpToFile() {
            long diff = (mStopTime - mStartTime);
            int iter = 0;
            do {
                diff = (diff / 1000);
                iter += 1;
                Log.d(TAG,"Diff " + diff + " iter " + iter);
            } while (diff > 9999);
            switch (iter){
                case 0:
                    Log.d(TAG, "Method " + mMethodName + " took " + diff + " nano seconds");
                    break;
                case 1:
                    Log.d(TAG, "Method " + mMethodName + " took " + diff + " micro seconds");
                    break;
                case 2:
                    Log.d(TAG, "Method " + mMethodName + " took " + diff + " milli seconds");
                    break;
                case 3:
                    Log.d(TAG, "Method " + mMethodName + " took " + diff + " seconds");
                    break;
            }

        }
    }
}
