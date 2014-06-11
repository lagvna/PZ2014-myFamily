package com.async;

import java.io.IOException;

import org.json.JSONException;

import android.os.AsyncTask;
import android.widget.Toast;

import com.classes.Note;
import com.http.HttpHandler;
import com.http.JSONParser2;
import com.myfamily.NotesActivity;

/**
 * Klasa wywolujaca zdarzenie asynchroniczne odpowiadajace za dodanie notatki do serwera
 * @author lagvna
 *
 */
public class AddNote extends AsyncTask<Void, Void, Void> {

	private NotesActivity callingActivity;
	private String content;
	private String responseText;

	public AddNote(String content, NotesActivity callingActivity) {
		this.callingActivity = callingActivity;
		this.content = content;
	}

	@Override
	protected void onPreExecute() {
		callingActivity.showProgressDial();
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		try {
			String data[] = new String[1];
			data[0] = content;
			responseText = new HttpHandler(
					"http://malinowepi.no-ip.org/add_note.php", data)
					.postDataAddNote();
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
			Note note = jp.getAddNoteResult();

			if (note.getId() == -1) {
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity, "Coś poszło nie tak!",
								Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				callingActivity.setNewNote(note);
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity, "Notatka dodana",
								Toast.LENGTH_SHORT).show();
					}
				});
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
