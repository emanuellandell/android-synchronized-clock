package com.spktools.emanuel.android_synchronized_clock;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // configurations that makes it possible to make the NTP requests through Internet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);

        // get form fields that we wan't to update
        final TextView timeField = findViewById(R.id.field_clock);
        final ImageView statusField = findViewById(R.id.field_online);

        // create an object that is responsible for updating the time
        final UpdateTime app = new UpdateTime();

        // pass on the textfield
        app.setView( timeField );

        Thread mainThread = new Thread() {
            @Override
            public void run() {
                while(!app.isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                    } catch( InterruptedException e) {
                        System.out.println("UpdateTime:run:Interupted");
                    }

                    // running it in the UI thread gives the posibility to update fields from
                    // other threads
                    runOnUiThread( app );

                    // micro bonus feature, display a green icon if we are connected to NTP
                    // TODO: this is not every efficient since we set the icon every iteration
                    //       observer pattern would make more sense.
                    if(app.isFallback()) {
                        statusField.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_away));
                    } else {
                        statusField.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_online));
                    }
                } // end while
            } // end run
        }; // end thread

        // launch our thread
        mainThread.start();
    } // end onCreate
}
