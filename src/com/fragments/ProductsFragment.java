package com.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.async.SetPrice;
import com.classes.Product;
import com.myfamily.AddProductActivity;
import com.myfamily.R;
import com.myfamily.ShoppingActivity;
import com.shopping.ImageAdapter;

/**
 * Fragment przypiety do aktywnosci zakupow wyswietlajacy liste dostepnych
 * produktow.
 * 
 * @author lagvna
 * 
 */
public class ProductsFragment extends Fragment {

	/**
	 * Widok kafelkow w ktorych wyswietlane sa produkty
	 */
	public GridView gv;
	private ShoppingActivity sa;
	private View rootView;
	private Button addProduct;
	/**
	 * Adapter przypinajacy kafelki produktow do widoku
	 */
	public ImageAdapter ia;
	private ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_products, container,
				false);

		sa = (ShoppingActivity) getActivity();
		ia = new ImageAdapter(getActivity(), sa.products);

		addProduct = (Button) rootView.findViewById(R.id.addproduct);
		gv = (GridView) rootView.findViewById(R.id.gridview);
		gv.setAdapter(ia);

		registerForContextMenu(rootView.findViewById(R.id.gridview));

		gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			// TODO zrobic jakies wcisniete i wycisniete guziki i zmiane
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				System.out.println(position);
				if (sa.shoppingList.contains(sa.products.get(position))) {
					sa.shoppingList.remove(sa.products.get(position));
				} else {
					sa.shoppingList.add(sa.products.get(position));
				}
				sa.mAdapter.slf.listAdapter.notifyDataSetChanged();
				sa.mAdapter.slf.createList();
			}

		});

		addProduct.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), AddProductActivity.class);
				startActivityForResult(i, 1);
			}
		});

		return rootView;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(Menu.NONE, R.id.c_item, Menu.NONE, "Edytuj cenę");
		menu.add(Menu.NONE, R.id.d_item, Menu.NONE, "Usuń");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {

		case R.id.c_item:
			editPrice((int) menuInfo.position);
			return true;
		case R.id.d_item:
			deleteProduct((int) menuInfo.position);
			return true;
		}
		return super.onContextItemSelected(item);
	}

	private void editPrice(final int pos) {
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("Wprowadź nową cenę");
		alert.setMessage("Cena");

		final EditText input = new EditText(getActivity());
		alert.setView(input);
		input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String price = input.getText().toString();
				Product p = ia.getProduct(pos);
				System.out.println(p.getName());
				new SetPrice(price, p.getId(), sa).execute();
			}
		});

		alert.setNegativeButton("Anuluj",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// nic
					}
				});

		alert.show();
	}

	private void deleteProduct(int id) {
		Product p = ia.getProduct(id);
		String toDel = "" + p.getId();
		sa.products.remove(p);

		sa.deleteProduct(toDel);
		ia = new ImageAdapter(getActivity(), sa.products);
		gv.setAdapter(ia);
	}

	/**
	 * Metoda wyswietlajaca okienko oczekiwania na odpowiedz z serwera
	 */
	public void showProgressDial() {
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage("Ustawianie nowej ceny");
		progressDialog.show();
	}

	/**
	 * Metoda chowajaca okienko oczekiwania na odpowiedz z serwera
	 */
	public void hideProgressDial() {
		progressDialog.dismiss();
	}

	/**
	 * Metoda pokazujaca toast z odpowiedzia od serwera
	 * @param responseText odpowiedz od serwera
	 */
	public void showToast(String responseText) {
		System.err.println(responseText);
	}
	
	/**
	 * Metoda wywolywana jako wynik wywolania aktywnosci dodania produktu.
	 * Dodaje produkt do listy i odswieza widok.
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getActivity();
		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			System.out.println("Jestem tutaj");
			Bundle extras = data.getExtras();
			Product p = (Product) extras.get("product");
			sa.products.add(p);

			ia = new ImageAdapter(getActivity(), sa.products);
			gv.setAdapter(ia);
		}
	}
}