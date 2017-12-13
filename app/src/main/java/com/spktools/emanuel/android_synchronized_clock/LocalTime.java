package com.spktools.emanuel.android_synchronized_clock;

/***
 *
 */
class LocalTime extends TimeController {

    @Override
    public long getTime() {
        return this.getDeviceTimestamp();
    }

    /***
     * return device local unix timestamp
     *
     * @return
     */
    public long getDeviceTimestamp() {
        return (int) (System.currentTimeMillis() / 1000L);
    }
}
