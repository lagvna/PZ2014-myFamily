package com.async;

import java.io.IOException;

import org.json.JSONException;

import android.os.AsyncTask;
import android.widget.Toast;

import com.classes.ShoppingList;
import com.http.HttpHandler2;
import com.http.JSONParser2;
import com.myfamily.ShoppingActivity;

public class GetShoppingList extends AsyncTask<Void, Void, Void> {

	private ShoppingActivity callingActivity;
	private String responseText;
	private String[] data;

	public GetShoppingList(String id, ShoppingActivity callingActivity) {
		this.callingActivity = callingActivity;
		data = new String[1];
		data[0] = id;
	}

	@Override
	protected void onPreExecute() {
		callingActivity.showProgressDial();
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		try {
			responseText = new HttpHandler2(
					"http://malinowepi.no-ip.org/get_shopping_list.php", data)
					.postDataGetShoppingList();
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
			ShoppingList sl = jp.getGetShoppingListResult();

			if (sl == null) {
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity, "Coś poszło nie tak!",
								Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				callingActivity.showToast(responseText);
				callingActivity.showPopup(sl);
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity,
								"Pobrano listę zakupów", Toast.LENGTH_SHORT)
								.show();
					}
				});
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
