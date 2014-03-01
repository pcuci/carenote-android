package com.health.caresnap;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class CareSnapMainActivity extends Activity {

	private DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbSetup();
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

	private void dbSetup() {
		db = new DatabaseHandler(this);
		/**
		 * CRUD Operations
		 * */
		// Inserting Contacts
		Log.d("Insert: ", "Inserting ..");
		db.addContact(new Impression("Ravi Pundit", "Cardiologist", "Montreal", "Blood pressure normal. Do take more walks in the morning."));
		db.addContact(new Impression("Maggie Odell", "Family Medicine", "Up north", "Good ECG, you need to see an endicrinologist, very soon!"));
		db.addContact(new Impression("Tommy Branco", "Endicrinologist", "Quebec City", "LDL is low, you need to eat more fish."));
		db.addContact(new Impression("Karthik Gidda", "Heart Surgeon", "Toronto", "Heart transplan surgery went well, practice mediation."));

		// Reading all contacts
		Log.d("Reading: ", "Reading all contacts..");
		List<Impression> impressionList = db.getAllImpressions();

		for (Impression impression : impressionList) {
			String log = "Id: " + impression.getID() + " , Name: " + impression.getName()
					+ " , Specialty: " + impression.getSpecialty()
					+ " , Location: " + impression.getSpecialty()
					+ " , Note: " + impression.getSpecialty();
			// Writing Contacts to log
			Log.d("Name: ", log);
		}
	}
}