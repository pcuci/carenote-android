package com.health.caresnap;

import java.util.List;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.health.caresnap.com.health.caresnap.model.Physician;
import com.health.caresnap.com.health.caresnap.model.Visit;
import com.health.caresnap.com.health.caresnap.persistance.DatabaseHandler;

public class CaptureSessionGlobal extends Application {
    private String TAG = "SESSION_STATE";
    private DatabaseHandler databaseHandler;

    public enum CaptureSessionState {
        STARTING_NEW_VISIT,
        PAUSED,
        RECORDING,
        FINISHED_RECORDING,
        STOPPED,
        FINISHED,
        FINAL_SAVE,
        VIEW_VISIT,
    }

    ;


    private CaptureSessionState sessionState = CaptureSessionState.STOPPED;

    public CaptureSessionState getSessionState() {
        return sessionState;
    }

    public void setSessionState(CaptureSessionState sessionState) {
        this.sessionState = sessionState;
        Log.d(TAG, "SESSION STATE:" + sessionState);
    }

    public void addPlan(Visit visit) {
        databaseHandler.addVisit(visit);
    }

    public void setupDatabase(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public List<Visit> getAllVisits() {
        return databaseHandler.getAllVisits();
    }


    public List<Physician> getAllPhysicians() {
        return databaseHandler.getAllPhysicians();
    }

    public void addPhysician(Physician physician) {
        databaseHandler.addPhysician(physician);
    }
}
