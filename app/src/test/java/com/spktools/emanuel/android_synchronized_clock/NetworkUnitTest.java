package com.spktools.emanuel.android_synchronized_clock;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NetworkUnitTest {
    @Test
    public void testPing() {
        assertTrue(Network.ping("127.0.0.1"));
        assertFalse(Network.ping("asdasddssd"));
    }
}