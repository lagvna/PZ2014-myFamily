package com.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.classes.Product;
import com.classes.ShoppingList;
import com.myfamily.R;
import com.myfamily.ShoppingActivity;
import com.shopping.CustomRow;
import com.shopping.ListViewAdapter;

public class ArchiveFragment extends Fragment {
	private ArrayList<CustomRow> CustomRow_data;
	private ShoppingActivity sa;
	private Button getArchives;
	public ListViewAdapter listAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_archive, container,
				false);

		sa = (ShoppingActivity) getActivity();

		CustomRow_data = new ArrayList<CustomRow>();
		createList();

		getArchives = (Button) rootView.findViewById(R.id.getArchives);
		ListView lv = (ListView) rootView.findViewById(R.id.listArchive);
		listAdapter = new ListViewAdapter(getActivity(), R.layout.element,
				CustomRow_data);
		lv.setAdapter(listAdapter);

		registerForContextMenu(rootView.findViewById(R.id.listArchive));

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				CustomRow sl = (CustomRow) parent.getAdapter()
						.getItem(position);
				String name = sl.title;
				int slId = findList(name);
				sa.getShoppingList(slId);
				// showPopup(getView());
			}
		});

		getArchives.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sa.getShoppingLists();
			}
		});

		return rootView;
	}
	
	private int findList(String name) {

		for (int i = 0; i < sa.shoppingLists.size(); i++) {
			if (sa.shoppingLists.get(i).getName().equals(name)) {
				return sa.shoppingLists.get(i).getId();
			}
		}
		return -1;
	}

	public void preShowPopup(ShoppingList sl) {
		String name = sl.getName();
		String cost = "Suma: " + sl.getTotalCost() + " zł";
		String products = "";

		for (int i = 0; i < sl.getProducts().size(); i++) {
			String prodName = sl.getProducts().get(i).getName();
			String prodCost = sl.getProducts().get(i).getPrice();

			products += prodName + " " + prodCost + " zł\n";
		}

		showPopup(getView(), name, products, cost, sl);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(Menu.NONE, R.id.b_item, Menu.NONE, "Usuń");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {

		case R.id.b_item:
			deleteList((int) menuInfo.position);
			return true;
		}
		return super.onContextItemSelected(item);
	}

	private void deleteList(int id) {
		searchForElement(id);
	}

	private void searchForElement(int id) {
		String name = CustomRow_data.get(id).title;
		String toDel = "";
		for (int i = 0; i < sa.shoppingLists.size(); i++) {
			if (sa.shoppingLists.get(i).getName().equals(name)) {
				toDel = "" + sa.shoppingLists.get(i).getId();
			}
		}

		sa.deleteShoppingList(toDel);
	}

	public void createList() {
		int len = sa.shoppingLists.size();

		CustomRow_data.clear();

		for (int i = 0; i < len; i++) {
			String name = sa.shoppingLists.get(i).getName();
			String date = sa.shoppingLists.get(i).getOwner() + " "
					+ sa.shoppingLists.get(i).getDate();
			System.out.println(name);
			System.out.println(date);
			CustomRow_data.add(new CustomRow(R.drawable.lvsel, name, date));
		}
	}

	public void showPopup(View anchorView, String name, String products,
			String cost, final ShoppingList sl) {
		WindowManager wm = (WindowManager) getView().getContext()
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int popupWidth = size.x * 4 / 5;
		int popupHeight = size.y * 1 / 2;

		View popupView = getLayoutInflater(getArguments()).inflate(
				R.layout.popup_main, null);

		PopupWindow popupWindow = new PopupWindow(popupView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		popupWindow.setWidth(popupWidth);
		popupWindow.setHeight(popupHeight);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new ColorDrawable());

		((TextView) popupWindow.getContentView().findViewById(R.id.ust))
				.setText(name);
		((TextView) popupWindow.getContentView().findViewById(R.id.dlDesc))
				.setText(products);
		((TextView) popupWindow.getContentView().findViewById(R.id.dlSum))
				.setText(cost);
		((Button) popupWindow.getContentView().findViewById(R.id.copyList))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						sa.shoppingList.clear();
						for (int i = 0; i < sl.getProducts().size(); i++) {
							int id = sl.getProducts().get(i).getId();
							String name = sl.getProducts().get(i).getName();
							String category = sl.getProducts().get(i)
									.getCategory();
							String price = sl.getProducts().get(i).getPrice();

							sa.shoppingList.add(new Product(id, name, category,
									price));
						}
						sa.mAdapter.slf.listAdapter.notifyDataSetChanged();
						sa.mAdapter.slf.createList();

						Toast.makeText(getActivity(), "Skopiowano!",
								Toast.LENGTH_SHORT).show();
					}
				});

		popupWindow.showAtLocation(anchorView, Gravity.CENTER, 10, 10);
	}
}