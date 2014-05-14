package com.health.caresnap.com.health.caresnap.ui;

import android.app.ListFragment;
import android.os.Bundle;
import com.health.caresnap.CaptureSessionGlobal;
import com.health.caresnap.com.health.caresnap.model.Visit;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to hold all the UI components and related Logic for Listing
 * StoryData.
 * 
 * @author Michael A. Walker
 * 
 */
public class NoteTypeListFragment extends ListFragment {

	static final String LOG_TAG = NoteTypeListFragment.class.getCanonicalName();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<String> noteTypes = new ArrayList<String>();
        noteTypes.add(NoteType.IMPRESSION.toString());
        noteTypes.add(NoteType.RESULTS.toString());
        noteTypes.add(NoteType.TESTS.toString());
        NoteTypeAdapter adapter = new NoteTypeAdapter(getActivity(),noteTypes );
        setListAdapter(adapter);
    }

}
