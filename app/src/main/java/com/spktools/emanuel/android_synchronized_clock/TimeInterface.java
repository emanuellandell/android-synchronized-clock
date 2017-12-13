package com.spktools.emanuel.android_synchronized_clock;

/***
 *  TimeInterface
 *
 *  describes requirements for the objects
 */
interface TimeInterface {

    /***
     *  return unix timestamp
     */
    long getTime();

    /**
     * stub function used to format timestamp
     *
     * @param pattern
     * @param timestamp
     * @return
     */
    String format(String pattern, long timestamp);

    /**
     * stub function used to format timestamp in Swedish
     *
     * @param timestamp
     * @return
     */
    String formatSwedish(long timestamp);
}
