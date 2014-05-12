package com.classes;

public class DataHolder {

	private String sessionId = "";
	private int familyId = -1;
	private String login ="";
	
	

	private static final DataHolder holder = new DataHolder();
	

	public static DataHolder getInstance() {
		return holder;
	}
	
	public String getSession() {
		return sessionId;
	}

	public void setSession(String data) {
		this.sessionId = data;
	}

	public void setFamilyId(int familyId) {
		this.familyId = familyId;
	}

	public int getFamilyId() {
		return this.familyId;
	}
	
	public String getStringFamilyId() {
		return this.familyId+"";
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}