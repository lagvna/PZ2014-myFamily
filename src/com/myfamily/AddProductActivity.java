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

/**
 * Klasa aktywnosci sluzacej dodaniu produktu, do serwerowej bazy danych.
 * @author lagvna
 *
 */
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
	
	/**
	 * Metoda obslugujaca przycisk dodania produktu. Metoda wyciaga z odpowiednich
	 * komponentow dane odnosnie produktu i wysyla je do serwera.
	 * @param v aktualny widok aplikacji
	 */
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

	/**
	 * Aktywnosc jest wywolywana w tzw. trybie "on result". W zwiazku z tym  jest to metoda, ktora
	 * zostanie wywolana przez async task wysylania nowego produktu do serwera i po tym zostanie
	 * ustawiony nowy produkt w glownej aktywnosci.
	 * @param p
	 */
	public void setNewProduct(Product p) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("product", p);
		setResult(Activity.RESULT_OK, returnIntent);
	}

	/**
	 * Metoda wywolujaca okienko czekania na odpowiedz od serwera.
	 */
	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Dodawanie produktu");
		progressDialog.show();
	}

	/**
	 * Metoda chowajaca okienko czekania na odpowiedz od serwera.
	 */
	public void hideProgressDial() {
		progressDialog.hide();
	}

	/**
	 * Metoda wywolujaca toast z odpowiedzia od serwera.
	 * @param responseText
	 */
	public void showToast(String responseText) {
		System.err.println(responseText);
	}

	/**
	 * Metoda wywolywana po nacisnieciu klawisza funkcyjnego "wstecz". Konczy aktywnosc.
	 */
	public void onBackPressed() {
		AddProductActivity.this.finish();
	}
}