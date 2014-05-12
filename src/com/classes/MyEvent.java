package com.classes;

import java.io.Serializable;

public class MyEvent implements Serializable {

	String name = "";
	String organizer = "";
	String note = "";
	String date = "";
	String id = "";
	int color = 0;
	
	
	public MyEvent(String name, String note, String date,String id,String color) {
		this.name = name;
		this.note = note;
		this.date = date;
		this.id = id;
		this.color = Integer.parseInt(color);
	}
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrganizer() {
		return organizer;
	}
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public int getYear() {
		return Integer.parseInt(date.substring(0, 4));
	}
	
	public int getMonth() {
		return Integer.parseInt(date.substring(5, 7));
	}
	
	public int getDay() {
		return Integer.parseInt(date.substring(8, 10));
	}
	
	
}
