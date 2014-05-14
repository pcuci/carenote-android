package com.health.caresnap.com.health.caresnap.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.health.caresnap.R;
import com.health.caresnap.CaptureSessionGlobal;

public class CareSnapSplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private boolean isDbCreated = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (!isDbCreated) {
            dbSetup();
            isDbCreated = true;
        }
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(CareSnapSplashScreen.this, VisitShowActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void dbSetup() {
        CaptureSessionGlobal global = ((CaptureSessionGlobal) getApplicationContext());
        global.setupDatabase(this);
    }

}
