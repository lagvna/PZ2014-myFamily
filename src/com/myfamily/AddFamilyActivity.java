package com.myfamily;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.async.AddFamily;

public class AddFamilyActivity extends Activity {

	private boolean correctData = false;

	private EditText loginEditText;
	private Button acceptButton;
	private ProgressDialog progressDialog;
	private String responseText = null;
	private Context context = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_add_family);
		context = this.getApplicationContext();
		loginEditText = (EditText) findViewById(R.id.familyNameEditText);

		acceptButton = (Button) findViewById(R.id.acceptBatton);



		acceptButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				removeErrors();
				if (isEmpty(loginEditText)) {
					removeErrors();
					loginEditText.setError("To pole nie moze byc puste");
					correctData = false;
				} else
					correctData = true;

				if (correctData) {
					newFamily();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_family, menu);
		return true;
	}

	public void removeErrors() {
		loginEditText.setError(null);
	}

	public boolean isEmpty(EditText et) {
		if (et.getText().toString().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Create new account or family
	 */
	private void newFamily() {

		String familyName = loginEditText.getText().toString();

		new AddFamily(familyName, this).execute();
	}

	/**
	 * this method show Progress Dialog
	 */
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
	 * This method is call from CreateAccount.class inform user that new account
	 * is available then open LoginActivity
	 */
	public void familyCreated() {

		// Toast.makeText(getApplicationContext(), "Rodzina utworzona.",
		// Toast.LENGTH_LONG).show();
		createConfirmationDialog("Rodzina utworzona. Zaloguj się ponownie");
		Intent returnIntent = new Intent();
		setResult(RESULT_OK, returnIntent);
		finish();
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(intent);
	}

	public void showToast(String responseText) {

		Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG)
				.show();
	}

	/**
	 * this method set the response text
	 * 
	 * @param rt
	 *            response text
	 */
	public void setResponseText(String rt) {
		this.responseText = rt;
	}

	private void createConfirmationDialog(String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Dodano nowa rodzine. Dodano także użytwkonika głównego. \nlogin: admin \nhasło: admin\n"
						+ "Użyj tych danych podczas pierwszego logowania.")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						finish();
						startActivity(new Intent(context, LoginActivity.class));
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
}