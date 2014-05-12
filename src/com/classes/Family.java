package com.classes;

import java.io.Serializable;

public class Family implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String familyName = "";
	private int role = -1;

	public Family(int id, String familyName, int role) {

		this.id = id;
		this.familyName = familyName;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

}