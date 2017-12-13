package com.spktools.emanuel.android_synchronized_clock;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);

        final UpdateTime app = new UpdateTime();

        // get fields
        final TextView timeField = (TextView)findViewById((int)R.id.field_clock);
        final ImageView statusField = (ImageView)findViewById((int)R.id.field_online);

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

                    System.out.println("MainActivity:onCreate:Before");
                    runOnUiThread(
                            app
                    );
                    System.out.println("MainActivity:onCreate:After");

                    // bonus feature, display a green icon if we are connected to NTP
                    if(app.isFallback()) {
                        statusField.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_away));
                    } else {
                        statusField.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_online));
                    }


                }
            }
        };


        mainThread.start();

        int i = 0;

        System.out.println("MainActivity:done");
    }
}
