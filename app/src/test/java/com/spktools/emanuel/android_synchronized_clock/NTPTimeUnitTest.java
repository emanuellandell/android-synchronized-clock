package com.spktools.emanuel.android_synchronized_clock;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NTPTimeUnitTest {
    @Test
    public void getNTPTime() {
        NTPTime currentTime = new NTPTime();
        Long value = currentTime.getTime();
        assertEquals( value.getClass().toString(), "class java.lang.Long" );
    }

    @Test
    public void formatNTPTime() {
        NTPTime currentTime = new NTPTime();
        long dynamicTimestamp = currentTime.getTime();
        String formatedDate = currentTime.formatSwedish(dynamicTimestamp);
        //assertEquals( formatedDate, "2017-12-11 23:45:12");
    }
}