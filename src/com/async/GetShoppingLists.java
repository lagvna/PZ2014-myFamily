package com.async;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.os.AsyncTask;
import android.widget.Toast;

import com.classes.ShoppingList;
import com.http.HttpHandler2;
import com.http.JSONParser2;
import com.myfamily.ShoppingActivity;

public class GetShoppingLists extends AsyncTask<Void, Void, Void> {

	private ShoppingActivity callingActivity;
	private String responseText;

	public GetShoppingLists(ShoppingActivity callingActivity) {
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
			responseText = new HttpHandler2(
					"http://malinowepi.no-ip.org/get_shopping_lists.php", null)
					.postDataGetShoppingLists();
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
			ArrayList<ShoppingList> sl = jp.getGetShoppingListsResult();

			if (sl == null) {
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity, "Coś poszło nie tak!",
								Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				callingActivity.showToast(responseText);
				callingActivity.assignLists(sl);
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity,
								"Pobrano wszystkie listy zakupów",
								Toast.LENGTH_SHORT).show();
					}
				});
				callingActivity.mAdapter.af.listAdapter.notifyDataSetChanged();
				callingActivity.mAdapter.af.createList();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
