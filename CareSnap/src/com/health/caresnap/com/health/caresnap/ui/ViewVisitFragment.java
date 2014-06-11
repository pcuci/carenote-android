package com.health.caresnap.com.health.caresnap.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.health.caresnap.CaptureSessionGlobal;
import com.health.caresnap.R;
import com.health.caresnap.com.health.caresnap.model.Physician;
import com.health.caresnap.com.health.caresnap.model.Visit;

import java.util.List;

/**
 * Fragment to hold all the UI components and related Logic for Listing
 * StoryData.
 *
 * @author Michael A. Walker
 */
public class ViewVisitFragment extends Fragment implements ViewNewFragmentCommunicator {
    public Context context;
    static final String LOG_TAG = ViewVisitFragment.class.getCanonicalName();
    private ViewNewActivityCommunicator activityCommunicator;
    private TextView physicianTextView;
    private TextView clinicNameTextView;
    private String physicianName;
    private String clinicName;



    public ViewVisitFragment(String physicianName, String clinicName){
        this.physicianName = physicianName;
        this.clinicName = clinicName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.visit_view_fragment, container, false);
        physicianTextView = (TextView) fragmentView.findViewById(R.id.view_visit_physician_name);
        clinicNameTextView = (TextView) fragmentView.findViewById(R.id.visit_view_clinic_name);
        physicianTextView.setText(physicianName);
        clinicNameTextView.setText(clinicName);
        return fragmentView;
    }

    private void populateVisitGui() {
        CaptureSessionGlobal session = (CaptureSessionGlobal) context.getApplicationContext();
        //physicianTextView.setText(session);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = getActivity();
        activityCommunicator = (ViewNewActivityCommunicator) context;
        ((VisitViewNewActivity) context).fragmentCommunicator = this;
    }

    @Override
    public void updatePhysicians() {

    }

    @Override
    public void updateClinicName() {

    }


}

