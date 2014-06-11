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

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.async.RemoveSth;
import com.classes.Product;
import com.classes.ShoppingList;

/**
 * Aktywnosc sluzaca administracji opcji zakupow.
 * 
 * @author lagvna
 * 
 */
public class ShoppingActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private String f1 = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/currShoppingList.dat";
	private ViewPager viewPager;
	/**
	 * Adapter przypinajacy fragmenty do aktywnosci.
	 */
	public TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private String[] tabs = { "Archiwum", "Lista zakupów", "Produkty" };
	private ProgressDialog progressDialog;

	/**
	 * Lista produktow
	 */
	public ArrayList<Product> products;
	/**
	 * Aktualna lista zakupow
	 */
	public ArrayList<Product> shoppingList;
	/**
	 * Lista archiwalnych list zakupow
	 */
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

		File f = new File(f1);
		System.out.println(f.getAbsolutePath());
		if (f.exists()) {
			readFromFile();
		}
	}

	/**
	 * Metoda wywolujaca async tast pobierajacy liste zakupow z serwera
	 * 
	 * @param id
	 *            id pobieranej listy
	 */
	public void getShoppingList(int id) {
		new GetShoppingList(Integer.toString(id), ShoppingActivity.this)
				.execute();
	}

	/**
	 * Metoda sluzaca odswiezeniu widoku listy zakupowej. Wywolywana po
	 * zakonczeniu zakupow
	 */
	public void beginNewList() {
		shoppingList.clear();
		mAdapter.slf.listAdapter.notifyDataSetChanged();
		mAdapter.slf.createList();
		mAdapter.slf.sumUser.setText("Twoja suma: ");
		mAdapter.slf.sumList.setText("Suma listy: ");
		mAdapter.slf.listName = "";
	}

	/**
	 * Metoda sluzaca zakonczeniu aktualnych zakupow
	 */
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

	/**
	 * Metoda sluzaca wywolaniu okienka popup z pobrana lista zakupow
	 * 
	 * @param sl
	 *            lista zakupow, ktora chcemy pobrac
	 */
	public void showPopup(ShoppingList sl) {
		mAdapter.af.preShowPopup(sl);
	}

	/**
	 * Metoda sluzaca pobraniu listy produktow
	 */
	public void getProducts() {
		new GetProducts(ShoppingActivity.this, "0").execute();
	}

	/**
	 * Metoda sluzaca usunieciu produktu z serwera i aplikacji
	 * 
	 * @param id
	 */
	public void deleteProduct(String id) {
		new RemoveSth(id, "Produkt").execute();
		getProducts();
	}

	/**
	 * Metoda sluzaca usunieciu listy zakupow
	 * 
	 * @param id
	 *            identyfikator listy zakupow
	 */
	public void deleteShoppingList(String id) {
		new RemoveSth(id, "ListaZakupow").execute();
		beginNewList();
		getShoppingLists();
	}

	/**
	 * Metoda sluzaca pobraniu wszystkich list zakupow
	 */
	public void getShoppingLists() {
		new GetShoppingLists(ShoppingActivity.this, "0").execute();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * Metoda przypisujaca listy zakupowe do widoku
	 * 
	 * @param sl
	 *            lista list zakupow
	 */
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

	/**
	 * Metoda przypisujaca produkty do listy produktow
	 * 
	 * @param p
	 */
	public void assignProducts(ArrayList<Product> p) {
		int len = p.size();
		products.clear();

		for (int i = 0; i < len; i++) {
			products.add(new Product(p.get(i).getId(), p.get(i).getName(), p
					.get(i).getCategory(), p.get(i).getPrice()));
		}
		getShoppingLists();
	}

	/**
	 * Metoda wyswietlajaca okienko oczekiwania na odpowiedz z serwera
	 */
	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Łączenie z serwerem");
		progressDialog.show();
	}

	/**
	 * Metoda chowajaca okienko oczekiwania na odpowiedz z serwera
	 */
	public void hideProgressDial() {
		progressDialog.dismiss();
	}

	/**
	 * Metoda wyswietlajaca odpowiedz z serwera
	 * 
	 * @param responseText
	 *            odpowiedz z serwera
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

	/**
	 * Metoda zapisujaca aktualna liste zakupow do pliku, aby pozniej mozna ja
	 * bylo z powrotem wywolac
	 */
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

	/**
	 * Metoda usuwajaca plik z zapisana lista zakupow
	 */
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

	/**
	 * Metoda wywolywana po kliknieciu "wstecz". Wywoluje zapisanie listy do
	 * pliku.
	 */
	public void onBackPressed() {
		writeToFile();
		ShoppingActivity.this.finish();
		// overridePendingTransition(R.anim.in_left, R.anim.out_right);
	}
}