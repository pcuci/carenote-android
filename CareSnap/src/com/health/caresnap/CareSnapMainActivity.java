package com.health.caresnap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CareSnapMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_care_snap_main);
		findViewById(R.id.button_capture_main).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {

						Intent i = new Intent(getBaseContext(),
								CreateImpressionActivity.class);
						startActivity(i);
					}
				});
		findViewById(R.id.button_view_main).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {

						Intent i = new Intent(getBaseContext(),
								CaptureActivity.class);
						startActivity(i);
					}
				});
	}
}