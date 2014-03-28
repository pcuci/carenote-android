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
    private TextView name;
    private Spinner speciality;


    public AddNewPhysicianDialog(Context context) {
        super(context);
        setContentView(R.layout.add_new_physician_dialog);
        setTitle("Add New Physician");
        addButton = (Button) findViewById(R.id.dialog_add_button);
        cancelButton = (Button) findViewById(R.id.dialog_cancel_button);
        name = (TextView) findViewById(R.id.add_practitioner_name_textView);
        speciality = (Spinner) findViewById(R.id.speciality_spinner);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Enter Physician's name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (speciality.getSelectedItemId()== 0) {
                    Toast.makeText(getContext(), "Enter Speciality", Toast.LENGTH_LONG).show();
                    return;
                }

                CaptureSessionGlobal session = ((CaptureSessionGlobal) AddNewPhysicianDialog.this.getContext().getApplicationContext());
                Physician physician = new Physician(name.getText().toString(), (String) speciality.getSelectedItem());
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
