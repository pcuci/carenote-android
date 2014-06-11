package com.health.caresnap.com.health.caresnap.ui;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.health.caresnap.CaptureSessionGlobal;
import com.health.caresnap.R;
import com.health.caresnap.com.health.caresnap.model.Note;
import com.health.caresnap.com.health.caresnap.model.NoteType;

import java.util.List;

public class NoteTypeAdapter extends ArrayAdapter<Note> {
    private final Activity context;
    private final List<Note> notes;
    private static int MAX_TEXT_PREVIEW_SIZE = 50;
    private final int REQUEST_CODE = 1;
    private CaptureSessionGlobal session;
    private int selectedNoteIndex;

    static class ViewHolder {
        public TextView noteType;
        public ImageView recordImage;
        public TextView previewText;
    }

    public NoteTypeAdapter(Activity context, List<Note> notes) {
        super(context, R.layout.visit_notes_listview_custom_row, notes);
        this.context = context;
        this.notes = notes;
        session = (CaptureSessionGlobal) context.getApplicationContext();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.visit_notes_listview_custom_row, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.noteType = (TextView) rowView.findViewById(R.id.note_type);
            viewHolder.recordImage = (ImageView) rowView.findViewById(R.id.note_record_image_view);
            viewHolder.previewText = (TextView) rowView.findViewById(R.id.note_text_preview);
            rowView.setTag(viewHolder);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder holder = (ViewHolder) v.getTag();
                    Note note = notes.get(position);
                    selectedNoteIndex = position;
                    Intent intent = null;
                    if (note.isSaved()) {
                        if (session.getSessionState() == CaptureSessionGlobal.CaptureSessionState.STOPPED) {
                            intent = new Intent(context, NoteViewActivity.class);
                            intent.putExtra(IntentExtras.NOTE.toString(), note);
                        }
                    } else {
                        if (session.getSessionState() == CaptureSessionGlobal.CaptureSessionState.STARTING_NEW_VISIT || session.getSessionState() == CaptureSessionGlobal.CaptureSessionState.FINISHED_RECORDING) {

                            intent = new Intent(context, NoteNewActivity.class);
                            intent.putExtra(IntentExtras.NOTE.toString(), note);
                        }
                    }
                    context.startActivityForResult(intent, REQUEST_CODE);
                }

            });
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Note note = notes.get(position);
        NoteType noteType = note.getType();
        holder.noteType.setText(noteType.toString());
        holder.recordImage.setVisibility(note.isSaved() ? View.INVISIBLE : View.VISIBLE);
        int noteTextLength = noteType.toString().length();
        String text = note.getText();
        if (text == null)

        {
            text = "";
        }

        String previewText = noteTextLength < MAX_TEXT_PREVIEW_SIZE ? text : (text.substring(0, MAX_TEXT_PREVIEW_SIZE) + "...");
        holder.previewText.setText(previewText);
        return rowView;
    }

    public void updateNoteAtSelectedNoteIndex(Note newNote) {
        if (selectedNoteIndex != -1) {
            Note note = notes.get(selectedNoteIndex);
            note.setText(newNote.getText());
            notifyDataSetInvalidated();
        }
        selectedNoteIndex = -1;
    }

}