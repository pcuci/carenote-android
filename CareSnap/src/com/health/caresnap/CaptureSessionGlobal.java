package com.health.caresnap;

import android.app.Application;
import android.util.Log;

public class CaptureSessionGlobal extends Application {
	private String TAG = "SESSION_STATE";

	public enum CaptureSessionState {
		STARTING, PAUSED, RECORDING, FINISHED_RECORDING, STOPED, FINISHED, FINAL_SAVE
	};

	private CaptureSessionState sessionState = CaptureSessionState.STOPED;

	public CaptureSessionState getSessionState() {
		return sessionState;
	}

	public void setSessionState(CaptureSessionState sessionState) {
		this.sessionState = sessionState;
		Log.d(TAG, "SESSION STATE:" + sessionState);

	}

}
