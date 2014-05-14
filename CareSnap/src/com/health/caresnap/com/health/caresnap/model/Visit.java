package com.health.caresnap.com.health.caresnap.model;

import android.text.format.Time;

public class Visit {
    private int id;
    private Physician physician;
    private String location;
    private String impressionNote;
    private String resultsNote;
    private String testsNote;
    private Time time;

    public Visit(int id, Physician physician,
                 String location, String note, Time time) {
        this.id = id;
        this.physician = physician;
        this.location = location;
        this.impressionNote = note;
        this.time = time;

    }

    public Visit(Physician physician,
                 String location, String note, Time time) {
        this(-1, physician, location, note, time);

    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public Physician getPhysician() {
        return physician;
    }

    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImpressionNote() {
        return impressionNote;
    }

    public void setImpressionNote(String impressionNote) {
        this.impressionNote = impressionNote;
    }

    public String getResultsNote() {
        return resultsNote;
    }

    public void setResultsNote(String resultsNote) {
        this.resultsNote = resultsNote;
    }

    public String getTestsNote() {
        return testsNote;
    }

    public void setTestsNote(String testsNote) {
        this.testsNote = testsNote;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
