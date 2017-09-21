package com.akshaydeo.timelapse.runtime;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class contains all the logic of storing and maintaining lapse
 * A singleton class
 * Created by akshay.d on 21/09/17.
 */

public final class Stopwatch {
    private static final String TAG = "##stopwatch##";
    private static Stopwatch sInstance;
    private Server mServer;
    /**
     * This map stores all the entries of the timings
     * Key format is : class_name : method_name
     * Value is : long in microseconds
     */
    private ConcurrentHashMap<String, ArrayList<Entry>> mLaps = new ConcurrentHashMap<>();

    private Stopwatch() {
        mServer = new Server(9999);
        try {
            mServer.start();
        } catch (IOException e) {
            throw new IllegalStateException("This should not happen");
        }
    }

    static Stopwatch getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        sInstance = new Stopwatch();
        return sInstance;
    }

    void add(final Lap lap) {
        final Entry entry = new Entry();
        entry.setMethodName(lap.getMethodName());
        long diff = (lap.getStopTime() - lap.getStartTime());
        int iter = 0;
        do {
            diff = (diff / 1000);
            iter += 1;
            Log.d(TAG, "Diff " + diff + " iter " + iter);
        } while (diff > 9999);
        switch (iter) {
            case 0:
                Log.d(TAG, "Method " + lap.getMethodName() + " took " + diff + " nano seconds");
                entry.setUnit(TimeUnit.NS);
                break;
            case 1:
                Log.d(TAG, "Method " + lap.getMethodName() + " took " + diff + " micro seconds");
                entry.setUnit(TimeUnit.MCS);
                break;
            case 2:
                Log.d(TAG, "Method " + lap.getMethodName() + " took " + diff + " milli seconds");
                entry.setUnit(TimeUnit.MS);
                break;
            case 3:
                Log.d(TAG, "Method " + lap.getMethodName() + " took " + diff + " seconds");
                entry.setUnit(TimeUnit.S);
                break;
        }
        entry.setTime(diff);
        ArrayList<Entry> lapsForClass = null;
        if (mLaps.containsKey(lap.getClassName())) {
            lapsForClass = mLaps.get(lap.getClassName());
        }
        if (lapsForClass == null) {
            lapsForClass = new ArrayList<>();
        }
        lapsForClass.add(entry);
        mLaps.put(lap.getClassName(), lapsForClass);
    }

    public String getLapsHtml() {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<hr>");
        for (Map.Entry<String, ArrayList<Entry>> entry : mLaps.entrySet()) {
            htmlBuilder.append("<h4>Class : ").append(entry.getKey()).append("<table><tr><th>Method</th><th>Time</th><tr>");
            for (Entry e : entry.getValue()) {
                htmlBuilder.append("<tr><td>").append(e.getMethodName()).append("</td><td>").append(e.getTime()).append(" ").append(e.getUnit().getUnitName()).append("</td></tr>");
            }
            htmlBuilder.append("</table>");
        }
        return htmlBuilder.toString();
    }

    private static class Entry {
        private String methodName;
        private TimeUnit unit;
        private long time;

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public TimeUnit getUnit() {
            return unit;
        }

        public void setUnit(TimeUnit unit) {
            this.unit = unit;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public Entry() {
        }

        public Entry(final String methodName, final long time, final TimeUnit unit) {
            this.methodName = methodName;
            this.unit = unit;
            this.time = time;
        }
    }
}
