package com.async;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.os.AsyncTask;
import android.widget.Toast;

import com.classes.Product;
import com.http.HttpHandler2;
import com.http.JSONParser2;
import com.myfamily.ShoppingActivity;

public class GetProducts extends AsyncTask<Void, Void, Void> {

	private ShoppingActivity callingActivity;
	private String responseText;
	private String dateFrom;

	public GetProducts(ShoppingActivity callingActivity, String dateFrom) {
		this.callingActivity = callingActivity;
		this.dateFrom = dateFrom;
	}

	@Override
	protected void onPreExecute() {
		callingActivity.showProgressDial();
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		try {
			String data[] = new String[1];
			data[0] = dateFrom;
			responseText = new HttpHandler2(
					"http://malinowepi.no-ip.org/get_products.php", data)
					.postDataGetProducts();
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
			ArrayList<Product> products = jp.getGetProductsResult();

			if (products == null) {
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity, "Coś poszło nie tak!",
								Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				callingActivity.showToast(responseText);
				callingActivity.assignProducts(products);
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity,
								"Pobrano wszystkie produkty",
								Toast.LENGTH_SHORT).show();
					}
				});

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
