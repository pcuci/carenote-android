package com.health.caresnap.com.health.caresnap.model;

public class Physician {
    private int physicianId;
    private String speciality;
    private String name;

    public Physician(int physicianId, String name, String speciality) {
        this.physicianId = physicianId;
        this.name = name;
        this.speciality = speciality;
    }

    public Physician(String name, String speciality) {
        this(-1, name, speciality);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
