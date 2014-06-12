package com.async;

import java.io.IOException;

import org.json.JSONException;

import android.os.AsyncTask;

import com.http.HttpHandler;
import com.http.JSONParser;
import com.myfamily.AddUserActivity;

/**
 * AsyncTask pozwalajacy na wywoalnie metody odpowiedzialnej
 * za pobieranie danych z serwera. Wywoluje metode, ktora 
 * pozwala dodawać użytkownikow do rodziny
 * @author kwachu
 *
 */
public class AddUser extends AsyncTask<Void, Void, Void> {

	private AddUserActivity callingActivity;
	private String responseText;
	private String[] dataArray;

	public AddUser(String userName, String role, AddUserActivity callingActivity) {
		this.callingActivity = callingActivity;
		dataArray = new String[2];
		dataArray[0] = userName;
		dataArray[1] = role;
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
					"http://malinowepi.no-ip.org/add_user_to_family.php",
					dataArray).postDataAddUserToFamily();
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
			String loginResult = jp.getAddUserResult();
			String tempArr[] = loginResult.split(":");
			if (tempArr[0].equals("0")) {
				callingActivity.showToast("Cos poszlo zle: " + tempArr[1]);
			} else {
				callingActivity.showToast("Użytkownik dodany");
				callingActivity.finish();
				callingActivity.userListShow();
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
