package com.myfamily;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.async.UserRegister;

public class UserRegisterActivity extends Activity {

	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_user_register);

		Button registerButton = (Button) findViewById(R.id.createAccountButton);
		final EditText loginEditText = (EditText) findViewById(R.id.userNameEditText);
		final EditText passEditText = (EditText) findViewById(R.id.userPasswordEditText);
		final EditText pass1EditText = (EditText) findViewById(R.id.userConfirmPasswordEditText);

		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (loginEditText.getText().toString().equals("")
						|| passEditText.getText().toString().equals("")
						|| pass1EditText.getText().toString().equals("")) {
					showToast("Uzypełnij wszystkie pola!");
				} else if (!passEditText.getText().toString().equals(
						pass1EditText.getText().toString())) {
					showToast("hasla nie zgadzaja sie");
				} else {
					new UserRegister(UserRegisterActivity.this, new String[] {
							loginEditText.getText().toString(),
							passEditText.getText().toString()}).execute();
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_register, menu);
		return true;
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

}