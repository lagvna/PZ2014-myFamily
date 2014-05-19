package com.async;

import java.io.IOException;

import org.json.JSONException;

import android.os.AsyncTask;
import android.widget.Toast;

import com.http.HttpHandler2;
import com.http.JSONParser2;
import com.myfamily.ExpenseActivity;

public class AddExpense extends AsyncTask<Void, Void, Void> {

	private ExpenseActivity callingActivity;
	private String responseText;
	private String[] data;

	public AddExpense(String name, String price, String note,
			ExpenseActivity callingActivity) {
		this.callingActivity = callingActivity;
		data = new String[3];
		data[0] = name;
		data[1] = price;
		data[2] = note;
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
					"http://malinowepi.no-ip.org/add_expense.php", data)
					.postDataAddExpense();
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
			int msg = jp.getAddExpenseResult();

			if (msg == 0) {
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity, "Coś poszło nie tak!",
								Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity, "Dodano nowy wydatek",
								Toast.LENGTH_SHORT).show();
					}
				});
			}
		} catch (JSONException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
