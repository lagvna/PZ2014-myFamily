package com.async;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.os.AsyncTask;
import android.widget.Toast;

import com.classes.Note;
import com.http.HttpHandler;
import com.http.JSONParser2;
import com.myfamily.NotesActivity;

public class GetNotes extends AsyncTask<Void, Void, Void> {

	private NotesActivity callingActivity;
	private String responseText;

	public GetNotes(NotesActivity callingActivity) {
		this.callingActivity = callingActivity;
	}

	@Override
	protected void onPreExecute() {
		callingActivity.showProgressDial();
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		try {
			responseText = new HttpHandler(
					"http://malinowepi.no-ip.org/get_notes.php", null)
					.postDataGetNotes();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		callingActivity.hideProgressDial();

		JSONParser2 jp = new JSONParser2(responseText);

		try {
			ArrayList<Note> notes = jp.getGetNotesResult();

			if (notes == null) {
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity, "Coś poszło nie tak!",
								Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				callingActivity.showToast(responseText);
				callingActivity.assignNotes(notes);
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity,
								"Pobrano wszystkie notatki", Toast.LENGTH_SHORT)
								.show();
					}
				});
			}
		} catch (JSONException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
