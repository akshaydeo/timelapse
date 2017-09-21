package com.akshaydeo.timelapse.runtime;

import android.util.Log;

import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * This class contains the pointcut related information
 * Author : Akshay Deo
 * Date   : 16/01/17 4:57 PM
 * Email  : akshay.d@media.net
 */

@Aspect
public class Timelapse {
    private static final String TAG = "##Timelapse##";

    @Around("within(@com.akshaydeo.timelapse.Measure *)")
    public Object NewLap(final ProceedingJoinPoint joinPoint)
            throws Throwable {
        if (joinPoint.getSignature().getName().contains("<")) {
            return joinPoint.proceed();
        }
        Log.d(TAG, "Lap started for " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        final Lap lap = new Lap(joinPoint.getSignature().getDeclaringType().getCanonicalName(), joinPoint.getSignature().getName());
        lap.start();
        try {
            return joinPoint.proceed();
        } catch (Exception ex) {
            lap.failed(ex);
            throw ex;
        } finally {
            lap.stop();
            Stopwatch.getInstance().add(lap);
        }
    }
}
