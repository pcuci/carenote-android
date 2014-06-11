package com.health.caresnap.com.health.caresnap.model;

import android.text.format.Time;

import java.io.IOException;
import java.io.Serializable;

public class Visit implements Serializable {
    private int id;
    private Physician physician;
    private String location;
    private Note impressionNote;
    private Note resultsNote;
    private Note testsNote;
    private Time time;
    private boolean isSaved;


    public Visit(int id, Physician physician,
                 String location, Note impressionNote, Note resultsNote, Note testsNote, Time time, boolean isSaved) {
        this.id = id;
        this.physician = physician;
        this.location = location;
        this.impressionNote = impressionNote;
        this.resultsNote = resultsNote;
        this.testsNote = testsNote;
        this.time = time;

        this.isSaved = isSaved;
    }

    public Visit(Physician physician,
                 String location, Note impressionNote, Note resultsNote, Note testsNote, Time time, boolean isSaved) {
        this(-1, physician, location, impressionNote, resultsNote, testsNote, time, isSaved);
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

    public Note getImpressionNote() {
        return impressionNote;
    }

    public void setImpressionNote(Note impressionNote) {
        this.impressionNote = impressionNote;
    }

    public Note getResultsNote() {
        return resultsNote;
    }

    public void setResultsNote(Note resultsNote) {
        this.resultsNote = resultsNote;
    }

    public Note getTestsNote() {
        return testsNote;
    }

    public void setTestsNote(Note testsNote) {
        this.testsNote = testsNote;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }


    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.writeObject(getPhysician());
        out.writeUTF(getLocation());
        out.writeObject(getImpressionNote());
        out.writeObject(getResultsNote());
        out.writeObject(getTestsNote());
        out.writeUTF(getTime().format2445());
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        setPhysician((Physician) in.readObject());
        setLocation(in.readUTF());
        setImpressionNote((Note) in.readObject());
        setResultsNote((Note) in.readObject());
        setTestsNote((Note) in.readObject());
        setTime(parseStringToTime(in.readUTF()));


    }

    private Time parseStringToTime(String time224) {
        Time timestamp = new Time();
        timestamp.parse(time224);
        return timestamp;
    }
}
