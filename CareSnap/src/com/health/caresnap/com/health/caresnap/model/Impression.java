package com.health.caresnap.com.health.caresnap.model;

public class Impression {

    // private variables
    private int id;
    private String name;
    private String specialty;
    private String location;
    private String note;
    private String time;

    // Empty constructor
    public Impression() {
    }

    // constructor
    public Impression(int id, String name, String specialty,
                      String location, String note, String time) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.location = location;
        this.note = note;
        this.time = time;

    }

    // constructor
    public Impression(String name, String specialty, String location,
                      String note, String time) {
        this.name = name;
        this.specialty = specialty;
        this.location = location;
        this.note = note;
        this.time = time;
    }

    // getting ID
    public int getID() {
        return this.id;
    }

    // setting id
    public void setID(int id) {
        this.id = id;
    }

    // getting name
    public String getName() {
        return this.name;
    }

    // setting name
    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    // setting name
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
