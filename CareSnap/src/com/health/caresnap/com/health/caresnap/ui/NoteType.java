package com.health.caresnap.com.health.caresnap.ui;

/**
 * Created by User on 14/05/14.
 */
public enum NoteType {
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
