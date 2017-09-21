package com.akshaydeo.timelapse.runtime;

/**
 * Created by akshay.d on 21/09/17.
 */

public enum TimeUnit {
    NS("ns"),
    MCS("micros"),
    MS("mills"),
    S("sec");

    String mUnitName;

    TimeUnit(final String timeUnit) {
        mUnitName = timeUnit;
    }

    String getUnitName() {
        return mUnitName;
    }
}
