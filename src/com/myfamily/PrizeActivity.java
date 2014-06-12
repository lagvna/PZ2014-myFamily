package com.myfamily;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adapters.MyPrizesAdapter;
import com.adapters.UserSpinnerAdapter;
import com.classes.JSonReader;
import com.classes.Prize;
import com.classes.User;
import com.http.HttpHandler;
import com.http.JSONParser;

/**
 * @author kwachu
 * Aktywnosc odpowiedzialna za prezetacje danych dotyczacych uzyskanych nagrud przez wybraneg uzywkownika 
 */
public class PrizeActivity extends Activity {

	private ListView myPrizesListView;
	private ArrayList<Prize> myPrizesList = new ArrayList<Prize>();
	private ArrayList<User> familyUsers = new ArrayList<User>();
	private Spinner userSpinner;
	private Spinner categorySpinner;
	private Button accept;
	protected User selectedUser;
	private TextView gainedPoints;
	private boolean userChanged = true;
	private ProgressDialog progressDialog;
	private int category = 0;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prize);

		userSpinner = (Spinner) findViewById(R.id.myPrizesUserSpinner);
		categorySpinner = (Spinner) findViewById(R.id.myPrizesCategorySpinner);
		accept = (Button) findViewById(R.id.myPrizesAcceptButton);
		gainedPoints = (TextView) findViewById(R.id.myPrizesGainedPointsTextView);
		myPrizesListView = (ListView) findViewById(R.id.myPrizesListView);
		addListenersOnSpinnersItemSelection();
		getFamilyUsersAndInitializeSpinner();
		context = this;
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.categories, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categorySpinner.setAdapter(adapter);

		categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				category = position+1;
				//showToast("Spinner1: position=" + position + " id=" + id);
			}

			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});

		accept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (selectedUser != null && userChanged) {
					myPrizesList.clear();
					new GetGainedPrizes(selectedUser.getUserName(), "1")
							.execute();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.prize, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void getFamilyUsersAndInitializeSpinner() {
		new GetUsers().execute();
	}

	/**
	 * Metoda odpwiedzilna za dodawnie sluchaczy akcji do spinerow wyswietlajacych liste uzytkownikow 
	 */
	public void addListenersOnSpinnersItemSelection() {
		userSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				User tempUser = (User) userSpinner.getSelectedItem();
				if (tempUser != null
						&& selectedUser != null
						&& tempUser.getUserName().equals(
								selectedUser.getUserName())) {
					userChanged = false;
				} else {
					userChanged = true;
					selectedUser = tempUser;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * @param points
	 * Metoda odpowiedzialna za incjalizowanie listy z nagrodami danego uzytkownika 
	 */
	public void initList(String points) {
		gainedPoints.setText("Zdobyte punkty: " + points);
		MyPrizesAdapter adapter;
		if (!myPrizesList.isEmpty()) {
			adapter = new MyPrizesAdapter(this.getApplicationContext(),
					myPrizesList);
		} else {
			ArrayList<Prize> tempList = new ArrayList<Prize>();
			tempList.add(new Prize("Brak nagród", "", "", "",""));
			adapter = new MyPrizesAdapter(this.getApplicationContext(),
					tempList);
		}
		myPrizesListView.setAdapter(adapter);
	}

	public void addItemsOnUserSpinner() {

		UserSpinnerAdapter spinnerArrayAdapter = new UserSpinnerAdapter(this,
				android.R.layout.simple_spinner_item, familyUsers);
		userSpinner.setAdapter(spinnerArrayAdapter);

	}

	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Sprawdzanie danych");
		progressDialog.show();
	}

	/**
	 * method which hide Progress dialog
	 */
	public void hideProgressDial() {
		progressDialog.hide();
	}

	/**
	 * @param responseText
	 */
	public void showToast(String responseText) {

		Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG)
				.show();
	}

	/**
	 * @author kwachu
	 *AsyncTask odpowiedzialny za pobieanie uztkownikow z serwera 
	 */
	class GetUsers extends AsyncTask<Void, Void, Void> {

		private String responseText = null;

		@Override
		protected void onPreExecute() {
			showProgressDial();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				responseText = new HttpHandler(
						"http://malinowepi.no-ip.org/get_family_users.php",
						null).postDataGetUsers();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			hideProgressDial();
			JSONParser jp = new JSONParser(responseText);
			try {
				familyUsers = jp.getUsersResult();
				addItemsOnUserSpinner();
			} catch (JSONException e) {
				// Toast.makeText(pa,e.toString(),2000);
			}
		}

	}

	/**
	 * @author kwachu
	 * AyncTask odpowiedzialny za pobieranie ocenionych nagrod z serwera 
	 */
	class GetGainedPrizes extends AsyncTask<Void, Void, Void> {

		private String responseText;
		private String[] dataArray;

		public GetGainedPrizes(String login, String receivedOrAll) {
			dataArray = new String[4];
			dataArray[0] = login;
			dataArray[1] = receivedOrAll;
			dataArray[2] = ""+category;
			
			String str = JSonReader.getInstance().readFile("prizes", context);
			String temp = str.substring(0, 19);
			if (temp.equals("0000-00-00 00:00:00")) {
				dataArray[3] = "0";
			} else {
				dataArray[3] = temp;
			}
		}

		@Override
		protected void onPreExecute() {
			showProgressDial();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				responseText = new HttpHandler(
						"http://malinowepi.no-ip.org/get_prizes.php", dataArray)
						.postDataGetPrizes();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			hideProgressDial();
			//showToast(responseText);
			JSONParser jp = new JSONParser(responseText);
			try {
				ArrayList resultSet = jp.getGainedPrizesResult();
				if (resultSet.size() == 2) {
					myPrizesList = (ArrayList<Prize>) resultSet.get(0);
					initList((String) ((ArrayList) resultSet.get(1)).get(2));
					jp.makePrizesJSon(myPrizesList,context,1);
				} else if (resultSet.size() == 1) {
					showToast((String) ((ArrayList) resultSet.get(0)).get(1));
					initList((String) ((ArrayList) resultSet.get(0)).get(2));
				}
				
				
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}

}
