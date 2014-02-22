package com.health.caresnap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CareSnapMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_snap_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.care_snap_main, menu);
        return true;
    }
    
}
