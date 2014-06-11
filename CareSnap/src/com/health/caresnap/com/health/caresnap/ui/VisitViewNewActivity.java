package com.health.caresnap.com.health.caresnap.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
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
import com.health.caresnap.com.health.caresnap.model.Note;
import com.health.caresnap.com.health.caresnap.model.NoteType;
import com.health.caresnap.com.health.caresnap.model.Physician;
import com.health.caresnap.com.health.caresnap.model.Visit;

import java.util.*;

public class VisitViewNewActivity extends Activity implements ViewNewActivityCommunicator {
    private final int REQUEST_CODE = 1;
    private TextView dateTimeTextView;
    private Timer timer;
    private String TAG = "NEW_VISIT";
    private String impressionText;
    private Type activityType;
    public ViewNewFragmentCommunicator fragmentCommunicator;
    private CaptureSessionGlobal session;
    private NoteTypeListFragment bottomListFragment;

    @Override
    public List<Note> getNotes() {
        return bottomListFragment.getNotes();
    }

    public enum Type {
        VIEW, NEW;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate()");
        Bundle extras = getIntent().getExtras();


        activityType = Type.valueOf((String) extras.getString(IntentExtras.VISIT_TYPE.toString()));
        session = (CaptureSessionGlobal) getApplicationContext();
        setupUi();
        // setupListeners();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                createExitConfirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupUi() {
        setContentView(R.layout.activity_visit_view_new);
        dateTimeTextView = (TextView) findViewById(R.id.date_time_textview);

        FragmentManager fm = getFragmentManager();
        Fragment top_fragment = null;
        NoteTypeListFragment bottom_fragment = null;
        if (fm.findFragmentById(R.id.view_new_bottom_part) == null) {
            if (activityType == Type.NEW) {
                setTitle("New Visit");
                top_fragment = new NewVisitFragment();
                bottom_fragment = new NoteTypeListFragment();
                bottomListFragment = bottom_fragment;
                getActionBar().setDisplayHomeAsUpEnabled(true);
            } else if (activityType == Type.VIEW) {
                setTitle("View Visit");
                Visit visit = (Visit) getIntent().getSerializableExtra(ShowVisitsAdapter.SER_KEY);
                Physician physician = visit.getPhysician();
                String displayPhysician = visit.getPhysician().getFirstName() + " " + visit.getPhysician().getLastName() + " (" + physician.getSpeciality() + ")";

                top_fragment = new ViewVisitFragment(displayPhysician, visit.getLocation());
                List<Note> notes = new ArrayList<Note>();
                notes.add(visit.getImpressionNote());
                notes.add(visit.getResultsNote());
                notes.add(visit.getTestsNote());
                bottom_fragment = new NoteTypeListFragment(notes);
            }
            fm.beginTransaction().add(R.id.view_new_top_part, top_fragment).commit();
            fm.beginTransaction().add(R.id.view_new_bottom_part, bottom_fragment).commit();
        }

        session = ((CaptureSessionGlobal) getApplicationContext());

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

        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");
    }


    @Override
    public void onBackPressed() {
        createExitConfirmationDialog();
    }

    private void createExitConfirmationDialog() {
        if (activityType == Type.NEW) {
            new AlertDialog.Builder(this)
                    .setTitle("Cancel visit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            updateSessionState(CaptureSessionState.STOPPED);
                            NavUtils.navigateUpFromSameTask(VisitViewNewActivity.this);
                            //  VisitViewNewActivity.super.onBackPressed();
                        }
                    }).create().show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                bottomListFragment.updateNote((Note) data.getSerializableExtra(IntentExtras.NOTE.toString()));
            }
        }
    }


    private void updateSessionState(CaptureSessionState newState) {
        if (newState == null)
            return;
        session.setSessionState(newState);
    }
}
