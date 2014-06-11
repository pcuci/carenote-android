package com.health.caresnap.com.health.caresnap.model;

import java.io.Serializable;

/**
 * Created by User on 15/05/14.
 */
public class Note implements Serializable{
    private NoteType type;
    private String text;
    private boolean isSaved;

    public Note(NoteType type, String text, boolean isSaved) {

        this.type = type;
        this.text = text;
        this.isSaved = isSaved;
    }

    public Note(NoteType type) {
        this(type, null, false);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }

    public NoteType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public boolean isSaved() {
        return isSaved;
    }
}
