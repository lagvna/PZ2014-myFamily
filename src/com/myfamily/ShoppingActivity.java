package com.myfamily;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.adapters.TabsPagerAdapter;
import com.async.AddShoppingList;
import com.async.GetProducts;
import com.async.GetShoppingList;
import com.async.GetShoppingLists;
import com.async.RemoveShopping;
import com.classes.Product;
import com.classes.ShoppingList;

public class ShoppingActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	public TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private String[] tabs = { "Archiwum", "Lista zakupów", "Produkty" };
	private ProgressDialog progressDialog;

	public ArrayList<Product> products;
	public ArrayList<Product> shoppingList;
	public ArrayList<ShoppingList> shoppingLists;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping);

		products = new ArrayList<Product>();
		shoppingList = new ArrayList<Product>();
		shoppingLists = new ArrayList<ShoppingList>();

		getProducts();

		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	public void getShoppingList(int id) {
		new GetShoppingList(Integer.toString(id), ShoppingActivity.this)
				.execute();
	}

	public void beginNewList() {
		shoppingList.clear();
		mAdapter.slf.listAdapter.notifyDataSetChanged();
		mAdapter.slf.createList();
		mAdapter.slf.sumUser.setText("Twoja suma: ");
		mAdapter.slf.sumList.setText("Suma listy: ");
		mAdapter.slf.listName = "";
	}

	public void endShopping() {
		String products = "";
		String totalCost;
		String name;

		System.out.println("WAZNE1: "
				+ mAdapter.slf.sumUser.getText().toString());
		System.out.println("WAZNE2: "
				+ mAdapter.slf.sumList.getText().toString());

		String tmp = (String) mAdapter.slf.sumUser.getText().toString();

		if (tmp.equals("Twoja suma: ")) {
			totalCost = (String) mAdapter.slf.sumList.getText().toString();
			System.out.println("Jestem tutaj1");
		} else {
			totalCost = (String) mAdapter.slf.sumUser.getText().toString();
			System.out.println("Jestem tutaj2");
		}

		for (int i = 0; i < shoppingList.size(); i++) {
			if (i < shoppingList.size() - 1) {
				products += shoppingList.get(i).getId() + ",";
			} else {
				products += shoppingList.get(i).getId();
			}
		}

		name = mAdapter.slf.listName;

		if (shoppingList.size() > 0) {
			new AddShoppingList(totalCost, name, products,
					ShoppingActivity.this).execute();
		} else {
			Toast.makeText(this, "Lista jest pusta!", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void showPopup(ShoppingList sl) {
		mAdapter.af.preShowPopup(sl);
	}
	
	public void delete(int what)	{
		
	}

	public void getProducts() {
		new GetProducts(ShoppingActivity.this).execute();
	}

	public void deleteProduct(String id) {
		new RemoveShopping(id, "Produkt", this).execute();
		getProducts();
	}

	public void deleteShoppingList(String id) {
		new RemoveShopping(id, "ListaZakupow", this).execute();
		beginNewList();
		getShoppingLists();
	}

	public void getShoppingLists() {
		new GetShoppingLists(ShoppingActivity.this).execute();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void assignLists(ArrayList<ShoppingList> sl) {
		int len = sl.size();
		shoppingLists.clear();

		for (int i = 0; i < len; i++) {
			shoppingLists.add(new ShoppingList(sl.get(i).getId(), sl.get(i)
					.getOwner(), sl.get(i).getName(), sl.get(i).getTotalCost(),
					sl.get(i).getDate()));
			System.out.println(shoppingLists.get(i).getName());
		}
	}

	public void assignProducts(ArrayList<Product> p) {
		int len = p.size();
		products.clear();

		for (int i = 0; i < len; i++) {
			products.add(new Product(p.get(i).getId(), p.get(i).getName(), p
					.get(i).getCategory(), p.get(i).getPrice()));
		}
		getShoppingLists();
	}

	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Łączenie z serwerem");
		progressDialog.show();
	}

	/**
	 * method which hide Progress dialog
	 */
	public void hideProgressDial() {
		progressDialog.dismiss();
	}

	/**
	 * @param responseText
	 */
	public void showToast(String responseText) {
		System.err.println(responseText);
	}

	@Override
	public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1) {

	}

	@Override
	public void onTabSelected(Tab arg0, android.app.FragmentTransaction arg1) {
		System.out.println(arg0.getText());
		viewPager.setCurrentItem(arg0.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, android.app.FragmentTransaction arg1) {

	}

	private void writeToFile(String data) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					openFileOutput("shopping_list.txt", Context.MODE_PRIVATE));
			outputStreamWriter.write(data);
			outputStreamWriter.close();
		} catch (IOException e) {
			Log.e("Exception", "File write failed: " + e.toString());
		}
	}

	private void deleteFile() {
		File file = new File("shopping_list.txt");
		file.delete();
	}

	private String readFromFile() {

		String ret = "";

		try {
			InputStream inputStream = openFileInput("shopping_list.txt");

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ((receiveString = bufferedReader.readLine()) != null) {
					stringBuilder.append(receiveString);
				}

				inputStream.close();
				ret = stringBuilder.toString();
			}
		} catch (FileNotFoundException e) {
			Log.e("login activity", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
		}

		return ret;
	}
}