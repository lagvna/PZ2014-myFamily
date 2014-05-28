package com.classes;

import java.util.Arrays;
import java.util.List;

public class DataHolder {

	private String sessionId = "";
	private int familyId = -1;
	private String login = "";

	private static final DataHolder holder = new DataHolder();

	public static final int NUM_OF_COLUMNS = 3;

	// Gridview image padding
	public static final int GRID_PADDING = 8; // in dp

	// SD card image directory
	public static final String PHOTO_ALBUM = "Pictures/myFamily";

	// supported file formats
	public static final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg",
			"png");

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
		return this.familyId + "";
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}