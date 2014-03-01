package com.health.caresnap;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.util.Log;

public class CaptureSessionGlobal extends Application {
	private String TAG = "SESSION_STATE";
	private String recording;
	private List<ImpressionEntry> impressions = new ArrayList<ImpressionEntry>();

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

	public List<ImpressionEntry> getImpresions() {
		return impressions;
	}

	public void setImpresions(List<ImpressionEntry> impressions) {
		this.impressions = impressions;
	}

	public void addImpression(ImpressionEntry impression) {
		impressions.add(impression);
	}
}
