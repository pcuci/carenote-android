package com.health.caresnap.com.health.caresnap.ui;

import android.app.ListFragment;
import android.os.Bundle;
import com.health.caresnap.com.health.caresnap.model.Note;
import com.health.caresnap.com.health.caresnap.model.NoteType;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to hold all the UI components and related Logic for Listing
 * StoryData.
 *
 * @author Michael A. Walker
 */
public class NoteTypeListFragment extends ListFragment {

    static final String LOG_TAG = NoteTypeListFragment.class.getCanonicalName();
    private List<Note> notes;
    private NoteTypeAdapter adapter;

    public NoteTypeListFragment() {
        this.notes = new ArrayList<Note>();
        createEmptyNotesList();

    }

    public NoteTypeListFragment(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (notes == null) {
            createEmptyNotesList();
        }
        adapter = new NoteTypeAdapter(getActivity(), notes);
        setListAdapter(adapter);
    }

    private void createEmptyNotesList() {
        notes.add(new Note(NoteType.IMPRESSION));
        notes.add(new Note(NoteType.RESULTS));
        notes.add(new Note(NoteType.TESTS));
    }

    public void updateNote(Note newNote) {
        adapter.updateNoteAtSelectedNoteIndex(newNote);
    }

    public List<Note> getNotes() {
        return notes;
    }
}
