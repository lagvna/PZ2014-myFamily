package com.http;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.classes.Family;
import com.classes.MyEvent;

public class JSONParser {

	private String inputStream;
	private JSONArray users = null;
	private ArrayList<Family> familyList = new ArrayList<Family>();
	private ArrayList<ArrayList> resultArray = new ArrayList<ArrayList>();
	private ArrayList<String> stringArray = new ArrayList<String>();
	public JSONParser(String inputStream) {

		this.inputStream = inputStream;
	}

	public ArrayList getLoginResult() throws JSONException {

		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");
		int n = jSonArray.length();

		JSONObject jSonObject = jSonArray.getJSONObject(0);
		String errorCode = jSonObject.getString("success");
		String message = jSonObject.getString("message");

		JSONArray familys;
		String session;

		try {
			if (!errorCode.equals("0")) { // brak sukcesu
				String hasFamily = jSonObject.getString("has_family");

				if (hasFamily.equals("1")) {
					familys = (JSONArray) jSonObject.get("familys");
					for (int i = 0; i < familys.length(); i++) {
						JSONObject object = familys.getJSONObject(i);
						familyList.add(new Family(object.getInt("id"), object
								.getString("family_name"), object
								.getInt("role")));
					}
					session = jSonObject.getString("sessionId");
					resultArray.add(familyList);
					stringArray.add(new String(errorCode));
					stringArray.add(new String(message));
					stringArray.add(new String(session));
					resultArray.add(stringArray);
					return resultArray;
				} else {
					session = jSonObject.getString("sessionId");
					stringArray.add(new String(errorCode));
					stringArray.add(new String(message));
					stringArray.add(new String(session));
					resultArray.clear();
					resultArray.add(stringArray);
				}
			} else {
				resultArray.clear();
				return resultArray;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		stringArray.add(new String(errorCode));
		stringArray.add(new String(message));
		resultArray.clear();
		resultArray.add(stringArray);
		return resultArray;
	}

	public String getAddUserResult() throws JSONException {

		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");

		JSONObject jSonObject = jSonArray.getJSONObject(0);
		String errorCode = jSonObject.getString("success");
		String message = jSonObject.getString("message");

		return errorCode + ":" + message;
	}
	
	

	public String getAddFamilyResult() throws JSONException {

		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");

		JSONObject jSonObject = jSonArray.getJSONObject(0);
		String errorCode = jSonObject.getString("success");
		String message = jSonObject.getString("message");

		return errorCode + ":" + message;
	}
	
	public ArrayList getEventsResult() throws JSONException {
		ArrayList<MyEvent> eventsList = new ArrayList<MyEvent>();
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");
		int n = jSonArray.length();

		JSONObject jSonObject = jSonArray.getJSONObject(0);
		String errorCode = jSonObject.getString("success");
		String message = jSonObject.getString("message");

		JSONArray tasks;

		try {
			if (!errorCode.equals("0")) { // sukces

				tasks = (JSONArray) jSonObject.get("events");
				for (int i = 0; i < tasks.length(); i++) {
					JSONObject object = tasks.getJSONObject(i);
					eventsList.add(new MyEvent(object.getString("name"), object.
							getString("note"), object.
							getString("date"), object.
							getString("Id"),object.
							getString("color")));
				}
				resultArray.add(eventsList);
				stringArray.add(new String(errorCode));
				stringArray.add(new String(message));
				resultArray.add(stringArray);
				return resultArray;

			} else {
				stringArray.add(new String(errorCode));
				stringArray.add(new String(message));
				resultArray.clear();
				resultArray.add(stringArray);
				return resultArray;
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return resultArray;
	}
}
