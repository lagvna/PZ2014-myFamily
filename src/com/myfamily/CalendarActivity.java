package com.myfamily;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.classes.JSonReader;
import com.classes.MyEvent;
import com.http.HttpHandler;
import com.http.JSONParser;
import com.tyczj.extendedcalendarview.CalendarProvider;
import com.tyczj.extendedcalendarview.Day;
import com.tyczj.extendedcalendarview.Event;
import com.tyczj.extendedcalendarview.ExtendedCalendarView;
import com.tyczj.extendedcalendarview.ExtendedCalendarView.OnDayClickListener;

public class CalendarActivity extends Activity {

	private Context context;
	private Calendar tempCal = Calendar.getInstance();
	private String dayy = "";
	private int month = 0;
	private String year = "";
	private ProgressDialog progressDialog;
	private int color;
	private int tempDay;
	private Intent startIntent;
	private ArrayList<MyEvent> events;
	private static ExtendedCalendarView calendar;
	String eventNeme;
	String eventNote;
	ArrayList<MyEvent> myDayEventList = new ArrayList<MyEvent>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		removeAllEvents();
		context = this;

		new GetEvents().execute();
		// new GetEventsFromFile().execute();
	}

	public static void repaintCalendar() {
		calendar.refreshCalendar();
		calendar.refreshDrawableState();
	}

	private void createCalendar() {

		calendar = (ExtendedCalendarView) findViewById(R.id.calendar);
		calendar.refreshCalendar();
		calendar.refreshDrawableState();

		calendar.setOnDayClickListener(new OnDayClickListener() {

			@Override
			public void onDayClicked(AdapterView<?> adapter, View view,
					int position, long id, Day day) {
				tempCal.set(day.getYear(), day.getMonth(), day.getDay());
				dayy = Integer.toString(day.getDay());
				month = day.getMonth() + 1;
				year = Integer.toString(day.getYear());
				tempDay = day.getStartDay();
				// addEvent(tempCal, "asdasd ","asdasd", 0);
				myDayEventList.clear();
				int n = day.getEvents().size();
				for (int i = 0; i < n; i++) {
					Event e = day.getEvents().get(i);
					myDayEventList.add(new MyEvent(e.getTitle(), e
							.getDescription(), e.getStartDate("dd:MM:yyyy"), e
							.getLocation(), "0"));
				}
				calendar.refreshDrawableState();

			}
		});
	}

	private void removeEvent(String id) {

		getContentResolver().delete(CalendarProvider.CONTENT_URI,
				CalendarProvider.LOCATION + "=?", new String[] { id });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.addEvent:
			// showPopUp(getStringDate(dayy, month, year));
			customPopUp(getStringDate(dayy, month, year));
			return true;
		case R.id.showEvent:
			Bundle args = new Bundle();
			Intent eventView = new Intent(this, EventViewActivity.class);
			eventView.putExtra("events", myDayEventList);
			startActivity(eventView);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void customPopUp(String data) {

		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_custom);
		dialog.setTitle(data);

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.dialogTextView);
		text.setText("Opis wydarzenia:");

		Spinner colors = (Spinner) dialog.findViewById(R.id.dialogSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.colors, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		colors.setAdapter(adapter);

		colors.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				color = arg2;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		final EditText eventNameEditText = (EditText) dialog
				.findViewById(R.id.dialogEventNameEditText);
		final EditText eventNoteEditText = (EditText) dialog
				.findViewById(R.id.dialogEventNoteEditText);

		Button dialogOkButton = (Button) dialog
				.findViewById(R.id.dialogOkButton);
		// if button is clicked, close the custom dialog
		dialogOkButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				eventNeme = eventNameEditText.getText().toString();
				eventNote = eventNoteEditText.getText().toString();
				new AddEvent(eventNeme, eventNote, color).execute();
				// addEvent(eventNeme,
				// eventNote,color,Integer.parseInt(year),month-1,Integer.parseInt(dayy),"",0);

				dialog.dismiss();
			}
		});

		Button dialogCancelButton = (Button) dialog
				.findViewById(R.id.dialogCancelButton);
		// if button is clicked, close the custom dialog
		dialogCancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	public void showToast(String responseText) {

		Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG)
				.show();
	}

	public void addEvent(String name, String note, int color, int year,
			int month, int day, String id, int type) {

		ContentValues values = new ContentValues();

		if (color == 0) {
			values.put(CalendarProvider.COLOR, Event.COLOR_RED);
		} else if (color == 1) {
			values.put(CalendarProvider.COLOR, Event.COLOR_BLUE);
		} else if (color == 2) {
			values.put(CalendarProvider.COLOR, Event.COLOR_GREEN);
		} else if (color == 3) {
			values.put(CalendarProvider.COLOR, Event.COLOR_PURPLE);
		} else if (color == 4) {
			values.put(CalendarProvider.COLOR, Event.COLOR_YELLOW);
		}

		Calendar cal = Calendar.getInstance();

		values.put(CalendarProvider.DESCRIPTION, note);
		values.put(CalendarProvider.LOCATION, id);
		values.put(CalendarProvider.EVENT, name);
		TimeZone tz = TimeZone.getDefault();

		cal.set(year, month, day);
		int startDayJulian = Time.getJulianDay(cal.getTimeInMillis(),
				TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal
						.getTimeInMillis())));
		values.put(CalendarProvider.START, cal.getTimeInMillis());
		values.put(CalendarProvider.START_DAY, startDayJulian);

		// cal.set(2014,4,15);
		int endDayJulian = Time.getJulianDay(cal.getTimeInMillis(),
				TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal
						.getTimeInMillis())));

		values.put(CalendarProvider.END, cal.getTimeInMillis());
		values.put(CalendarProvider.END_DAY, endDayJulian);

		Uri uri = getContentResolver().insert(CalendarProvider.CONTENT_URI,
				values);
		if (type == 1) {
			//showToast("dodalem do kle");
			calendar.refreshCalendar();
			calendar.refreshDrawableState();
		}
	}

	private void removeAllEvents() {
		getContentResolver().delete(CalendarProvider.CONTENT_URI,
				CalendarProvider.COLOR + "!=?", new String[] { "6" });
	}

	private void recreateActivity() {
		startActivity(startIntent);
		finish();
	}

	private String getStringDate(String day, int month, String year) {

		String months[] = { "stycznia", "lutego", "marca", "kwietnia", "maja",
				"czerwca", "lipca", "sierpnia", "września", "października",
				"listopada", "grudnia" };
		return day + " " + months[month - 1] + " " + year;
	}

	private String getWellFormatedStringDate(String day, int month, String year) {

		String wellFormatedStringDate = "";
		wellFormatedStringDate += year;

		if (month < 9) {
			wellFormatedStringDate += "." + 0 + month;
		} else {
			wellFormatedStringDate += "." + month;
		}

		if (day.length() == 1) {
			wellFormatedStringDate += "." + 0 + day;
		} else {
			wellFormatedStringDate += "." + day;
		}

		return wellFormatedStringDate;
	}

	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Sprawdzanie danych");
		progressDialog.show();
	}

	public void hideProgressDial() {
		progressDialog.hide();
	}

	public class AddEvent extends AsyncTask<Void, Void, Void> {

		private String responseText = null;
		private String dataArray[];

		public AddEvent(String eventName, String eventNote, int color) {
			dataArray = new String[4];
			dataArray[0] = eventName;
			dataArray[1] = eventNote;
			dataArray[2] = getWellFormatedStringDate(dayy, month, year);
			dataArray[3] = Integer.toString(color);
		}

		@Override
		protected void onPreExecute() {
			showProgressDial();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				responseText = new HttpHandler(
						"http://malinowepi.no-ip.org/add_event.php", dataArray)
						.postDataAddEvent();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			hideProgressDial();
			//showToast(responseText);
			JSONParser jp = new JSONParser(responseText);

			try {
				String addEventResult = jp.getAddEventResult();
				if (addEventResult.split(":")[0].equals("1")) {
					showToast(addEventResult.split(":")[1]);
					addEvent(eventNeme, eventNote, color,
							Integer.parseInt(year), month - 1,
							Integer.parseInt(dayy),
							addEventResult.split(":")[2], 1);
				} else {
					showToast(addEventResult.split(":")[1]);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public class GetEvents extends AsyncTask<Void, Void, Void> {

		private String responseText = null;
		private String dataArray[];
		ArrayList<String> deletedEventsId = new ArrayList<String>();

		public GetEvents() {
			dataArray = new String[1];
			String str = JSonReader.getInstance().readFile("events", context);
			String temp = str.substring(0, 19);
			if (temp.equals("0000-00-00 00:00:00")) {
				dataArray[0] = "0";
			} else {
				dataArray[0] = temp;
			}
		}

		@Override
		protected void onPreExecute() {
			showProgressDial();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				responseText = new HttpHandler(
						"http://malinowepi.no-ip.org/get_events.php", dataArray)
						.postDataGetEvents();
			} catch (IOException e) {
				e.printStackTrace();
				showToast(e.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			hideProgressDial();

			JSONParser jp;
			jp = new JSONParser(responseText);

			try {
				ArrayList eventResult = jp.getEventsResult(context);
				MyEvent tempEvent;
				if (eventResult.size() == 1) {
					if (((ArrayList<String>) eventResult.get(0)).get(0).equals(
							0)) {
						showToast("Coś poszło nie tak:"
								+ ((ArrayList<String>) eventResult.get(0))
										.get(1));
					}
				} else if (eventResult.size() == 2) {
					events = (ArrayList<MyEvent>) eventResult.get(0);
					for (int i = 0; i < events.size(); i++) {
						tempEvent = ((MyEvent) events.get(i));
						addEvent(tempEvent.getName(), tempEvent.getNote(),
								tempEvent.getColor(), tempEvent.getYear(),
								tempEvent.getMonth() - 1, tempEvent.getDay(),
								tempEvent.getId(), 0);
					}

					if (((ArrayList<String>) eventResult.get(1)).get(2).equals(
							"1")) {
						jp.makeEventsJSon(events, context, 1);
					}

				} else if (eventResult.size() == 3) {
					events = (ArrayList<MyEvent>) eventResult.get(0);
					for (int i = 0; i < events.size(); i++) {
						tempEvent = ((MyEvent) events.get(i));
						addEvent(tempEvent.getName(), tempEvent.getNote(),
								tempEvent.getColor(), tempEvent.getYear(),
								tempEvent.getMonth() - 1, tempEvent.getDay(),
								tempEvent.getId(), 0);
					}

					deletedEventsId = (ArrayList<String>) eventResult.get(1);

					jp.removeEventFromJson(deletedEventsId, context);
					jp.makeEventsJSon(events, context, 1);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				showToast(e.toString());
			}
			createCalendar();

		}
	}

	public class GetEventsFromFile extends AsyncTask<Void, Void, Void> {

		private String responseText = null;
		private String dataArray[];

		public GetEventsFromFile() {
			dataArray = new String[1];
		}

		@Override
		protected void onPreExecute() {
			showProgressDial();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			responseText = JSonReader.getInstance().readFile("events", context);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			hideProgressDial();
			//showToast(responseText);
			JSONParser jp = new JSONParser(responseText);

			try {
				ArrayList eventResult = jp.getEventsResult(context);
				//showToast(eventResult.size() + "");
				MyEvent tempEvent;

				if (eventResult.size() == 1) {
					showToast("Coś poszło nie tak:" + eventResult.get(2));
				} else if (eventResult.size() == 2) {
					events = (ArrayList<MyEvent>) eventResult.get(0);
					for (int i = 0; i < events.size(); i++) {
						tempEvent = ((MyEvent) events.get(i));
						addEvent(tempEvent.getName(), tempEvent.getNote(),
								tempEvent.getColor(), tempEvent.getYear(),
								tempEvent.getMonth() - 1, tempEvent.getDay(),
								tempEvent.getId(), 0);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				showToast(e.toString());
			}
			createCalendar();

		}
	}

}