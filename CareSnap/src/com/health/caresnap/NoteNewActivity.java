package com.health.caresnap;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.health.caresnap.CaptureSessionGlobal.CaptureSessionState;

public class NoteNewActivity extends Activity implements OnClickListener {

	protected static final int REQUEST_OK = 1;
	protected String impressionText;
	protected Button saveButton;
    public static final String NOTE_TEXT= "NOTE_TEXT";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_new);
		findViewById(R.id.record_button).setOnClickListener(this);

		saveButton = (Button) findViewById(R.id.save_capture_button);
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				returnIntent.putExtra(NOTE_TEXT, impressionText);
				setResult(RESULT_OK, returnIntent);
				updateSessionState(CaptureSessionState.FINISHED_RECORDING);
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
		try {
			startActivityForResult(i, REQUEST_OK);
			updateSessionState(CaptureSessionState.RECORDING);
		} catch (Exception e) {
			Toast.makeText(this, "Error initializing speech to text engine.",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onBackPressed() {
        updateSessionState(CaptureSessionState.STOPPED);
        Toast.makeText(getBaseContext(), "Note cancelled by user.",2000).show();

        setResult(RESULT_CANCELED);
        finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_OK && resultCode == RESULT_OK) {

			ArrayList<String> thingsYouSaid = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			impressionText = thingsYouSaid.get(0);
			TextView view = ((TextView) findViewById(R.id.summary_text));
			view.setText(impressionText);
			view.setTextSize(24);
			updateSessionState(CaptureSessionState.PAUSED);
            Toast.makeText(getBaseContext(), "Note created, press Save Impression to store it.",2000).show();
		}
	}

	private void updateSessionState(CaptureSessionState newState) {
		CaptureSessionGlobal global = ((CaptureSessionGlobal) getApplicationContext());
		global.setSessionState(newState);
	}
}
