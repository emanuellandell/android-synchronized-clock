package com.spktools.emanuel.android_synchronized_clock;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

/***
 * TimeController
 *
 * provides helpful functions for working with time
 */
abstract class TimeController implements TimeInterface {

    /**
     * @param pattern
     * @param timestamp
     * @return String
     */
    @SuppressLint("SimpleDateFormat")
    public String format(String pattern, long timestamp) {
        return new SimpleDateFormat(pattern).format(timestamp);
    }

    /***
     * format timestamp in microseconds to swedish format
     *
     * @param timestamp
     * @return String
     */
    public String formatSwedish(long timestamp) {
        return this.format("yyyy-MM-dd HH:mm:ss", timestamp*1000L);
    }

    /***
     *
     * @param timestamp
     * @return String
     */
    public String formatMilitaryTime(long timestamp) {
        return this.format("HH:mm:ss", timestamp*1000L);
    }
}
