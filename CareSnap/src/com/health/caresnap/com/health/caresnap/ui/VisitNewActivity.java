package com.health.caresnap.com.health.caresnap.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.health.caresnap.CaptureSessionGlobal;
import com.health.caresnap.CaptureSessionGlobal.CaptureSessionState;
import com.health.caresnap.R;
import com.health.caresnap.com.health.caresnap.model.Physician;
import com.health.caresnap.com.health.caresnap.model.Visit;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class VisitNewActivity extends Activity {
    private final int REQUEST_CODE = 1;
    private TextView dateTimeTextView;
    private Timer timer;
    private String recordingText = "";
    private String TAG = "CREATE_IMPRESSION";
    private Spinner physiciansSpinner;
    private TextView hospitalTextView;
    private Button addNewNoteButton;
    private Button saveImpressionButton;
    private CaptureSessionGlobal session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupUi();
        setupListeners();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                createExitConfirmationDialog();
                return true;
            case R.id.action_new_physician:
                startNewPhysicianDialog();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupUi() {
        setContentView(R.layout.activity_visit_new);
        physiciansSpinner = (Spinner) findViewById(R.id.physiciansSpinner);
        hospitalTextView = (TextView) findViewById(R.id.clinic_name);
        dateTimeTextView = (TextView) findViewById(R.id.date_time_textview);
        addNewNoteButton = (Button) findViewById(R.id.note_new_button);
        saveImpressionButton = (Button) findViewById(R.id.impression_new_save_button);
        physiciansSpinner.setPrompt("Select Physician");
        session = ((CaptureSessionGlobal) getApplicationContext());
        saveImpressionButton.setEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupListeners() {
        addNewNoteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Log.d(TAG, "clicking Add Note Session state: " + session);

                if (session.getSessionState() == CaptureSessionState.STOPPED) {
                    recordingText = "";
                } else if (session.getSessionState() == CaptureSessionState.STARTING_NEW_VISIT) {
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

                    Physician physician = null;
                    try {
                        physician = (Physician) wrappedAdapter.getAdapter().getItem(selectedPhysicianId);
                    } catch (Exception e) {

                        Toast.makeText(getBaseContext(), "Select a physician.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (hospitalName == null || hospitalName.equals("")) {
                        Toast.makeText(getBaseContext(), "Enter clinic name.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (recordingText == null || recordingText.equals("")) {
                        Toast.makeText(getBaseContext(), "Recording is empty.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Visit visit = new Visit(
                            physician,
                            hospitalName,
                            recordingText, getCurrentTime());
                    session.addPlan(visit);
                    clearFields();
                    session.setSessionState(CaptureSessionState.STARTING_NEW_VISIT);

                    Toast.makeText(getBaseContext(), "Visit saved.", Toast.LENGTH_SHORT).show();
                }
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
            updateSessionState(CaptureSessionState.STARTING_NEW_VISIT);
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
        createExitConfirmationDialog();
    }

    private void createExitConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Cancel visit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        updateSessionState(CaptureSessionState.STOPPED);
                        NavUtils.navigateUpFromSameTask(VisitNewActivity.this);
                        //  VisitNewActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                recordingText += data.getStringExtra(NoteNewActivity.NOTE_TEXT);
                saveImpressionButton.setEnabled(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_visit, menu);
        return super.onCreateOptionsMenu(menu);
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


    private void startNewPhysicianDialog() {
        final AddNewPhysicianDialog dialog = new AddNewPhysicianDialog(VisitNewActivity.this);
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
}
