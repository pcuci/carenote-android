package com.health.caresnap.com.health.caresnap.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.*;
import com.health.caresnap.CaptureSessionGlobal;
import com.health.caresnap.R;
import com.health.caresnap.com.health.caresnap.model.Physician;

/**
 * Created by User on 27/03/14.
 */
public class AddNewPhysicianDialog extends Dialog {
    private Button addButton;
    private Button cancelButton;
    private TextView firstName;
    private TextView lastName;
    private Spinner speciality;


    public AddNewPhysicianDialog(Context context) {
        super(context);
        setContentView(R.layout.add_new_physician_dialog);
        setTitle("Add New Physician");
        addButton = (Button) findViewById(R.id.dialog_add_button);
        cancelButton = (Button) findViewById(R.id.dialog_cancel_button);
        firstName = (TextView) findViewById(R.id.add_practitioner_first_name_textView);
        lastName = (TextView) findViewById(R.id.add_practitioner_last_name_textView);
        speciality = (Spinner) findViewById(R.id.speciality_spinner);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameStr = firstName.getText().toString();
                String lastNameStr = lastName.getText().toString();
                if (firstNameStr == null || firstNameStr.equals("")) {
                    Toast.makeText(getContext(), "Enter Physician's first name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (lastNameStr == null || lastNameStr.equals("")) {
                    Toast.makeText(getContext(), "Enter Physician's last name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (speciality.getSelectedItemId() == 0) {
                    Toast.makeText(getContext(), "Select Speciality", Toast.LENGTH_LONG).show();
                    return;
                }

                CaptureSessionGlobal session = ((CaptureSessionGlobal) AddNewPhysicianDialog.this.getContext().getApplicationContext());
                Physician physician = new Physician(firstNameStr, lastNameStr, (String) speciality.getSelectedItem());
                session.addPhysician(physician);

                AddNewPhysicianDialog.this.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AddNewPhysicianDialog.this.dismiss();
            }
        });
    }
}
