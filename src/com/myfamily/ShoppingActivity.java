package com.myfamily;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.adapters.TabsPagerAdapter;
import com.async.AddShoppingList;
import com.async.GetProducts;
import com.async.GetShoppingList;
import com.async.GetShoppingLists;
import com.async.RemoveShopping;
import com.classes.Product;
import com.classes.ShoppingList;
import com.http.HttpHandler2;
import com.http.JSONParser2;

public class ShoppingActivity extends FragmentActivity implements
		ActionBar.TabListener {
	private String f1 = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/currShoppingList.dat";
	private ViewPager viewPager;
	public TabsPagerAdapter mAdapter;
	private String[] tabs = { "Archiwum", "Lista zakupów", "Produkty" };
	private ProgressDialog progressDialog;
	private ShoppingActivity activity;

	public ArrayList<Product> products;
	public ArrayList<Product> shoppingList;
	public ArrayList<ShoppingList> shoppingLists;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping);
		activity = this;

		// date = sdf.format(new Date());
		// format datyFrom: 2014-05-12 20:08:34
		// 0 pobiera wszystko
		new GetShoppingData("0").execute();

		products = new ArrayList<Product>();
		shoppingList = new ArrayList<Product>();
		shoppingLists = new ArrayList<ShoppingList>();
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

		deleteFile();
	}

	public void showPopup(ShoppingList sl) {
		mAdapter.af.preShowPopup(sl);
	}

	public void delete(int what) {

	}

	public void getProducts() {
		new GetProducts(ShoppingActivity.this, "0").execute();
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
		new GetShoppingLists(ShoppingActivity.this, "0").execute();
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

	private void initializeFragments() {

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setHomeButtonEnabled(false);

		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(mAdapter);

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

	private void writeToFile() {
		FileOutputStream fos;
		File f = new File(f1);
		try {
			fos = new FileOutputStream(f);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeInt(shoppingList.size());
			for (Product p : shoppingList)
				os.writeObject(p);
			os.close();
			fos.close();
			System.out.println(f.getAbsolutePath());
			System.out.println("Zapisałem!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void deleteFile() {
		File file = new File(f1);
		file.delete();
	}

	private void readFromFile() {
		FileInputStream fis;
		try {
			File f = new File(f1);
			fis = new FileInputStream(f);
			ObjectInputStream is = new ObjectInputStream(fis);
			int count = is.readInt();
			for (int c = 0; c < count; c++)
				shoppingList.add((Product) is.readObject());
			is.close();
			fis.close();
			System.out.println("Odczytałem!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void onBackPressed() {
		writeToFile();
		ShoppingActivity.this.finish();
		// overridePendingTransition(R.anim.in_left, R.anim.out_right);
	}

	public class GetShoppingData extends AsyncTask<Void, Void, Void> {

		private String responseShoppingLists;
		private String responseProducts;
		private String dateFrom;

		public GetShoppingData(String dateFrom) {
			this.dateFrom = dateFrom;
		}

		@Override
		protected void onPreExecute() {
			showProgressDial();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				String data[] = new String[1];
				data[0] = dateFrom;
				responseShoppingLists = new HttpHandler2(
						"http://malinowepi.no-ip.org/get_shopping_lists.php",
						data).postDataGetShoppingLists();

				responseProducts = new HttpHandler2(
						"http://malinowepi.no-ip.org/get_products.php", data)
						.postDataGetProducts();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			File f = new File(activity.f1);
			System.out.println(f.getAbsolutePath());
			if (f.exists()) {
				readFromFile();
			}
		
			JSONParser2 jp = new JSONParser2(responseShoppingLists);

			try {
				ArrayList<ShoppingList> sl = jp.getGetShoppingListsResult();

				if (sl == null) {
					activity.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(activity,
									"NIEPOWODZENIE! Pobieranie list zakupów",
									Toast.LENGTH_SHORT).show();
						}
					});
				} else {
					activity.shoppingLists.addAll(sl);
					System.out.println(shoppingLists.get(0).getName());
					activity.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(activity,
									"Pobrano wszystkie listy zakupów",
									Toast.LENGTH_SHORT).show();
						}
					});
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			JSONParser2 jp2 = new JSONParser2(responseProducts);

			try {
				ArrayList<Product> p = jp2.getGetProductsResult();

				if (products == null) {
					activity.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(activity,
									"NIEPOWODZENIE! Pobieranie produktów",
									Toast.LENGTH_SHORT).show();
						}
					});
				} else {
					activity.products.addAll(p);
					System.out.println(products.get(0).getName());
					activity.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(activity,
									"Pobrano wszystkie produkty",
									Toast.LENGTH_SHORT).show();
						}
					});
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			initializeFragments();
			hideProgressDial();
		}
	}
}