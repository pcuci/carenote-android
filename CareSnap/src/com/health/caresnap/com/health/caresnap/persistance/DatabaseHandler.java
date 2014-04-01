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
import com.health.caresnap.com.health.caresnap.model.Plan;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "snapCare";

    // Contacts table name
    private static final String TABLE_PLANS = "Plans";
    private static final String TABLE_PHYSICIANS = "Physicians";
    private static final String KEY_PHYSICIAN_ID_STRING = "physician_id";
    // Plans column names
    private String TAG = "DATABASE";

    ;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    ;

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLANS_TABLE = "CREATE TABLE " + TABLE_PLANS
                + "(" + PlanColumn.KEY_PLAN_ID + " integer PRIMARY KEY AUTOINCREMENT," + PlanColumn.KEY_PHYSICIAN_ID.toString() + " integer," + PlanColumn.KEY_LOCATION + " text not null," + PlanColumn.KEY_NOTE
                + " text," + PlanColumn.KEY_TIME + " datetime" + ",FOREIGN KEY(" + PlanColumn.KEY_PHYSICIAN_ID + ") REFERENCES " + TABLE_PHYSICIANS + "(" + PlanColumn.KEY_PHYSICIAN_ID + "))";
        String CREATE_DOCTORS_TABLE = "CREATE TABLE " + TABLE_PHYSICIANS + "(" + PhysicianColumn.KEY_PHYSICIAN + " integer PRIMARY KEY autoincrement," + PhysicianColumn.KEY_PHYSICIAN_NAME + " text,"
                + PhysicianColumn.KEY_PHYSICIAN_SPECIALITY + " text" + ")";

        db.execSQL(CREATE_DOCTORS_TABLE);
        db.execSQL(CREATE_PLANS_TABLE);
        Log.d(TAG, "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANS);

        onCreate(db);
    }

    public void addPlan(Plan plan) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PlanColumn.KEY_PHYSICIAN_ID.toString(), plan.getPhysician().getPhysicianId());
        values.put(PlanColumn.KEY_LOCATION.toString(), plan.getLocation());
        values.put(PlanColumn.KEY_NOTE.toString(), plan.getNote());
        values.put(PlanColumn.KEY_TIME.toString(), plan.getTime().format2445());

        db.insert(TABLE_PLANS, null, values);
        db.close();
    }

    public Plan getPlan(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor planCursor = db.query(TABLE_PLANS, new String[]{PlanColumn.KEY_PLAN_ID.toString(),
                PlanColumn.KEY_PHYSICIAN_ID.toString(), PlanColumn.KEY_LOCATION.toString(), PlanColumn.KEY_NOTE.toString(), PlanColumn.KEY_TIME.toString()},
                PlanColumn.KEY_PLAN_ID.toString() + "=?", new String[]{String.valueOf(id)}, null, null,
                null, null);


        if (planCursor != null) {
            planCursor.moveToFirst();
        }

        int physicianId = Integer.parseInt(planCursor.getString(PlanColumn.KEY_PHYSICIAN_ID.ordinal()));
        Physician physician = getPhysician(physicianId);

        Time timestamp = parseStringToTime(planCursor.getString(PlanColumn.KEY_TIME.ordinal()));
        Log.d(TAG, "time from db:" + timestamp.format("%c"));

        Plan plan = new Plan(Integer.parseInt(planCursor
                .getString(PlanColumn.KEY_PLAN_ID.ordinal())), physician, planCursor.getString(PlanColumn.KEY_LOCATION.ordinal()),
                planCursor.getString(PlanColumn.KEY_NOTE.ordinal()), timestamp);

        planCursor.close();
        db.close();
        return plan;
    }

    public List<Plan> getAllPlans() {

        List<Plan> planList = new ArrayList<Plan>();

        String plansSelectQuery = "SELECT * FROM " + TABLE_PLANS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor planCursor = db.rawQuery(plansSelectQuery, null);

        // looping through all rows and adding to list
        if (planCursor.moveToFirst()) {
            do {
                int physicianId = Integer.parseInt(planCursor.getString(PlanColumn.KEY_PHYSICIAN_ID.ordinal()));
                Physician physician = getPhysician(physicianId);
                Time timestamp = parseStringToTime(planCursor.getString(PlanColumn.KEY_TIME.ordinal()));
                Log.d(TAG, "time from db:" + timestamp.format("%c"));
                Plan plan = new Plan(Integer.parseInt(planCursor
                        .getString(PlanColumn.KEY_PLAN_ID.ordinal())), physician, planCursor.getString(PlanColumn.KEY_LOCATION.ordinal()),
                        planCursor.getString(PlanColumn.KEY_NOTE.ordinal()), timestamp);
                planList.add(plan);
            } while (planCursor.moveToNext());
        }

        planCursor.close();
        db.close();

        return planList;
    }

    public int getPlansCount() {

        String countQuery = "SELECT  * FROM " + TABLE_PLANS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updatePlan(Plan plan) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PlanColumn.KEY_PHYSICIAN_ID.toString(), plan.getPhysician().getPhysicianId());
        values.put(PlanColumn.KEY_LOCATION.toString(), plan.getLocation());
        values.put(PlanColumn.KEY_NOTE.toString(), plan.getNote());
        values.put(PlanColumn.KEY_TIME.toString(), plan.getTime().format("%c"));

        return db.update(TABLE_PLANS, values, PlanColumn.KEY_PLAN_ID.toString() + " = ?",
                new String[]{String.valueOf(plan.getID())});
    }

    public void deletePlan(Plan plan) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLANS, PlanColumn.KEY_PLAN_ID.toString() + " = ?",
                new String[]{String.valueOf(plan.getID())});
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

    private enum PlanColumn {
        KEY_PLAN_ID("plan_id"), KEY_PHYSICIAN_ID(KEY_PHYSICIAN_ID_STRING), KEY_LOCATION("plan_location"), KEY_NOTE("plane_note"), KEY_TIME("plan_time");
        private String columnName;

        private PlanColumn(String columnName) {
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