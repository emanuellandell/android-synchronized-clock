package com.spktools.emanuel.android_synchronized_clock;

import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/***
 * MainActivity
 *
 * this is the entry point for our code
 */
public class MainActivity extends AppCompatActivity {

    private boolean mPaused = false;
    private Object mPauseLock = null;
    private Thread mMainThread = null;
    private UpdateTime mApp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPauseLock = new Object();

        // configurations that makes it possible to make the NTP requests through Internet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);

        // get form fields that we wan't to update
        final TextView timeField = findViewById(R.id.field_clock);
        final ImageView statusField = findViewById(R.id.field_online);

        Drawable iconOn     = getResources().getDrawable(android.R.drawable.presence_online);
        Drawable iconOff    = getResources().getDrawable(android.R.drawable.presence_away);

        // create an object that is responsible for updating the time
        mApp = new UpdateTime( timeField );

        // pass on the textfield
        mApp.setStatusImage( statusField, iconOn, iconOff );

        mMainThread = new Thread() {
            @Override
            public void run() {
                while(!mApp.isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                    } catch( InterruptedException e) {
                        System.out.println("UpdateTime:run:Interupted");
                    }

                    synchronized (mPauseLock) {
                        while (mPaused) {
                            try {
                                mPauseLock.wait();
                            } catch (InterruptedException e) {
                            }
                        }

                        // running it in the UI thread gives the posibility to update fields from
                        // other threads
                        runOnUiThread( mApp );

                    } // end synchronize
                } // end while
            } // end run
        }; // end thread

        // launch our thread
        mMainThread.start();
    } // end onCreate


    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("MainActivity:onResume");

        synchronized (mPauseLock) {
            mPaused = false;
            mPauseLock.notifyAll();
            mApp.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("MainActivity:onPause");

        synchronized (mPauseLock) {
            mPaused = true;
            mApp.onPause();
        }
    }
}
