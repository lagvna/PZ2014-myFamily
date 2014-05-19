package com.myfamily;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.adapters.TabsPagerAdapterExpenses;
import com.async.AddExpense;
import com.async.GetExpenses;
import com.async.GetUsersForExpenses;
import com.async.RemoveSth;
import com.classes.Expense;
import com.classes.User;

public class ExpenseActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	public TabsPagerAdapterExpenses mAdapter;
	private ActionBar actionBar;
	private String[] tabs = { "Nowy wydatek", "Podsumowanie", "Wykresy" };
	private ProgressDialog progressDialog;

	public ArrayList<User> users;
	public ArrayList<Expense> expenseList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expense);

		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		mAdapter = new TabsPagerAdapterExpenses(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		users = new ArrayList<User>();
		expenseList = new ArrayList<Expense>();

		// Adding Tabs
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

		getUsers();
	}

	public void deleteExpense(String id) {
		new RemoveSth(id, "Wydatek").execute();
	}

	public void getUsers() {
		new GetUsersForExpenses(ExpenseActivity.this).execute();
	}

	public void setUsers(ArrayList<User> u) {
		for (int i = 0; i < u.size(); i++) {
			users.add(new User(u.get(i).getUserName(), u.get(i).getUserRole()));
		}

		mAdapter.sf.addItemsOnUserSpinner();
	}

	public void getExpenses(String fromDate, String toDate, String login) {
		new GetExpenses(fromDate, toDate, login, ExpenseActivity.this)
				.execute();
	}

	public void addExpense(String name, String description, String cost) {
		new AddExpense(name, cost, description, ExpenseActivity.this).execute();
	}

	public void assignExpenses(ArrayList<Expense> e) {
		for (int i = 0; i < e.size(); i++) {
			expenseList.add(new Expense(e.get(i).getId(), e.get(i).getName(), e
					.get(i).getNote(), e.get(i).getOwner(),
					e.get(i).getPrice(), e.get(i).getDate()));
		}

		mAdapter.sf.setExpenses();
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
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}