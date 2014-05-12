package com.fragments;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.myfamily.R;
import com.myfamily.ShoppingActivity;
import com.shopping.CustomRow;
import com.shopping.ListViewAdapter;

public class ShoppingListFragment extends Fragment {
	private ArrayList<CustomRow> CustomRow_data;
	public ListViewAdapter listAdapter;
	ShoppingActivity sa;
	public TextView sumList;
	public TextView sumUser;
	private Button editShopping;
	private Button endShopping;
	public String listName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_shopping_list,
				container, false);

		sa = (ShoppingActivity) getActivity();

		listName = "";
		editShopping = (Button) rootView.findViewById(R.id.editPrice);
		endShopping = (Button) rootView.findViewById(R.id.endShopping);
		sumUser = (TextView) rootView.findViewById(R.id.sumUserList);
		sumList = (TextView) rootView.findViewById(R.id.sumFromList);
		CustomRow_data = new ArrayList<CustomRow>();
		createList();

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

		return rootView;
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

	public void createList() {
		int len = sa.shoppingList.size();

		CustomRow_data.clear();
		float tmpSum = 0;

		for (int i = 0; i < len; i++) {
			String name = sa.shoppingList.get(i).getName();
			String price = sa.shoppingList.get(i).getPrice();
			tmpSum += Float.parseFloat(price);

			CustomRow_data.add(new CustomRow(R.drawable.lvsel, name, price
					+ " zł"));
		}
		sumList.setText("" + tmpSum);
	}
}