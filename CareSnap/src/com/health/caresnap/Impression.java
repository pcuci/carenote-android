package com.health.caresnap;

public class Impression {

	// private variables
	private int _id;
	private String _name;
	private String _specialty;
	private String _location;
	private String _note;
	private String _time;

	// Empty constructor
	public Impression() {
	}

	// constructor
	public Impression(int _id, String _name, String _specialty,
			String _location, String _note, String _time) {
		this._id = _id;
		this._name = _name;
		this._specialty = _specialty;
		this._location = _location;
		this._note = _note;
		this._time = _time;

	}

	// constructor
	public Impression(String name, String _specialty, String _location,
			String _note, String _time) {
		this._name = name;
		this._specialty = _specialty;
		this._location = _location;
		this._note = _note;
		this._time = _time;
	}

	// getting ID
	public int getID() {
		return this._id;
	}

	// setting id
	public void setID(int id) {
		this._id = id;
	}

	// getting name
	public String getName() {
		return this._name;
	}

	// setting name
	public void setName(String name) {
		this._name = name;
	}

	public String getSpecialty() {
		return _specialty;
	}

	// setting name
	public void setSpecialty(String specialty) {
		this._specialty = specialty;
	}

	public String getLocation() {
		return _location;
	}

	public void setLocation(String _location) {
		this._location = _location;
	}

	public String getNote() {
		return _note;
	}

	public void setNote(String _note) {
		this._note = _note;
	}

	public String getTime() {
		return _time;
	}

	public void setTime(String _time) {
		this._time = _time;
	}

}
