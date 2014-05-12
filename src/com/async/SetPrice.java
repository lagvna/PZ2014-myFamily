package com.async;

import java.io.IOException;

import org.json.JSONException;

import android.os.AsyncTask;
import android.widget.Toast;

import com.http.HttpHandler2;
import com.http.JSONParser2;
import com.myfamily.ShoppingActivity;

public class SetPrice extends AsyncTask<Void, Void, Void> {

	private ShoppingActivity callingActivity;
	private String responseText;
	private String[] data;

	public SetPrice(String price, int id, ShoppingActivity callingActivity) {
		this.callingActivity = callingActivity;
		data = new String[2];
		data[0] = price;
		data[1] = Integer.toString(id);
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
					"http://malinowepi.no-ip.org/set_price.php", data)
					.postDataSetPrice();
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
			int err = jp.getSetPriceResult();

			if (err == 0) {
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity, "Coś poszło nie tak!",
								Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity,
								"Cena pomyślnie zmieniona", Toast.LENGTH_SHORT)
								.show();
					}
				});
				callingActivity.getProducts();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
