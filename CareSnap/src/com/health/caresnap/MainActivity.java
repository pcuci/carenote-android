package com.health.caresnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.health.caresnap.com.health.caresnap.model.Impression;

import java.util.List;

public class MainActivity extends Activity {
    private boolean isDbCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isDbCreated) {
            dbSetup();
            isDbCreated = true;
        }
        setContentView(R.layout.activity_main);
        findViewById(R.id.impression_new_button).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        Intent i = new Intent(getBaseContext(),
                                ImpressionNewActivity.class);
                        startActivity(i);
                    }
                }
        );
        findViewById(R.id.impression_index_button).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        Intent i = new Intent(getBaseContext(),
                                ImpressionShowActivity.class);
                        startActivity(i);
                    }
                }
        );
    }

    private void dbSetup() {
        CaptureSessionGlobal global = ((CaptureSessionGlobal) getApplicationContext());
        global.setupDatabase(this);
        // insertDummyData(global);
    }

    private void insertDummyData(CaptureSessionGlobal global) {
        // Inserting Impressions
        Log.d("Insert: ", "Inserting ..");
        global.addImpression(new Impression("Ravi Pundit", "Cardiologist",
                "Montreal",
                "Blood pressure normal. Do take more walks in the morning. Please see me next month.", ""));
        global.addImpression(new Impression("Maggie Odell", "Family Medicine",
                "Up north",
                "Good electrocardiogram, you need to see an endocrinologist for complications.", ""));
        global.addImpression(new Impression("Tommy Bran", "Endocrinologist",
                "Quebec City", "Good cholesterol, it is low, but do your best to eat more fish.", ""));
        global.addImpression(new Impression("Samuel Torrents", "Heart Surgeon",
                "Toronto",
                "Heart transplant surgery went well, practice mediation.", ""));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Impression> impressionList = global.getAllImpressions();

        for (Impression impression : impressionList) {
            String log = "Id: " + impression.getID() + " , Name: "
                    + impression.getName() + " , Specialty: "
                    + impression.getSpecialty() + " , Location: "
                    + impression.getSpecialty() + " , Note: "
                    + impression.getSpecialty();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
    }
}