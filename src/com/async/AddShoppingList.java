package com.async;

import java.io.IOException;

import org.json.JSONException;

import android.os.AsyncTask;
import android.widget.Toast;

import com.http.HttpHandler2;
import com.http.JSONParser2;
import com.myfamily.ShoppingActivity;

/**
 * Klasa wywolujaca zdarzenie asynchroniczne odpowiadajace za dodanie listy zakupow po jej zakonczeniu
 * @author lagvna
 *
 */
public class AddShoppingList extends AsyncTask<Void, Void, Void> {

	private ShoppingActivity callingActivity;
	private String responseText;
	private String[] data;

	public AddShoppingList(String totalCost, String name, String products,
			ShoppingActivity callingActivity) {
		this.callingActivity = callingActivity;
		data = new String[3];
		data[0] = totalCost;
		data[1] = name;
		data[2] = products;
		System.out.println(totalCost);
		System.out.println(name);
		System.out.println(products);
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
					"http://malinowepi.no-ip.org/add_shopping_list.php", data)
					.postDataAddShoppingList();
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
			String msg = jp.getAddShoppingListResult();

			if (msg.equals("0")) {
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity, "Coś poszło nie tak!",
								Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				callingActivity.beginNewList();
				callingActivity.getShoppingLists();
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity, "Zakończono zakupy",
								Toast.LENGTH_SHORT).show();
					}
				});
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
