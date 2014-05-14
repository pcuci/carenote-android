package com.health.caresnap.com.health.caresnap.persistance;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;
import android.util.Log;

import com.health.caresnap.com.health.caresnap.model.Physician;
import com.health.caresnap.com.health.caresnap.model.Visit;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "CareSnap";

    // Contacts table name
    private static final String TABLE_VISITS = "Visits";
    private static final String TABLE_PHYSICIANS = "Physicians";
    private static final String KEY_PHYSICIAN_ID_STRING = "physician_id";
    // Visits column names
    private String TAG = "DATABASE";

    ;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_VISITS_TABLE = "CREATE TABLE " + TABLE_VISITS
                + "(" + VisitColumn.KEY_VISIT_ID + " integer PRIMARY KEY AUTOINCREMENT," +
                VisitColumn.KEY_PHYSICIAN_ID.toString() + " integer," +
                VisitColumn.KEY_LOCATION + " physician_name not null," +
                VisitColumn.KEY_IMPRESSION_NOTE + " physician_name," +
                VisitColumn.KEY_RESULTS_NOTE + " physician_name," +
                VisitColumn.KEY_TESTS_NOTE + " physician_name," +
                VisitColumn.KEY_TIME + " datetime" +
                ",FOREIGN KEY(" + VisitColumn.KEY_PHYSICIAN_ID + ") REFERENCES " + TABLE_PHYSICIANS + "(" + VisitColumn.KEY_PHYSICIAN_ID + "))";

        String CREATE_DOCTORS_TABLE = "CREATE TABLE " + TABLE_PHYSICIANS + "(" + PhysicianColumn.KEY_PHYSICIAN + " integer PRIMARY KEY autoincrement," +
                PhysicianColumn.KEY_PHYSICIAN_NAME + " physician_name," +
                PhysicianColumn.KEY_PHYSICIAN_SPECIALITY + " physician_name" + ")";

        db.execSQL(CREATE_DOCTORS_TABLE);
        db.execSQL(CREATE_VISITS_TABLE);
        Log.d(TAG, "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITS);

        onCreate(db);
    }

    public void addVisit(Visit visit) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(VisitColumn.KEY_PHYSICIAN_ID.toString(), visit.getPhysician().getPhysicianId());
        values.put(VisitColumn.KEY_LOCATION.toString(), visit.getLocation());
        values.put(VisitColumn.KEY_IMPRESSION_NOTE.toString(), visit.getImpressionNote());
        values.put(VisitColumn.KEY_RESULTS_NOTE.toString(), visit.getResultsNote());
        values.put(VisitColumn.KEY_TESTS_NOTE.toString(), visit.getTestsNote());
        values.put(VisitColumn.KEY_TIME.toString(), visit.getTime().format2445());

        db.insert(TABLE_VISITS, null, values);
        db.close();
    }

    public Visit getVisit(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_VISITS, new String[]{VisitColumn.KEY_VISIT_ID.toString(),
                VisitColumn.KEY_PHYSICIAN_ID.toString(), VisitColumn.KEY_LOCATION.toString(), VisitColumn.KEY_IMPRESSION_NOTE.toString(), VisitColumn.KEY_RESULTS_NOTE.toString(), VisitColumn.KEY_TESTS_NOTE.toString(), VisitColumn.KEY_TIME.toString()},
                VisitColumn.KEY_VISIT_ID.toString() + "=?", new String[]{String.valueOf(id)}, null, null,
                null, null);


        if (cursor != null) {
            cursor.moveToFirst();
        }

        int physicianId = Integer.parseInt(cursor.getString(VisitColumn.KEY_PHYSICIAN_ID.ordinal()));
        Physician physician = getPhysician(physicianId);

        Time timestamp = parseStringToTime(cursor.getString(VisitColumn.KEY_TIME.ordinal()));
        Log.d(TAG, "time from db:" + timestamp.format("%c"));

        Visit visit = new Visit(Integer.parseInt(cursor
                .getString(VisitColumn.KEY_VISIT_ID.ordinal())), physician, cursor.getString(VisitColumn.KEY_LOCATION.ordinal()),
                cursor.getString(VisitColumn.KEY_IMPRESSION_NOTE.ordinal()), timestamp);

        cursor.close();
        db.close();
        return visit;
    }

    public List<Visit> getAllVisits() {

        List<Visit> visitList = new ArrayList<Visit>();

        String visitsSelectQuery = "SELECT * FROM " + TABLE_VISITS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(visitsSelectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int physicianId = Integer.parseInt(cursor.getString(VisitColumn.KEY_PHYSICIAN_ID.ordinal()));
                Physician physician = getPhysician(physicianId);
                Time timestamp = parseStringToTime(cursor.getString(VisitColumn.KEY_TIME.ordinal()));
                Log.d(TAG, "time from db:" + timestamp.format("%c"));
                Visit visit = new Visit(Integer.parseInt(cursor
                        .getString(VisitColumn.KEY_VISIT_ID.ordinal())), physician, cursor.getString(VisitColumn.KEY_LOCATION.ordinal()),
                        cursor.getString(VisitColumn.KEY_IMPRESSION_NOTE.ordinal()), timestamp);
                visitList.add(visit);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return visitList;
    }

    public int getVisitsCount() {

        String countQuery = "SELECT  * FROM " + TABLE_VISITS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateVisit(Visit visit) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(VisitColumn.KEY_PHYSICIAN_ID.toString(), visit.getPhysician().getPhysicianId());
        values.put(VisitColumn.KEY_LOCATION.toString(), visit.getLocation());
        values.put(VisitColumn.KEY_IMPRESSION_NOTE.toString(), visit.getImpressionNote());
        values.put(VisitColumn.KEY_TIME.toString(), visit.getTime().format("%c"));

        return db.update(TABLE_VISITS, values, VisitColumn.KEY_VISIT_ID.toString() + " = ?",
                new String[]{String.valueOf(visit.getID())});
    }

    public void deleteVisit(Visit visit) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VISITS, VisitColumn.KEY_VISIT_ID.toString() + " = ?",
                new String[]{String.valueOf(visit.getID())});
        db.close();
    }

    public Physician getPhysician(int physicianId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor physicianCursor = db.query(TABLE_PHYSICIANS, new String[]{PhysicianColumn.KEY_PHYSICIAN.toString(),
                PhysicianColumn.KEY_PHYSICIAN_NAME.toString(), PhysicianColumn.KEY_PHYSICIAN_SPECIALITY.toString()},
                PhysicianColumn.KEY_PHYSICIAN.toString() + "=?", new String[]{String.valueOf(physicianId)}, null, null,
                null, null);

        if (physicianCursor != null)
            physicianCursor.moveToFirst();

        Physician physician = new Physician(Integer.parseInt(physicianCursor.getString(PhysicianColumn.KEY_PHYSICIAN.ordinal())), physicianCursor.getString(PhysicianColumn.KEY_PHYSICIAN_NAME.ordinal()), physicianCursor.getString(PhysicianColumn.KEY_PHYSICIAN_SPECIALITY.ordinal()));

        physicianCursor.close();
        db.close();
        return physician;
    }

    public void addPhysician(Physician physician) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PhysicianColumn.KEY_PHYSICIAN_NAME.toString(), physician.getName());
        values.put(PhysicianColumn.KEY_PHYSICIAN_SPECIALITY.toString(), physician.getSpeciality());

        db.insert(TABLE_PHYSICIANS, null, values);
        db.close();
    }

    public List<Physician> getAllPhysicians() {

        List<Physician> physicianList = new ArrayList<Physician>();

        String selectQuery = "SELECT * FROM " + TABLE_PHYSICIANS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor physicianCursor = db.rawQuery(selectQuery, null);

        if (physicianCursor.moveToFirst()) {
            do {
                Physician physician = new Physician(Integer.parseInt(physicianCursor.getString(PhysicianColumn.KEY_PHYSICIAN.ordinal())), physicianCursor.getString(PhysicianColumn.KEY_PHYSICIAN_NAME.ordinal()), physicianCursor.getString(PhysicianColumn.KEY_PHYSICIAN_SPECIALITY.ordinal()));
                physicianList.add(physician);
            } while (physicianCursor.moveToNext());
        }

        physicianCursor.close();
        db.close();

        return physicianList;
    }

    private Time parseStringToTime(String time224) {
        Time timestamp = new Time();
        timestamp.parse(time224);
        return timestamp;
    }

    private enum VisitColumn {
        KEY_VISIT_ID("visit_id"), KEY_PHYSICIAN_ID(KEY_PHYSICIAN_ID_STRING), KEY_LOCATION("location"), KEY_IMPRESSION_NOTE("impression_note"), KEY_RESULTS_NOTE("results_note"), KEY_TESTS_NOTE("tests_note"), KEY_TIME("visit_time");
        private String columnName;

        private VisitColumn(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    private enum PhysicianColumn {
        KEY_PHYSICIAN(KEY_PHYSICIAN_ID_STRING), KEY_PHYSICIAN_NAME("physician_name"), KEY_PHYSICIAN_SPECIALITY("physician_speciality");
        private String columnName;

        private PhysicianColumn(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

}