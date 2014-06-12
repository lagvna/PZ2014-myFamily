package com.myfamily;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.async.AddUser;
import com.classes.DataHolder;

/**
 * @author kwachu
 * Aktywnosc odpowiedzilna za dodawanie nowego uzytkownika do systemu aplikacji 
 */
public class AddUserActivity extends Activity {

	private AddUserActivity context;
	private ProgressDialog progressDialog;
	private String sessionId = null;
	private Button addButton = null;
	private EditText userName = null;
	private String responseText = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_add_user);
		context = this;
		final Spinner spinner = (Spinner) findViewById(R.id.spinner);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.users_type, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		sessionId = DataHolder.getInstance().getSession();

		addButton = (Button) findViewById(R.id.button1);
		userName = (EditText) findViewById(R.id.userName);

		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AddUser(userName.getText().toString(), spinner
						.getSelectedItem().toString(), context).execute();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_user, menu);
		return true;
	}

	/*
	 * @Override public void onBackPressed() { userListShow(); }
	 */

	/**
	 * Metoda odpowiedzialna za wyswietlanie paska postepu na ekranie 
	 */
	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Sprawdzanie danych");
		progressDialog.show();
	}

	/**
	 * matoda odpowiedzalna za ukrywanie paska postepu 
	 */
	public void hideProgressDial() {
		progressDialog.hide();
	}

	/**
	 * @param responseText
	 * 
	 * metoda odpowiedzialna ze pokazywanie tosta na ekranie 
	 */
	public void showToast(String responseText) {

		Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG)
				.show();
	}

	/**
	 * @param rt
	 * metoda odpowiedzialna za ustawianie tekstu odebranego od serwra 
	 */
	public void setResponseText(String rt) {
		this.responseText = rt;
	}

	/**
	 * Metoda odpowiedzialna ze uruchomienie aktywnosci zawierajacej liste uzytkownikow 
	 */
	public void userListShow() {
		finish();
		startActivity(new Intent(this.getApplicationContext(),
				MainActivity.class));
	}

}