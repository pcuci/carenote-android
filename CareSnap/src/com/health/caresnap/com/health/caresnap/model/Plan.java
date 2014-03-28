package com.health.caresnap.com.health.caresnap.model;

import android.text.format.Time;

public class Plan {
    private int id;
    private Physician physician;
    private String location;
    private String note;
    private Time time;

    public Plan(int id, Physician physician,
                String location, String note, Time time) {
        this.id = id;
        this.physician = physician;
        this.location = location;
        this.note = note;
        this.time = time;

    }

    public Plan(Physician physician,
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
