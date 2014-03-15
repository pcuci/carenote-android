package com.health.caresnap;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.health.caresnap.CaptureSessionGlobal.CaptureSessionState;
import com.health.caresnap.com.health.caresnap.model.Impression;

public class ImpressionNewActivity extends Activity {
    private String dateTime;
    private TextView dateTimeTextView;
    private Handler handler = new Handler();
    private String recordingText = "";
    private Thread timeThread;
    private String TAG = "CREATE_IMPRESSION";
    private Button captureSaveButton;
    private TextView nameTextView;
    private Spinner specialityTextView;
    private TextView hospitalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impression_new);
        captureSaveButton = (Button) findViewById(R.id.note_new_button);
        nameTextView = (TextView) findViewById(R.id.doctor_name);
        specialityTextView = (Spinner) findViewById(R.id.speciality_spinner);
        hospitalTextView = (TextView) findViewById(R.id.clinic_name);
        captureSaveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                CaptureSessionGlobal session = ((CaptureSessionGlobal) getApplicationContext());
                if (session.getSessionState() == CaptureSessionState.PAUSED) {
                    updateSessionState(CaptureSessionState.FINAL_SAVE);
                } else if (session.getSessionState() == CaptureSessionState.STOPPED) {
                    captureSaveButton.setText("Capture");
                } else if (session.getSessionState() == CaptureSessionState.STARTING) {

                    Intent i = new Intent(getBaseContext(),
                            NoteNewActivity.class);
                    startActivityForResult(i, 1);
                }
            }
        });
        // get action bar
        // ActionBar actionBar = getActionBar();
        //
        // // Enabling Up / Back navigation
        // actionBar.setDisplayHomeAsUpEnabled(true);
        dateTimeTextView = (TextView) findViewById(R.id.date_time_textview);
        timeThread = new Thread() {
            public void run() {
                Calendar c = Calendar.getInstance();
                Date date = c.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");

                dateTime = sdf.format(date);
                Log.d(TAG, "Impression-CreateActivity.onCreate(...).new Thread() {...}.run(" + dateTime + ")");
                handler.postDelayed(this, 1000);
            }
        };
        timeThread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        handler.removeCallbacks(timeThread);
        handler.postDelayed(timeThread, 0);
        Log.d(TAG, "onResume");

        CaptureSessionGlobal session = ((CaptureSessionGlobal) getApplicationContext());
        CaptureSessionGlobal.CaptureSessionState sessionState = session
                .getSessionState();
        if (sessionState == CaptureSessionState.STOPPED) {
            updateSessionState(CaptureSessionState.STARTING);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        handler.removeCallbacks(timeThread);
        Log.d(TAG, "onPause");
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        startActivity(i);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

            // TODO - What sets this RESULT_OK ?
            if (resultCode == RESULT_OK) {
                String recordingText = "";
                recordingText += data.getStringExtra("impression");

                // TODO - use the other button to save the impression
                Impression impression = new Impression(
                        String.valueOf(nameTextView.getText()),
                        String.valueOf(specialityTextView.getSelectedItem()),
                        String.valueOf(hospitalTextView.getText()),
                        recordingText, dateTime);

                CaptureSessionGlobal global = ((CaptureSessionGlobal) getApplicationContext());
                global.addImpression(impression);
            }
            if (resultCode == RESULT_CANCELED) {
            }
        }
    } // onActivityResult

    private void updateSessionState(CaptureSessionState newState) {
        CaptureSessionGlobal global = ((CaptureSessionGlobal) getApplicationContext());
        global.setSessionState(newState);
    }

}
