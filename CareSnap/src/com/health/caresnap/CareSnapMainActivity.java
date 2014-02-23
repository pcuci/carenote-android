package com.health.caresnap;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class CareSnapMainActivity extends Activity implements OnClickListener {

	@Override
	public void onClick(View v) {
		Intent i = new Intent(getApplicationContext(), CaptureActivity.class);
		startActivity(i);
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_care_snap_main);
		findViewById(R.id.button_capture_main).setOnClickListener(this);
	}
}