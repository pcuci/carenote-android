package com.health.caresnap;

import java.util.ArrayList;
import java.util.List;
import android.app.Application;
import android.util.Log;

public class CaptureSessionGlobal extends Application {
	private String TAG = "SESSION_STATE";
	private String recording;
	private List impresions = new ArrayList<ImpressionEntry>();

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

	public String getRecording() {
		return recording;
	}

	public void setRecording(String recording) {
		this.recording = recording;
	}

	public List getImpresions() {
		return impresions;
	}

	public void setImpresions(List impresions) {
		this.impresions = impresions;
	}

	public void addImpression(ImpressionEntry impression) {
		impresions.add(impression);
	}
}
