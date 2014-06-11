package com.health.caresnap.com.health.caresnap.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.Time;
import android.view.*;
import android.widget.*;
import com.health.caresnap.CaptureSessionGlobal;
import com.health.caresnap.R;
import com.health.caresnap.com.health.caresnap.model.Note;
import com.health.caresnap.com.health.caresnap.model.NoteType;
import com.health.caresnap.com.health.caresnap.model.Physician;
import com.health.caresnap.com.health.caresnap.model.Visit;

import java.util.List;

/**
 * Fragment to hold all the UI components and related Logic for Listing
 * StoryData.
 *
 * @author Michael A. Walker
 */
public class NewVisitFragment extends Fragment implements ViewNewFragmentCommunicator {

    public Context context;
    static final String LOG_TAG = NewVisitFragment.class.getCanonicalName();
    private Spinner physiciansSpinner;
    private EditText hospitalTextView;
    private CaptureSessionGlobal session;
    private ViewNewActivityCommunicator activityCommunicator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.visit_new_fragment, container, false);
        physiciansSpinner = (Spinner) fragmentView.findViewById(R.id.physiciansSpinner);
        session = (CaptureSessionGlobal) getActivity().getApplicationContext();
        CaptureSessionGlobal.CaptureSessionState sessionState = session
                .getSessionState();
        hospitalTextView = (EditText) fragmentView.findViewById(R.id.visit_view_clinic_name);
        if (sessionState == CaptureSessionGlobal.CaptureSessionState.STOPPED) {
            updateSessionState(CaptureSessionGlobal.CaptureSessionState.STARTING_NEW_VISIT);
            clearFields();
            physiciansSpinner.setSelection(0);
        }
        return fragmentView;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {

        context = getActivity();
        activityCommunicator = (ViewNewActivityCommunicator) context;
        ((VisitViewNewActivity) context).fragmentCommunicator = this;


        super.onAttach(activity);
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.new_visit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_confirm_save:
                CaptureSessionGlobal.CaptureSessionState state = session.getSessionState();
                saveVisit(state);
                return true;
            case R.id.action_new_physician:
                startNewPhysicianDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSessionState(CaptureSessionGlobal.CaptureSessionState newState) {
        if (newState == null)
            return;
        session.setSessionState(newState);
    }

    private Physician[] getAllPhysicians(CaptureSessionGlobal session) {
        List<Physician> doctorsList = session.getAllPhysicians();
        return doctorsList.toArray(new Physician[doctorsList.size()]);
    }

    private void populateDoctorsSpinner(CaptureSessionGlobal session) {
        Physician[] physicians = getAllPhysicians(session);
        ArrayAdapter<Physician> dataAdapter = new PhysicianSpinAdapter(this.getActivity().getBaseContext(), android.R.layout.simple_spinner_item, physicians);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        physiciansSpinner.setEmptyView(LayoutInflater.from(this.getActivity().getBaseContext()).inflate(R.layout.select_physician_nothing_selected, (ViewGroup) physiciansSpinner.getParent(), false));
        physiciansSpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        dataAdapter,
                        R.layout.select_physician_nothing_selected,
                        R.layout.select_physician_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this.getActivity()));
    }


    private void clearFields() {
        populateDoctorsSpinner(session);
        hospitalTextView.setText("");
    }

    private void startNewPhysicianDialog() {
        final AddNewPhysicianDialog dialog = new AddNewPhysicianDialog(getActivity());
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                long selectedPhysicianId = physiciansSpinner.getSelectedItemId() + 1;
                populateDoctorsSpinner(session);
                physiciansSpinner.setSelection((int) selectedPhysicianId);
            }
        });
        dialog.show();
    }

    private void saveVisit(CaptureSessionGlobal.CaptureSessionState state) {
        state = CaptureSessionGlobal.CaptureSessionState.FINISHED_RECORDING;
        if (state == CaptureSessionGlobal.CaptureSessionState.FINISHED_RECORDING) {
            NothingSelectedSpinnerAdapter wrappedAdapter = (NothingSelectedSpinnerAdapter) physiciansSpinner.getAdapter();
            int selectedPhysicianId = (int) physiciansSpinner.getSelectedItemId();
            String hospitalName = hospitalTextView.getText().toString();

            Physician physician = null;
            List<Note> notes = activityCommunicator.getNotes();
            Note impressionNote = notes.get(0);
            Note resultsNote = notes.get(1);
            Note testsNote = notes.get(2);
            try {
                physician = (Physician) wrappedAdapter.getAdapter().getItem(selectedPhysicianId);
            } catch (Exception e) {

                Toast.makeText(getActivity().getBaseContext(), "Select a physician.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (hospitalName == null || hospitalName.equals("")) {
                Toast.makeText(getActivity().getBaseContext(), "Enter clinic name.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (impressionNote.getText() == null || impressionNote.getText().equals("")) {
                Toast.makeText(getActivity().getBaseContext(), "Impression is empty.", Toast.LENGTH_SHORT).show();
                return;
            }
            impressionNote.setIsSaved(true);
            resultsNote.setIsSaved(true);
            testsNote.setIsSaved(true);
            Visit visit = new Visit(
                    physician,
                    hospitalName, impressionNote,
                    resultsNote,
                    testsNote,
                    getCurrentTime(), true);
            session.addPlan(visit);
            session.setSessionState(CaptureSessionGlobal.CaptureSessionState.STARTING_NEW_VISIT);

            Toast.makeText(getActivity().getApplicationContext(), "Visit saved.", Toast.LENGTH_SHORT).show();
        }
    }

    private Time getCurrentTime() {
        Time now = new Time();
        now.setToNow();
        return now;
    }

    @Override
    public void updatePhysicians() {

    }

    @Override
    public void updateClinicName() {

    }
}

