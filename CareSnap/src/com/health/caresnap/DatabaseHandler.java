package com.health.caresnap;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "snapCare";

	// Contacts table name
	private static final String TABLE_IMPRESSIONS = "impressions";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_SPECIALTY = "specialty";
	private static final String KEY_LOCATION = "location";
	private static final String KEY_NOTE = "note";

	private String TAG = "DATABASE";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_IMPRESSIONS
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_SPECIALTY + " TEXT," + KEY_LOCATION + " TEXT," + KEY_NOTE
				+ " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
		Log.d(TAG, "Database created");
		
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMPRESSIONS);

		// Create tables again
		onCreate(db);
	}

	// Adding new contact
	public void addContact(Impression impression) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, impression.getName()); // Contact Name
		values.put(KEY_SPECIALTY, impression.getSpecialty()); // Contact Phone
																// Number

		// Inserting Row
		db.insert(TABLE_IMPRESSIONS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	public Impression getImpression(int id) {

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_IMPRESSIONS, new String[] { KEY_ID,
				KEY_NAME, KEY_SPECIALTY, KEY_LOCATION, KEY_NOTE }, KEY_ID
				+ "=?", new String[] { String.valueOf(id) }, null, null, null,
				null);
		if (cursor != null)
			cursor.moveToFirst();

		Impression impression = new Impression(Integer.parseInt(cursor
				.getString(0)), cursor.getString(1), cursor.getString(2),
				cursor.getString(3), cursor.getString(4));
		// return contact
		return impression;
	}

	// Getting All Contacts
	public List<Impression> getAllImpressions() {

		List<Impression> impressionList = new ArrayList<Impression>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_IMPRESSIONS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Impression impression = new Impression();
				impression.setID(Integer.parseInt(cursor.getString(0)));
				impression.setName(cursor.getString(1));
				impression.setSpecialty(cursor.getString(2));
				impression.setLocation(cursor.getString(3));
				impression.setNote(cursor.getString(4));
				// Adding contact to list
				impressionList.add(impression);
			} while (cursor.moveToNext());
		}

		// return contact list
		return impressionList;
	}

	// Getting contacts Count
	public int getImpressionsCount() {

		String countQuery = "SELECT  * FROM " + TABLE_IMPRESSIONS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	// Updating single contact
	public int updateImpression(Impression impression) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, impression.getName());
		values.put(KEY_SPECIALTY, impression.getSpecialty());
		values.put(KEY_LOCATION, impression.getName());
		values.put(KEY_NOTE, impression.getName());

		// updating row
		return db.update(TABLE_IMPRESSIONS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(impression.getID()) });
	}

	// Deleting single contact
	public void deleteContact(Impression impression) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_IMPRESSIONS, KEY_ID + " = ?",
				new String[] { String.valueOf(impression.getID()) });
		db.close();
	}
}