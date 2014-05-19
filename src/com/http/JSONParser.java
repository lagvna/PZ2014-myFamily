package com.http;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.classes.Family;
import com.classes.JSonReader;
import com.classes.JSonWriter;
import com.classes.MyEvent;
import com.classes.Prize;
import com.classes.User;

public class JSONParser {

	private String inputStream;
	private JSONArray users = null;
	private ArrayList<Family> familyList = new ArrayList<Family>();
	private ArrayList<ArrayList> resultArray = new ArrayList<ArrayList>();
	private ArrayList<String> stringArray = new ArrayList<String>();
	private ArrayList<Prize> allPrizesList = new ArrayList<Prize>();
	private ArrayList<Prize> gainedPrizesList = new ArrayList<Prize>();
	private ArrayList<User> userList = new ArrayList<User>();

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

	public String getAddEventResult() throws JSONException {

		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");

		JSONObject jSonObject = jSonArray.getJSONObject(0);
		String errorCode = jSonObject.getString("success");
		String message = jSonObject.getString("message");
		JSONArray event = (JSONArray) jSonObject.get("created_event");
		JSONObject object = event.getJSONObject(0);
		String id = object.getString("Id");

		return errorCode + ":" + message + ":" + id;
	}

	public ArrayList getEventsResult(Context context) throws JSONException {
		ArrayList<MyEvent> eventsList = new ArrayList<MyEvent>();
		ArrayList<String> deletedEventsId = new ArrayList<String>();
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");
		int n = jSonArray.length();

		JSONObject jSonObject = jSonArray.getJSONObject(0);
		String errorCode = jSonObject.getString("success");
		String message = jSonObject.getString("message");
		String newEventCode;
		String deletedEventCode;

		JSONArray events;
		JSONArray deletedEvents;
		JSONObject object;

		try {
			if (!errorCode.equals("0")) { // sukces

				newEventCode = jSonObject.getString("new_events_success");
				deletedEventCode = jSonObject
						.getString("deleted_events_success");

				if (!newEventCode.equals("0")) { // jesli sa nowe wydarzenia
													// doddania

					events = (JSONArray) jSonObject.get("new_events");
					for (int i = 0; i < events.length(); i++) {
						object = events.getJSONObject(i);
						eventsList.add(new MyEvent(object.getString("name"),
								object.getString("note"), object
										.getString("date"), object
										.getString("Id"), object
										.getString("color")));
					}

					resultArray.add(eventsList);
				} else {

					String oldJson = JSonReader.getInstance().readFile(
							"events", context);
					jSonArray = new JSONArray("[" + oldJson.substring(19) + "]");
					jSonObject = jSonArray.getJSONObject(0);
					events = (JSONArray) jSonObject.get("events");

					for (int i = 0; i < events.length(); i++) {
						object = events.getJSONObject(i);
						eventsList.add(new MyEvent(object.getString("name"),
								object.getString("note"), object
										.getString("date"), object
										.getString("Id"), object
										.getString("color")));
					}
					resultArray.add(eventsList);
				}

				if (!deletedEventCode.equals("0")) { // jesli sa jakies zadanie
														// do usuniecia z pliku

					deletedEvents = (JSONArray) jSonObject
							.get("deleted_events");
					for (int i = 0; i < deletedEvents.length(); i++) {
						object = deletedEvents.getJSONObject(i);
						deletedEventsId.add(object.getString("Id"));
					}
					resultArray.add(deletedEventsId);
				}

				// makeEventsJSon(eventsList);
				stringArray.add(new String(errorCode));
				stringArray.add(new String(message));
				stringArray.add(newEventCode);
				stringArray.add(deletedEventCode);
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

	public void makeEventsJSon(ArrayList<MyEvent> eventsList, Context context,
			int type) throws JSONException {

		String oldJson = JSonReader.getInstance().readFile("events", context);
		JSONArray jSonArray;
		if (type == 0) {
			jSonArray = new JSONArray("[" + "{\"events\":[]}" + "]");
		} else {
			jSonArray = new JSONArray("[" + oldJson.substring(19) + "]");
		}

		JSONObject jSonObject = jSonArray.getJSONObject(0);
		JSONArray events = (JSONArray) jSonObject.get("events");

		MyEvent myEvent;
		JSONObject JSonEvent;
		// JSONArray jsonArray = new JSONArray();
		JSONObject eventsObj = new JSONObject();

		for (int i = 0; i < eventsList.size(); i++) {
			myEvent = eventsList.get(i);
			JSonEvent = new JSONObject();
			try {
				JSonEvent.put("name", myEvent.getName());
				JSonEvent.put("note", myEvent.getNote());
				JSonEvent.put("date", myEvent.getDate());
				JSonEvent.put("Id", myEvent.getId());
				JSonEvent.put("color", myEvent.getColor());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			events.put(JSonEvent);
		}

		try {
			eventsObj.put("events", events);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateandTime = sdf.format(new Date());

		String jsonStr = eventsObj.toString();
		// System.out.println(currentDateandTime + jsonStr);
		JSonWriter.getInstance().appendToFile("events",
				currentDateandTime + jsonStr, 0, context);
		System.out
				.println(JSonReader.getInstance().readFile("events", context));

	}
	
	public void makePrizesJSon(ArrayList<Prize> prizesList, Context context,
			int type) throws JSONException {

		String oldJson = JSonReader.getInstance().readFile("prizes", context);
		JSONArray jSonArray;
		if (type == 0) { // towrzenie nowej listy
			jSonArray = new JSONArray("[" + "{\"prizes\":[]}" + "]");
		} else { // dopisywanie do instniejacych 
			jSonArray = new JSONArray("[" + oldJson.substring(19) + "]");
		}

		JSONObject jSonObject = jSonArray.getJSONObject(0);
		JSONArray events = (JSONArray) jSonObject.get("prizes");

		Prize myPrize;
		JSONObject JSonEvent;
		// JSONArray jsonArray = new JSONArray();
		JSONObject eventsObj = new JSONObject();

		for (int i = 0; i < prizesList.size(); i++) {
			myPrize = prizesList.get(i);
			JSonEvent = new JSONObject();
			try {
				JSonEvent.put("Id", myPrize.getId());
				JSonEvent.put("name", myPrize.getName());
				JSonEvent.put("user", myPrize.getWhos());
				JSonEvent.put("receiving_date", myPrize.getGained_date());
				JSonEvent.put("category", myPrize.getCategory());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			events.put(JSonEvent);
		}

		try {
			eventsObj.put("prizes", events);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateandTime = sdf.format(new Date());

		String jsonStr = eventsObj.toString();
		// System.out.println(currentDateandTime + jsonStr);
		JSonWriter.getInstance().appendToFile("prizes",
				currentDateandTime + jsonStr, 0, context);
		System.out
				.println(JSonReader.getInstance().readFile("prizes", context));

	}
	

	public void removeEventFromJson(ArrayList<String> deletedEvents,
			Context context) throws JSONException {

		String oldJson = JSonReader.getInstance().readFile("events", context);
		JSONArray jSonArray = new JSONArray("[" + oldJson.substring(19) + "]");
		JSONObject jSonObject = jSonArray.getJSONObject(0);
		JSONArray events = (JSONArray) jSonObject.get("events");
		JSONObject object;
		ArrayList<MyEvent> eventsList = new ArrayList<MyEvent>();

		for (int i = 0; i < events.length(); i++) {
			object = events.getJSONObject(i);
			eventsList.add(new MyEvent(object.getString("name"), object
					.getString("note"), object.getString("date"), object
					.getString("Id"), object.getString("color")));
		}
		MyEvent myEvent;
		for (int i = 0; i < eventsList.size(); i++) {
			myEvent = eventsList.get(i);
			if (deletedEvents.contains(myEvent.getId())) {
				eventsList.remove(i);
			}
		}

		makeEventsJSon(eventsList, context, 0);
	}
	
	
	public String getAddPrizeResult() throws JSONException {

		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");

		JSONObject jSonObject = jSonArray.getJSONObject(0);
		String errorCode = jSonObject.getString("success");
		String message = jSonObject.getString("message");
		String id = jSonObject.getString("Id");

		return errorCode + ":" + message + ":" + id;
	}

	public ArrayList getGainedPrizesResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");
		int n = jSonArray.length();

		JSONObject jSonObject = jSonArray.getJSONObject(0);
		//String points = jSonObject.getString("points");
		String points = "-1";
		String errorCode = jSonObject.getString("success");
		String message = jSonObject.getString("message");
		
		JSONArray prizes;

		try {
			if (!errorCode.equals("0")) { // sukces

				prizes = (JSONArray) jSonObject.get("prizes");
				for (int i = 0; i < prizes.length(); i++) {
					JSONObject object = prizes.getJSONObject(i);
					gainedPrizesList.add(new Prize(
							
							object.getString("name"), 
							object.getString("Id"),
							object.getString("user"),
							object.getString("receiving_date"),
							object.getString("category")));
					points = object.getString("points");
				}
				if (prizes.length()==0 ) {
					points = jSonObject.getString("points");
				}
				resultArray.add(gainedPrizesList);
				stringArray.add(new String(errorCode));
				stringArray.add(new String(message));
				stringArray.add(new String(points));
				resultArray.add(stringArray);
				return resultArray;
			} else {
				stringArray.add(new String(errorCode));
				stringArray.add(new String(message));
				stringArray.add(new String(points));
				resultArray.clear();
				resultArray.add(stringArray);
				return resultArray;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return resultArray;
	}
	
	public ArrayList<User> getUsersResult() throws JSONException {

		JSONObject jsonObj = new JSONObject(inputStream);
		users = jsonObj.getJSONArray("users");

		for (int i = 0; i < users.length(); i++) {
			JSONObject c = users.getJSONObject(i);

			String name = c.getString("name");
			String role = c.getString("role");

			// tmp hashmap for single contact
			userList.add(new User(name, role));
		}

		return userList;
	}
	
	

}