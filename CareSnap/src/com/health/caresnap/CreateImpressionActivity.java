package com.health.caresnap;

import java.text.DateFormat;
import java.util.Date;

import com.health.caresnap.CaptureSessionGlobal.CaptureSessionState;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class CreateImpressionActivity extends Activity {
	private String dateTime;
	private TextView dateTimeTextView;
	private Handler handler = new Handler();

	private Thread timeThread;
	private String TAG = "CREATE_IMPRESSION";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_impression);

		findViewById(R.id.capture_create_impr).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {

						Intent i = new Intent(getBaseContext(),
								CaptureActivity.class);
						startActivity(i);
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

				dateTime = DateFormat.getDateTimeInstance().format(new Date());
				dateTimeTextView.setText("Now is: " + dateTime);
				Log.d(TAG, "local Thread sleeping");
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

		if (session.getSessionState() == CaptureSessionState.STOPED) {
			updateSessionState(CaptureSessionState.STARTING);

		} else if (session.getSessionState() == CaptureSessionState.FINISHED_RECORDING) {
			updateSessionState(CaptureSessionState.FINAL_SAVE);
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
	}

	private void updateSessionState(CaptureSessionState newState) {
		CaptureSessionGlobal global = ((CaptureSessionGlobal) getApplicationContext());
		global.setSessionState(newState);
	}
}
