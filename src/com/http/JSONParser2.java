package com.http;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.classes.Note;

public class JSONParser2 {

	private String inputStream;
	private ArrayList<Note> notesArray = null;

	public JSONParser2(String inputStream) {
		this.inputStream = inputStream;
	}

	public Note getAddNoteResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");
		Note note;

		JSONObject jo = jSonArray.getJSONObject(0);
		String errorCode = jo.getString("success");

		if (!errorCode.equals("0")) {
			JSONObject jo2 = jo.getJSONObject("created_note");

			int id = jo2.getInt("Id");
			String author = jo2.getString("owner");
			String content = jo2.getString("content");
			String date = jo2.getString("creation_date");

			note = new Note(id, author, content, date);
		} else {
			note = new Note();
		}

		return note;
	}

	public ArrayList<Note> getGetNotesResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");
		JSONObject jo = jSonArray.getJSONObject(0);
		String errorCode = jo.getString("success");

		if (!errorCode.equals("0")) {
			notesArray = new ArrayList<Note>();
			JSONArray notes = (JSONArray) jo.get("notes");
			for (int i = 0; i < notes.length(); i++) {
				JSONObject object = notes.getJSONObject(i);
				notesArray.add(new Note(object.getInt("Id"), object
						.getString("owner"), object.getString("content"),
						object.getString("creation_date")));
			}
		}

		return notesArray;
	}
	
	public String[] getRemoveSthResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");

		String[] data = new String[2];
		JSONObject jo = jSonArray.getJSONObject(0);
		data[0] = jo.getString("success");
		data[1] = jo.getString("message");
		
		return data;
	}
}
