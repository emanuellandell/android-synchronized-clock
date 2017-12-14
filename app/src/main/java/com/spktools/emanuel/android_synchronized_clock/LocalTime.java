package com.spktools.emanuel.android_synchronized_clock;

/***
 * device functions for device clock
 */
class LocalTime implements TimeInterface {

    @Override
    public long getTime() {
        return this.getDeviceTimestamp();
    }

    /***
     * return device local unix timestamp
     *
     * @return long
     */
    private long getDeviceTimestamp() {
        return (int) (System.currentTimeMillis() / 1000L);
    }
}
