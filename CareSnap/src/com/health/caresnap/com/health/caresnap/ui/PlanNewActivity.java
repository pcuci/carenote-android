package com.health.caresnap.com.health.caresnap.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.health.caresnap.CaptureSessionGlobal;
import com.health.caresnap.CaptureSessionGlobal.CaptureSessionState;
import com.health.caresnap.R;
import com.health.caresnap.com.health.caresnap.model.Physician;
import com.health.caresnap.com.health.caresnap.model.Plan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PlanNewActivity extends Activity {
    private final int REQUEST_CODE = 1;
    private TextView dateTimeTextView;
    private Timer timer;
    private String recordingText = "";
    private String TAG = "CREATE_IMPRESSION";
    private Spinner physiciansSpinner;
    private TextView hospitalTextView;
    private Button addNewPhysician;
    private Button addNewNoteButton;
    private Button saveImpressionButton;
    private CaptureSessionGlobal session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupUi();
        setupListeners();
    }

    private void setupUi() {
        setContentView(R.layout.activity_plan_new);
        physiciansSpinner = (Spinner) findViewById(R.id.physiciansSpinner);
        hospitalTextView = (TextView) findViewById(R.id.clinic_name);
        dateTimeTextView = (TextView) findViewById(R.id.date_time_textview);
        addNewNoteButton = (Button) findViewById(R.id.note_new_button);
        saveImpressionButton = (Button) findViewById(R.id.impression_new_save_button);
        addNewPhysician = (Button) findViewById(R.id.addNewPractitioner);
        physiciansSpinner.setPrompt("Select Physician");
        session = ((CaptureSessionGlobal) getApplicationContext());
        saveImpressionButton.setEnabled(false);
    }

    private void setupListeners() {
        addNewNoteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Log.d(TAG, "clicking Add Note Session state: " + session);

                if (session.getSessionState() == CaptureSessionState.STOPPED) {
                    recordingText = "";
                } else if (session.getSessionState() == CaptureSessionState.STARTING) {
                    Intent i = new Intent(getBaseContext(),
                            NoteNewActivity.class);
                    startActivityForResult(i, REQUEST_CODE);
                }
            }
        });

        saveImpressionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaptureSessionState state = session.getSessionState();
                if (state == CaptureSessionState.FINISHED_RECORDING) {
                    NothingSelectedSpinnerAdapter wrappedAdapter = (NothingSelectedSpinnerAdapter) physiciansSpinner.getAdapter();
                    int selectedPhysicianId = (int) physiciansSpinner.getSelectedItemId();
                    String hospitalName = hospitalTextView.getText().toString();
                   /* if (selectedPhysicianId == 0) {
                        Toast.makeText(PlanNewActivity.this.getBaseContext(), "Select a physician", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(hospitalName.equals("")){
                        Toast.makeText(PlanNewActivity.this.getBaseContext(), "Enter hospital name", Toast.LENGTH_SHORT).show();
                        return;
                    }*/
                    Physician physician = (Physician) wrappedAdapter.getAdapter().getItem(selectedPhysicianId);

                    Log.d(TAG, "CURRENT TIME: " + getCurrentTime());
                    Plan plan = new Plan(
                            physician,
                            hospitalName,
                            recordingText, getCurrentTime());
                    session.addPlan(plan);
                    clearFields();
                    session.setSessionState(CaptureSessionState.STARTING);

                    Toast.makeText(getBaseContext(), "Plan saved.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addNewPhysician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AddNewPhysicianDialog dialog = new AddNewPhysicianDialog(PlanNewActivity.this);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        long selectedPhysicianId = physiciansSpinner.getSelectedItemId() + 1;
                        populateDoctorsSpinner(session);
                        physiciansSpinner.setSelection((int) selectedPhysicianId);
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    protected void onStart() {
        dateTimeTextView.setText(getCurrentTimeString());
        timer = new Timer("CurrentTime");
        final Runnable updateTimeTask = new Runnable() {
            @Override
            public void run() {
                dateTimeTextView.setText(getCurrentTimeString());
            }
        };
        // update the UI
        Calendar calendar = Calendar.getInstance();
        int msec = 999 - calendar.get(Calendar.MILLISECOND);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(updateTimeTask);
            }
        }, msec, 1000);

        super.onStart();
    }

    @Override
    protected void onStop() {
        timer.cancel();
        timer.purge();
        timer = null;
        super.onStop();
    }

    private String getCurrentTimeString() {
        Time now = getCurrentTime();
        return now.format("%c");
    }

    private Time getCurrentTime() {
        Time now = new Time();
        now.setToNow();
        return now;
    }

    @Override
    protected void onResume() {

        Log.d(TAG, "onResume");

        CaptureSessionGlobal.CaptureSessionState sessionState = session
                .getSessionState();
        if (sessionState == CaptureSessionState.STOPPED) {
            updateSessionState(CaptureSessionState.STARTING);
            clearFields();
            physiciansSpinner.setSelection(0);
        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");
    }

    private void populateDoctorsSpinner(CaptureSessionGlobal session) {
        Physician[] physicians = getAllPhysicians(session);
        ArrayAdapter<Physician> dataAdapter = new PhysicianSpinAdapter(this.getBaseContext(), android.R.layout.simple_spinner_item, physicians);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        physiciansSpinner.setEmptyView(LayoutInflater.from(getBaseContext()).inflate(R.layout.select_physician_nothing_selected, (ViewGroup) physiciansSpinner.getParent(), false));
        physiciansSpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        dataAdapter,
                        R.layout.select_physician_nothing_selected,
                        R.layout.select_physician_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
    }

    private Physician[] getAllPhysicians(CaptureSessionGlobal session) {
        List<Physician> doctorsList = session.getAllPhysicians();
        return doctorsList.toArray(new Physician[doctorsList.size()]);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        startActivity(i);

        updateSessionState(CaptureSessionState.STOPPED);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                recordingText += data.getStringExtra(NoteNewActivity.NOTE_TEXT);
                saveImpressionButton.setEnabled(true);
            }
        }
    }

    private void updateSessionState(CaptureSessionState newState) {
        if (newState == null)
            return;
        session.setSessionState(newState);
    }

    private void clearFields() {
        populateDoctorsSpinner(session);
        hospitalTextView.setText("");
        saveImpressionButton.setEnabled(false);
    }


}
