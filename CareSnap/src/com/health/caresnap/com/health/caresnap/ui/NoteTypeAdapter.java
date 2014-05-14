package com.health.caresnap.com.health.caresnap.ui;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.health.caresnap.R;
import com.health.caresnap.com.health.caresnap.model.Visit;

import java.util.List;

public class NoteTypeAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final List<String> notesTypes;

    static class ViewHolder {
        public TextView noteType;
        public ImageView recordImage;
    }

    public NoteTypeAdapter(Activity context, List<String> notesTypes) {
        super(context, R.layout.visit_notes_listview_custom_row, notesTypes);
        this.context = context;
        this.notesTypes = notesTypes;
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

            viewHolder.noteType = (TextView) rowView.findViewById(R.id.note_type);
            viewHolder.recordImage = (ImageView)rowView.findViewById(R.id.note_record_image_view);
            rowView.setTag(viewHolder);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder holder =  (ViewHolder)v.getTag();

                }
            });
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String noteType = notesTypes.get(position);

        return rowView;
    }

}