package com.spktools.emanuel.android_synchronized_clock;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DateTimeUnitTest {
    @Test
    public void formatLocalTime() {
        LocalTime currentTime = new LocalTime();
        long staticTimestamp = (long)1513032312;
        String formatedDate = currentTime.formatSwedish(staticTimestamp);
        assertEquals( formatedDate, "2017-12-11 23:45:12");
    }

    @Test
    public void getLocalTime() {
        LocalTime currentTime = new LocalTime();
        Long value = currentTime.getTime();
        assertEquals( value.getClass().toString(), "class java.lang.Long" );
    }
}