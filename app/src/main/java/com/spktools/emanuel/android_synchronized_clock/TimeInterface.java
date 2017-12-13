package com.spktools.emanuel.android_synchronized_clock;

import java.text.SimpleDateFormat;

/***
 *
 */
interface TimeInterface {

    /***
     *  return unix timestamp
     */
    long getTime();

    String format(String pattern, long timestamp);

    String formatSwedish(long timestamp);
}
