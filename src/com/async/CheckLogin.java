package com.async;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.os.AsyncTask;

import com.classes.Family;
import com.http.HttpHandler;
import com.http.JSONParser;
import com.myfamily.LoginActivity;

/**
 * Klasa wywolujaca zdarzenie asynchroniczne odpowiadajace za sprawdzenie loginu i hasla uzytkownika z rekordami w bazie
 * @author lagvna
 *
 */
public class CheckLogin extends AsyncTask<Void, Void, Void> {

	private LoginActivity callingActivity;
	private String responseText;
	private String dataArray[] = new String[2];

	public CheckLogin(LoginActivity callingActivity, String login,
			String password) {
		this.callingActivity = callingActivity;
		this.dataArray[0] = login;
		this.dataArray[1] = password;
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
					"http://malinowepi.no-ip.org/login.php", dataArray)
					.postDataLogin();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	protected void onPostExecute(Void result) {
		callingActivity.hideProgressDial();
		JSONParser jp = new JSONParser(responseText);
		try {
			ArrayList loginResult = jp.getLoginResult();
			if (loginResult.size() == 0) { // blad
				callingActivity.showToast("Cos poszlo zle: "
						+ "Zły login lub hasło");
			} else if (loginResult.size() == 1) { // brak rodzin
				callingActivity
						.setResponseText(((String) ((ArrayList) loginResult
								.get(0)).get(1)));
				callingActivity.loginAccepted(null,(String) ((ArrayList) loginResult
						.get(0)).get(2));
			} else {
				// dodaj uzytkownikow na liste
				callingActivity
						.setResponseText(((String) ((ArrayList) loginResult
								.get(1)).get(1)));
				callingActivity.loginAccepted((ArrayList<Family>) loginResult
						.get(0),(String) ((ArrayList) loginResult
								.get(1)).get(2));
			}
			/*
			 * if (!tempArr[0].equals("0")) {
			 * callingActivity.setResponseText(tempArr[1]);
			 * callingActivity.userAccepted(); } else {
			 * callingActivity.showToast("Cos poszlo zle: " + tempArr[1]); }
			 */

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//callingActivity.showToast(responseText);

	}
}