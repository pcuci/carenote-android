package com.health.caresnap.com.health.caresnap.ui;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
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
public class ViewPlansListFragment extends ListFragment {

	static final String LOG_TAG = ViewPlansListFragment.class.getCanonicalName();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CaptureSessionGlobal global = ((CaptureSessionGlobal) getActivity().getApplicationContext());
        List<Visit> allVisits = global.getAllVisits();
        ShowVisitsAdapter adapter = new ShowVisitsAdapter(getActivity(), allVisits);
        setListAdapter(adapter);
        this.setEmptyText("No visits yet!");

    }

}
