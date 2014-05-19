package com.async;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.os.AsyncTask;
import android.widget.Toast;

import com.classes.Expense;
import com.http.HttpHandler2;
import com.http.JSONParser2;
import com.myfamily.ExpenseActivity;

public class GetExpenses extends AsyncTask<Void, Void, Void> {

	private ExpenseActivity callingActivity;
	private String responseText;
	private String data[];

	public GetExpenses(String fromDate, String toDate, String login,
			ExpenseActivity callingActivity) {
		this.callingActivity = callingActivity;
		data = new String[3];
		data[0] = fromDate;
		data[1] = toDate;
		data[2] = login;
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
					"http://malinowepi.no-ip.org/get_expenses.php", data)
					.postDataGetExpenses();
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
			ArrayList<Expense> exp = jp.getGetExpensesResult();
			callingActivity.showToast(responseText);
			if (exp == null) {
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity, "Coś poszło nie tak!",
								Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				callingActivity.assignExpenses(exp);
				callingActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(callingActivity,
								"Pobrano żądane wydatki", Toast.LENGTH_SHORT)
								.show();
					}
				});
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
