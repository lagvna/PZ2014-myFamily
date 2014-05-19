package com.classes;

import java.io.Serializable;

public class Expense implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id = -1;
	private String name = null;
	private String note = null;
	private String owner = null;
	private String price = null;
	private String date = null;

	public Expense() {

	}

	public Expense(int id, String name, String note, String owner,
			String price, String date) {
		this.setId(id);
		this.setName(name);
		this.setNote(note);
		this.setOwner(owner);
		this.setPrice(price);
		this.setDate(date);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
