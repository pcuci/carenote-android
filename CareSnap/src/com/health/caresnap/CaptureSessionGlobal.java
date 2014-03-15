package com.health.caresnap;

import java.util.List;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.health.caresnap.com.health.caresnap.model.Impression;
import com.health.caresnap.com.health.caresnap.persistance.DatabaseHandler;

public class CaptureSessionGlobal extends Application {
    private String TAG = "SESSION_STATE";
    private String recording;
    private DatabaseHandler databaseHandler;

    public enum CaptureSessionState {
        STARTING,
        PAUSED,
        RECORDING,
        FINISHED_RECORDING,
        STOPPED,
        FINISHED,
        FINAL_SAVE
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

    public String getRecording() {
        return recording;
    }

    public void setRecording(String recording) {
        this.recording = recording;
    }

    public void addImpression(Impression impression) {
        databaseHandler.addImpression(impression);
    }

    public void setupDatabase(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public List<Impression> getAllImpressions() {
        return databaseHandler.getAllImpressions();
    }
}
