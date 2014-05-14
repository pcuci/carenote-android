package com.health.caresnap.com.health.caresnap.ui;


import android.app.Activity;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.health.caresnap.R;
import com.health.caresnap.com.health.caresnap.model.Visit;
import org.w3c.dom.Text;

import java.util.List;

public class ShowVisitsAdapter extends ArrayAdapter<Visit> {
    private final Activity context;
    private final List<Visit> visits;

    static class ViewHolder {
        public TextView id;
        public TextView physician_name;
        public TextView speciality;
        public TextView time;

    }

    public ShowVisitsAdapter(Activity context, List<Visit> visits) {
        super(context, R.layout.visit_listview_custom_row, visits);
        this.context = context;
        this.visits = visits;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.visit_listview_custom_row, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.id = (TextView) rowView.findViewById(R.id.visit_listview_custom_row_KEY_ID_textView);
            viewHolder.physician_name = (TextView) rowView.findViewById(R.id.visit_listview_custom_row_physician_textView);
            viewHolder.speciality = (TextView) rowView.findViewById(R.id.visit_listview_custom_row_physician_speciality_textView);
            viewHolder.time = (TextView) rowView
                    .findViewById(R.id.visit_listview_custom_row_creation_time_textView);
            rowView.setTag(viewHolder);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder holder =  (ViewHolder)v.getTag();
                    Toast.makeText(context.getApplicationContext(),
                            holder.id.getText()+" "+holder.physician_name.getText()+" "+ holder.time.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Visit visit = visits.get(position);
        holder.physician_name.setText(visit.getPhysician().getName());
        holder.speciality.setText(visit.getPhysician().getSpeciality());

        holder.id.setText(String.valueOf(position + 1));
        holder.time.setText(visit.getTime().format("%c"));
        return rowView;
    }

}