package com.health.caresnap.com.health.caresnap.model;

import java.io.Serializable;

/**
 * Created by User on 14/05/14.
 */
public enum NoteType implements Serializable{
    IMPRESSION("Impression"), RESULTS("Results"), TESTS("Tests");
    private String title;

    NoteType(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
