package com.health.caresnap.com.health.caresnap.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.health.caresnap.CaptureSessionGlobal;
import com.health.caresnap.CaptureSessionGlobal.CaptureSessionState;
import com.health.caresnap.R;
import com.health.caresnap.com.health.caresnap.model.Note;

public class NoteNewActivity extends Activity implements OnClickListener {

    protected static final int REQUEST_OK = 1;
    protected String noteText;
    private TextView noteTypeTV;
    private TextView text;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_new);
        findViewById(R.id.new_note_record_button).setOnClickListener(this);
        noteTypeTV = (TextView) findViewById(R.id.new_note_note_type);

        text = ((TextView) findViewById(R.id.summary_text));
        Bundle extras = getIntent().getExtras();
        note = (Note) extras.get(IntentExtras.NOTE.toString());
        noteTypeTV.setText(note.getType().toString());
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        try {
            startActivityForResult(i, REQUEST_OK);
            updateSessionState(CaptureSessionState.RECORDING);
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing speech to physician_first_name engine.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_note, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        updateSessionState(CaptureSessionState.STOPPED);
        Toast.makeText(getBaseContext(), "Note cancelled.", Toast.LENGTH_SHORT).show();

        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OK && resultCode == RESULT_OK) {

            ArrayList<String> thingsYouSaid = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            noteText = thingsYouSaid.get(0);
            text.setText(noteText);
            text.setTextSize(24);
            note.setText(noteText);
            updateSessionState(CaptureSessionState.PAUSED);
            Toast.makeText(getBaseContext(), "Note created, press Save Visit to store it.", 2000).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                updateSessionState(CaptureSessionState.STOPPED);
                Toast.makeText(getBaseContext(), "Note cancelled.", Toast.LENGTH_SHORT).show();
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.save_capture_button:
                saveNote();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(IntentExtras.NOTE.toString(), note);
        setResult(RESULT_OK, returnIntent);
        updateSessionState(CaptureSessionState.FINISHED_RECORDING);
        finish();
    }

    private void updateSessionState(CaptureSessionState newState) {
        CaptureSessionGlobal global = ((CaptureSessionGlobal) getApplicationContext());
        global.setSessionState(newState);
    }
}
