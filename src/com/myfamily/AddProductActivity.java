package com.myfamily;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.async.AddProduct;
import com.classes.Product;

public class AddProductActivity extends Activity {

	private EditText name;
	private EditText category;
	private EditText price;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_add_product);

		name = (EditText) findViewById(R.id.title_descProd);
		category = (EditText) findViewById(R.id.desc_descProd);
		price = (EditText) findViewById(R.id.cost_descProd);
	}

	public void addProduct(View v) {
		if (!name.getText().toString().equals("")
				&& !category.getText().toString().equals("")) {
			String nameTxt = name.getText().toString();
			String catTxt = category.getText().toString();
			String costTxt = price.getText().toString();

			sendToServer(nameTxt, catTxt, costTxt);

		} else {
			Toast.makeText(this, "Wprowadź nazwę i/lub kategorię",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void sendToServer(String n, String c, String p) {
		new AddProduct(n, c, p, AddProductActivity.this).execute();
	}

	public void setNewProduct(Product p) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("product", p);
		setResult(Activity.RESULT_OK, returnIntent);
	}

	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Dodawanie produktu");
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
		System.err.println(responseText);
	}

	public void onBackPressed() {
		AddProductActivity.this.finish();
	}
}