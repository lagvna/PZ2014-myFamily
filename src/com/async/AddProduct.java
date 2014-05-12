package com.async;

import java.io.IOException;

import org.json.JSONException;

import android.os.AsyncTask;
import android.widget.Toast;

import com.classes.Product;
import com.http.HttpHandler2;
import com.http.JSONParser2;
import com.myfamily.AddProductActivity;

public class AddProduct extends AsyncTask<Void, Void, Void> {

	private AddProductActivity callingActivity;
	private String responseText;
	private String[] data;

	public AddProduct(String name, String category, String price,
			AddProductActivity callingActivity) {
		this.callingActivity = callingActivity;
		data = new String[3];
		data[0] = name;
		data[1] = category;
		data[2] = price;
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
					"http://malinowepi.no-ip.org/add_product.php", data)
					.postDataAddProduct();
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
			Product product = jp.getAddProductResult();

			if (product.getId() == -1) {
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity, "Coś poszło nie tak!",
								Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				callingActivity.setNewProduct(product);
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity, "Dodano produkt",
								Toast.LENGTH_SHORT).show();
					}
				});
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		callingActivity.finish();
	}
}
