package com.fragments;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.myfamily.R;
import com.myfamily.ShoppingActivity;
import com.shopping.CustomRow;
import com.shopping.ListViewAdapter;

/**
 * Fragment przypiety do aktywnosci administracji zakupow.
 * 
 * @author lagvna
 *
 */
public class ShoppingListFragment extends Fragment {
	private ArrayList<CustomRow> CustomRow_data;
	/**
	 * Adapter widoku listowego
	 */
	public ListViewAdapter listAdapter;
	ShoppingActivity sa;
	/**
	 * Komponent odpowiadajacy za wyswietlanie sumy za produkty z listy
	 */
	public TextView sumList;
	/**
	 * Komponent wyswietlajacy sume za produkty podana przez uzytkownika
	 */
	public TextView sumUser;
	private Button editShopping;
	private Button endShopping;
	/**
	 * Nazwa listy
	 */
	public String listName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_shopping_list,
				container, false);

		sa = (ShoppingActivity) getActivity();
		
		registerForContextMenu(rootView.findViewById(R.id.list));

		listName = "";
		editShopping = (Button) rootView.findViewById(R.id.editPrice);
		endShopping = (Button) rootView.findViewById(R.id.endShopping);
		sumUser = (TextView) rootView.findViewById(R.id.sumUserList);
		sumList = (TextView) rootView.findViewById(R.id.sumFromList);
		CustomRow_data = new ArrayList<CustomRow>();
				

		editShopping.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setUserSum();
			}
		});

		endShopping.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				endShoppingList();
			}
		});

		ListView lv = (ListView) rootView.findViewById(R.id.list);
		listAdapter = new ListViewAdapter(getActivity(), R.layout.element2,
				CustomRow_data);
		lv.setAdapter(listAdapter);

		registerForContextMenu(rootView.findViewById(R.id.list));

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO zaznaczanie ze kupione
			}
		});
		
		listAdapter.notifyDataSetChanged();
		createList();

		return rootView;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(Menu.NONE, R.id.a_item, Menu.NONE, "Usuń");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {

		case R.id.a_item:
			deleteItem((int) menuInfo.position);
			return true;
		}
		return super.onContextItemSelected(item);
	}

	private void deleteItem(int id) {
		sa.shoppingList.remove(id);
		listAdapter.notifyDataSetChanged();
		createList();
	}
	
	private void endShoppingList() {
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("Wprowadź nazwę zakupów");
		alert.setMessage("Nazwa");

		final EditText input = new EditText(getActivity());
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				listName = input.getText().toString();
				sa.endShopping();
			}
		});

		alert.setNegativeButton("Anuluj",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});

		alert.show();
	}

	private void setUserSum() {
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("Wprowadź sumę");
		alert.setMessage("Suma");

		final EditText input = new EditText(getActivity());
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				sumUser.setText(input.getText().toString());
			}
		});

		alert.setNegativeButton("Anuluj",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						sumUser.setText("Twoja suma: ");
					}
				});

		alert.show();
	}

	/**
	 * Metoda tworzaca liste i przypisujaca ja pod widok
	 */
	public void createList() {
		
		int len = sa.shoppingList.size();

		CustomRow_data.clear();
		float tmpSum = 0;

		for (int i = 0; i < len; i++) {
			String name = sa.shoppingList.get(i).getName();
			String price = null;
			try	{
				price = sa.shoppingList.get(i).getPrice();
				tmpSum += Float.parseFloat(price);
			} catch(Exception e)	{
				price = "0.00";
				tmpSum += Float.parseFloat(price);
			}
			
			CustomRow_data.add(new CustomRow(R.drawable.lvsel, name, price
					+ " zł"));
		}
		sumList.setText("" + tmpSum);
	}
}