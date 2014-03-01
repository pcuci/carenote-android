package com.health.caresnap;

public class Impression {

	// private variables
	int _id;
	String _name;
	String _specialty;
	String _location;
	String _note;

	// Empty constructor
	public Impression() {
	}

	// constructor
	public Impression(int id, String name, String _specialty, String _location,
			String _note) {
		this._id = id;
		this._name = name;
		this._specialty = _specialty;
		this._location = _location;
		this._note = _note;

	}

	// constructor
	public Impression(String name, String _specialty, String _location,
			String _note) {
		this._name = name;
		this._specialty = _specialty;
		this._location = _location;
		this._note = _note;
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
		return _location;
	}

	public void setNote(String _location) {
		this._location = _location;
	}

}
