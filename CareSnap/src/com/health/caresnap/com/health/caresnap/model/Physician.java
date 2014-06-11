package com.health.caresnap.com.health.caresnap.model;

import java.io.Serializable;

public class Physician implements Serializable{
    private int physicianId;
    private String speciality;
    private String firstName;
    private String lastName;

    public Physician(int physicianId, String firstName, String lastName, String speciality) {
        this.physicianId = physicianId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.speciality = speciality;
    }

    public Physician(String firstName, String lastName, String speciality) {
        this(-1, firstName, lastName, speciality);
    }

    public int getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(int physicianId) {
        this.physicianId = physicianId;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
