package com.async;

import java.io.IOException;

import org.json.JSONException;

import android.os.AsyncTask;

import com.http.HttpHandler;
import com.http.JSONParser;
import com.myfamily.UserRegisterActivity;

public class UserRegister extends AsyncTask<Void, Void, Void> {

	private UserRegisterActivity callingActivity;
	private String responseText;
	private String[] dataArray;

	public UserRegister(UserRegisterActivity callingActivity,String dataArray[]) {
		this.callingActivity = callingActivity;
		this.dataArray = dataArray;
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
					"http://malinowepi.no-ip.org/register.php",
					dataArray).postDataUserRegister();
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
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
