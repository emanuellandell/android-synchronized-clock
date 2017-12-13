package com.spktools.emanuel.android_synchronized_clock;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.spktools.emanuel.android_synchronized_clock.MainActivity;

import static org.junit.Assert.*;

/**
 * MainActivityTest
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.spktools.emanuel.android_synchronized_clock", appContext.getPackageName());
    }

    public void getDate() throws Exception {



        // Context of the app under test.
        //Context appContext = InstrumentationRegistry.getTargetContext();

        //assertEquals("com.spktools.emanuel.android_synchronized_clock", appContext.getPackageName());
    }
}
