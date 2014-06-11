package com.async;

import java.io.IOException;

import org.json.JSONException;

import android.os.AsyncTask;

import com.http.HttpHandler;
import com.http.JSONParser2;

/**
 * Klasa wywolujaca zdarzenie asynchroniczne odpowiadajace za uniwersalny mechanizm usuwania danych z serwera
 * @author lagvna
 *
 */
public class RemoveSth extends AsyncTask<Void, Void, Void> {

	private String id = "";
	private String action = "";
	private String responseText;
	private String data[];

	public RemoveSth(String id, String action) {
		this.id = id;
		this.action = action;
	}

	@Override
	protected void onPreExecute() {
		// callingActivity.showProgressDial();
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... arg0) {

		try {
			responseText = new HttpHandler(
					"http://malinowepi.no-ip.org/remove.php", new String[] {
							action, id }).postDataRemove();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	protected void onPostExecute(Void result) {
		// callingActivity.hideProgressDial();

		JSONParser2 jp = new JSONParser2(responseText);
		try {
			data = jp.getRemoveSthResult();

			if (data[0].equals("1")) {
				// callingActivity.delete();
			}
			/*
			 * callingActivity.runOnUiThread(new Runnable() { public void run()
			 * { Toast.makeText(callingActivity, data[1],
			 * Toast.LENGTH_SHORT).show(); } });
			 */
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}