package com.health.caresnap.com.health.caresnap.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.health.caresnap.CaptureSessionGlobal;
import com.health.caresnap.R;
import com.health.caresnap.com.health.caresnap.model.Plan;

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
                                PlanNewActivity.class);
                        startActivity(i);
                    }
                }
        );
        findViewById(R.id.impression_index_button).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        Intent i = new Intent(getBaseContext(),
                                PlanShowActivity.class);
                        startActivity(i);
                    }
                }
        );
    }

    private void dbSetup() {
        CaptureSessionGlobal global = ((CaptureSessionGlobal) getApplicationContext());
        global.setupDatabase(this);
    }

    private void insertDummyData(CaptureSessionGlobal global) {
        // Inserting Impressions
        /*
        Log.d("Insert: ", "Inserting ..");
        global.addPlan(new Plan("Ravi Pundit", "Cardiologist",
                "Montreal",
                "Blood pressure normal. Do take more walks in the morning. Please see me next month.", ""));
        global.addPlan(new Plan("Maggie Odell", "Family Medicine",
                "Up north",
                "Good electrocardiogram, you need to see an endocrinologist for complications.", ""));
        global.addPlan(new Plan("Tommy Bran", "Endocrinologist",
                "Quebec City", "Good cholesterol, it is low, but do your best to eat more fish.", ""));
        global.addPlan(new Plan("Samuel Torrents", "Heart Surgeon",
                "Toronto",
                "Heart transplant surgery went well, practice mediation.", ""));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Plan> planList = global.getAllPlans();

        for (Plan plan : planList) {
            String log = "Id: " + plan.getID() + " , Name: "
                    + plan.getName() + " , Specialty: "
                    + plan.getSpecialty() + " , Location: "
                    + plan.getSpecialty() + " , Note: "
                    + plan.getSpecialty();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
        */
    }
}