package com.health.caresnap.com.health.caresnap.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.health.caresnap.R;
import com.health.caresnap.com.health.caresnap.model.Visit;

import java.util.List;

public class ShowVisitsAdapter extends ArrayAdapter<Visit> {
    public final static String SER_KEY = "com.health.caresnap.ser.VISIT";

    private static int PREVIEW_LENGTH = 200;
    private final Activity context;
    private final List<Visit> visits;

    static class ViewHolder {
        public TextView id;
        public TextView physician_first_name;
        public TextView physician_last_name;
        public TextView speciality;
        public TextView time;
        public TextView impression;
        public TextView results;
        public TextView tests;

    }

    public ShowVisitsAdapter(Activity context, List<Visit> visits) {
        super(context, R.layout.visit_listview_custom_row, visits);
        this.context = context;
        this.visits = visits;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.visit_listview_custom_row, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.id = (TextView) rowView.findViewById(R.id.visit_listview_custom_row_KEY_ID_textView);
            viewHolder.physician_first_name = (TextView) rowView.findViewById(R.id.visit_listview_custom_row_physician_fname_textView);
            viewHolder.physician_last_name = (TextView) rowView.findViewById(R.id.visit_listview_custom_row_physician_lname_textView);
            viewHolder.speciality = (TextView) rowView.findViewById(R.id.visit_listview_custom_row_physician_speciality_textView);
            viewHolder.time = (TextView) rowView
                    .findViewById(R.id.visit_listview_custom_row_creation_time_textView);
            viewHolder.impression = (TextView) rowView.findViewById(R.id.visit_view_impression_text);
            viewHolder.results = (TextView) rowView.findViewById(R.id.visit_view_results_text);
            viewHolder.tests = (TextView) rowView.findViewById(R.id.visit_view_tests_text);
            rowView.setTag(viewHolder);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Visit visit = visits.get(position);
                    ViewHolder holder = (ViewHolder) v.getTag();
                    Intent intent = new Intent(context.getApplicationContext(), VisitViewNewActivity.class);
                    intent.putExtra(IntentExtras.VISIT_TYPE.toString(), VisitViewNewActivity.Type.VIEW.toString());
                    intent.putExtra(SER_KEY, visit);
                    context.startActivity(intent);
                }
            });
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Visit visit = visits.get(position);
        holder.physician_first_name.setText(visit.getPhysician().getFirstName());
        holder.physician_last_name.setText(visit.getPhysician().getLastName());
        holder.speciality.setText(visit.getPhysician().getSpeciality());

        String impression = visit.getImpressionNote().getText();
        String results = visit.getResultsNote().getText();
        String tests = visit.getTestsNote().getText();

        holder.id.setText(String.valueOf(position + 1));
        holder.time.setText(visit.getTime().format("%c"));
        if (impression == null) {
            impression = "No impression recorded.";
        }
        if (results == null) {
            results = "No results recorded.";
        }
        if (tests == null) {
            tests = "No tests recorded.";
        }
        holder.impression.setText(impression.substring(0, impression.length() < PREVIEW_LENGTH ? impression.length() : PREVIEW_LENGTH) + "...");
        holder.results.setText(results.substring(0, results.length() < PREVIEW_LENGTH ? results.length() : PREVIEW_LENGTH) + "...");
        holder.tests.setText(tests.substring(0, tests.length() < PREVIEW_LENGTH ? tests.length() : PREVIEW_LENGTH) + "...");
        return rowView;
    }
}