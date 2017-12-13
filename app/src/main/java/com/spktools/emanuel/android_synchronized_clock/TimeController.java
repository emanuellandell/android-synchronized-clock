package com.spktools.emanuel.android_synchronized_clock;

import java.text.SimpleDateFormat;

/**
 * Created by hemverket on 2017-12-12.
 */

abstract class TimeController implements TimeInterface {

    public String format(String pattern, long timestamp) {
        return new SimpleDateFormat(pattern).format(timestamp);
    }

    /***
     * format timestamp in microseconds to swedish format
     *
     * @param timestamp
     * @return
     */
    public String formatSwedish(long timestamp) {
        return this.format("yyyy-MM-dd HH:mm:ss", timestamp*1000L);
    }

    /***
     *
     * @param timestamp
     * @return
     */
    public String formatMilitaryTime(long timestamp) {
        return this.format("HH:mm:ss", timestamp*1000L);
    }
}
