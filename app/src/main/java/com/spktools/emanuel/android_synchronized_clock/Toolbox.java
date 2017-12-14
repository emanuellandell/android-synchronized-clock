package com.spktools.emanuel.android_synchronized_clock;

import java.text.SimpleDateFormat;

/***
 * Toolbox
 *
 * provides helpful functions for working with time
 */
abstract class Toolbox implements TimeInterface {

    /***
     * format timestamp in microseconds to swedish format
     *
     * @param timestamp
     * @return String
     */
    public static String formatSwedish(long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp*1000L);
    }

    /***
     * format timestamp to clock format so that we se it in hours and minutes
     *
     * @param timestamp
     * @return String
     */
    public static String formatMilitaryTime(long timestamp) {
        return new SimpleDateFormat("HH:mm:ss").format(timestamp*1000L);

    }
}
