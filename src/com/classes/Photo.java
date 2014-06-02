package com.classes;

public class Photo {
	private String name;
	private String date;
	private String Id;
	public Photo(String Id, String name, String date) {
		this.setName(name);
		this.setDate(date);
		this.setId(Id);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
}
