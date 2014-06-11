package com.async;

import java.io.IOException;

import org.json.JSONException;

import android.os.AsyncTask;

import com.classes.DataHolder;
import com.http.HttpHandler;
import com.http.JSONParser;
import com.myfamily.AddFamilyActivity;
/**
 * Klasa wywolujaca zdarzenie asynchroniczne odpowiadajace za dodanie rodziny do serwera
 * @author lagvna
 *
 */
public class AddFamily extends AsyncTask<Void, Void, Void> {

	private AddFamilyActivity callingActivity;
	private String responseText = null;
	private String dataArray[];

	public AddFamily(String familyName, AddFamilyActivity callingActivity) {
		this.callingActivity = callingActivity;
		dataArray = new String[1];
		dataArray[0] = familyName;
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
					"http://malinowepi.no-ip.org/add_family.php", dataArray)
					.postDataAddFamily();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	protected void onPostExecute(Void result) {
		callingActivity.hideProgressDial();
		callingActivity.showToast(responseText);
		callingActivity.showToast(DataHolder.getInstance().getSession());
		JSONParser jp = new JSONParser(responseText);
		try {
			String loginResult = jp.getAddFamilyResult();
			String tempArr[] = loginResult.split(":");
			if (tempArr[0].equals("0")) {
				callingActivity.showToast("Cos poszlo zle: " + tempArr[1]);
			} else {
				callingActivity.familyCreated();
			}
			
			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
