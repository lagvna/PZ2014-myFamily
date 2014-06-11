package com.async;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.os.AsyncTask;

import com.classes.User;
import com.http.HttpHandler;
import com.http.JSONParser;
import com.myfamily.ExpenseActivity;

/**
 * Klasa wywolujaca zdarzenie asynchroniczne odpowiadajace za pobranie nazw uzytkownikow z bazy serwera dla celow przegladania wydatkow
 * @author lagvna
 *
 */
public class GetUsersForExpenses extends AsyncTask<Void, Void, Void> {

	private ExpenseActivity callingActivity;
	private String responseText = null;

	public GetUsersForExpenses(ExpenseActivity callingActivity) {
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
					"http://malinowepi.no-ip.org/get_family_users.php", null)
					.postDataGetUsers();
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
			ArrayList<User> usersResult = jp.getUsersResult();
			if (!usersResult.isEmpty()) {
				callingActivity.showToast(responseText);
				callingActivity.setUsers(usersResult);
			}
		} catch (JSONException e) {
			callingActivity.showToast(e.toString());
		}
	}
}
