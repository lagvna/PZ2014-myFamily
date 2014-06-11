package com.myfamily;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.async.CheckLogin;
import com.classes.DataHolder;
import com.classes.Family;
import com.classes.JSonReader;
import com.classes.JSonWriter;

/**
 * @author kwachu
 * Aktywnosc odpowiedzalna za logowanie uzytkownika do systemu 
 */
public class LoginActivity extends Activity {

	private EditText username;
	private EditText password;
	private ProgressDialog progressDialog;
	private static final int LONG_DELAY = 35000;
	private String responseText ="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_login);

		DataHolder dataHolder = new DataHolder();
		JSonReader JSonReader = new JSonReader();
		JSonWriter jSonWriter = new JSonWriter();
		
		username = (EditText) findViewById(R.id.userNameEditText);
		password = (EditText) findViewById(R.id.passwordEditText);

	}


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	}

	/**
	 * @param view
	 * Metoda odpowiedzialna za wywolywanie akatywnosci  UserRegisterActicity 
	 */
	public void createAccount(View view) {
		startActivity(new Intent(this.getApplicationContext(),
				UserRegisterActivity.class));
	}

	/**
	 * Metoda wywolujaca MainActivity po zalogowaniu do systemu .
	 */
	public void loginAccepted(ArrayList<Family> familyList,String session) {
		setSessionId(session);
		DataHolder.getInstance().setLogin(username.getText().toString());
		Intent intent = new Intent(getBaseContext(), FamilyListActivity.class);
		if(familyList!=null) {
			for(int i=0;i<familyList.size();i++) {
				intent.putExtra(""+i, familyList.get(i));
			}
			finish();
			startActivity(intent);
		} else {
			finish();
			startActivity(intent);
		}
		
		// tutaj odpala sie aktywnosc Jarkaa
	}

	/**
	 * Metoda odpowiedzina za informowanie uzytkownika o wprowadzonych niepoprawnych danych. 
	 * 
	 * @param view
	 *            - reference to button which call this method (loginButton)
	 */
	public void login(View view) {

		if (!(username.getText().toString().equals("") || password.getText()
				.toString().equals(""))) {
			new CheckLogin(this, username.getText().toString(), password
					.getText().toString()).execute();
		} else {
			Toast.makeText(getApplicationContext(),
					"wprowadzone dane są niepoprawne", Toast.LENGTH_LONG)
					.show();
		}
	}

	/**
	 * Metoda odpowiedzialna za wyswietlanie progres bara 
	 */
	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Sprawdzanie danych");
		progressDialog.show();
	}

	/**
	 * metoda odpowiedzialna za ukrywanie progres bara 
	 */
	public void hideProgressDial() {
		progressDialog.hide();
	}

	/**
	 * metoda odpowiedzialna za wyswietlenie tosta na ekranie 
	 * @param responseText
	 */
	public void showToast(String responseText) {

		Toast.makeText(getApplicationContext(), responseText, LONG_DELAY)
				.show();
	}

	/**
	 * Metoda odpowiedzialna za ustawianie tekstu, ktory otrzymalismy od serwera 
	 * 
	 * @param rt
	 *            response text
	 */
	public void setResponseText(String rt) {
		this.responseText = rt;
	}

	/**
	 * @param session
	 * Metoda odpowiedzialna ze ustawianie Id sesji 
	 */
	public void setSessionId(String session) {
		DataHolder.getInstance().setSession(session);
	}

}