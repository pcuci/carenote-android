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

public class CaptureActivity extends Activity implements OnClickListener {

	protected static final int REQUEST_OK = 1;
	protected String impressionText;
	protected Button saveButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);
		findViewById(R.id.record_button).setOnClickListener(this);

		saveButton = (Button) findViewById(R.id.save_capture_button);
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				returnIntent.putExtra("impression", impressionText);
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
		Intent i = new Intent(getBaseContext(), CreateImpressionActivity.class);
		startActivity(i);
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

			CaptureSessionGlobal global = ((CaptureSessionGlobal) getApplicationContext());
			global.setRecording(impressionText);
		}
	}

	private void updateSessionState(CaptureSessionState newState) {
		CaptureSessionGlobal global = ((CaptureSessionGlobal) getApplicationContext());
		global.setSessionState(newState);
	}
}
