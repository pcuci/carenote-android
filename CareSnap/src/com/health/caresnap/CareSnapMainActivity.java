package com.health.caresnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class CareSnapMainActivity extends Activity {
	private boolean isDbCreated = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!isDbCreated) {
			dbSetup();
			isDbCreated = true;
		}
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
								ViewImpressionActivity.class);
						startActivity(i);
					}
				});
	}

	private void dbSetup() {
		CaptureSessionGlobal global = ((CaptureSessionGlobal) getApplicationContext());
		global.setupDatabase(this);
		/**
		 * CRUD Operations
		 * */
		// Inserting Contacts
//		Log.d("Insert: ", "Inserting ..");
//		global.addImpression(new Impression("Ravi Pundit", "Cardiologist",
//				"Montreal",
//				"Blood pressure normal. Do take more walks in the morning.", ""));
//		global.addImpression(new Impression("Maggie Odell", "Family Medicine",
//				"Up north",
//				"Good ECG, you need to see an endicrinologist, very soon!", ""));
//		global.addImpression(new Impression("Tommy Branco", "Endicrinologist",
//				"Quebec City", "LDL is low, you need to eat more fish.", ""));
//		global.addImpression(new Impression("Karthik Gidda", "Heart Surgeon",
//				"Toronto",
//				"Heart transplan surgery went well, practice mediation.", ""));

		// // Reading all contacts
		// Log.d("Reading: ", "Reading all contacts..");
		// List<Impression> impressionList = global.getAllImpressions();
		//
		// for (Impression impression : impressionList) {
		// String log = "Id: " + impression.getID() + " , Name: "
		// + impression.getName() + " , Specialty: "
		// + impression.getSpecialty() + " , Location: "
		// + impression.getSpecialty() + " , Note: "
		// + impression.getSpecialty();
		// // Writing Contacts to log
		// Log.d("Name: ", log);
		// }
	}
}