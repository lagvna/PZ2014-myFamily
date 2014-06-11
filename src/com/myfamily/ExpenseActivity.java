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

/**
 * Klasa sluzaca do zarzadzania aktywnoscia zwiazana z wydatkami.
 * Implementuje TabListener, totez ma przypiete 3 fragmenty, pomiedzy ktorymi mozna sie poruszac.
 * @author lagvna
 *
 */
public class ExpenseActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	/**
	 * Adapter fragmentow aktywnosci wydatkow.
	 */
	public TabsPagerAdapterExpenses mAdapter;
	private ActionBar actionBar;
	private String[] tabs = { "Nowy wydatek", "Podsumowanie", "Wykresy" };
	private ProgressDialog progressDialog;

	/**
	 * Tablica uzytkownikow, dzieki ktorej po wlaczeniu aktywnosci, mozemy wybrac czyje wydatki chcemy przegladac.
	 */
	public ArrayList<User> users;
	/**
	 * Tablica wydatkow, do ktorego ladowane sa wydatki pobrane z serwera wg wybranych opcji.
	 */
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

	/**
	 * Metoda sluzaca usunieciu wydatku z serwera.
	 * @param id
	 */
	public void deleteExpense(String id) {
		new RemoveSth(id, "Wydatek").execute();
	}

	/**
	 * Metoda sluzaca pobraniu uzytkownikow nalezacych do rodziny, w ktorej aktualnie jestesmy zalogowani.
	 */
	public void getUsers() {
		new GetUsersForExpenses(ExpenseActivity.this).execute();
	}

	/**
	 * Metoda wywolywana jako wynik async taska, dzieki ktorej uzytkownicy sa przypisywani do rozwijanej listy.
	 * @param u
	 */
	public void setUsers(ArrayList<User> u) {
		for (int i = 0; i < u.size(); i++) {
			users.add(new User(u.get(i).getUserName(), u.get(i).getUserRole()));
		}

		mAdapter.sf.addItemsOnUserSpinner();
	}

	/**
	 * Metoda sluzaca pobraniu wydatkow z serwera dla zadanych opcji
	 * @param fromDate data od
	 * @param toDate data do
	 * @param login uzytkownik, dla ktorego chcemy pobrac wydatki
	 */
	public void getExpenses(String fromDate, String toDate, String login) {
		new GetExpenses(fromDate, toDate, login, ExpenseActivity.this)
				.execute();
	}

	/**
	 * Metoda wywolujaca async task sluzacy dodaniu wydatku do serwera.
	 * @param name nazwa wydatku
	 * @param description opis wydatku
	 * @param cost koszt wydatku
	 */
	public void addExpense(String name, String description, String cost) {
		new AddExpense(name, cost, description, ExpenseActivity.this).execute();
	}

	/**
	 * Metoda przypisujaca wydatki do listy wydatkow.
	 * @param e lista wydatkow zwroconych przez serwer
	 */
	public void assignExpenses(ArrayList<Expense> e) {
		for (int i = 0; i < e.size(); i++) {
			expenseList.add(new Expense(e.get(i).getId(), e.get(i).getName(), e
					.get(i).getNote(), e.get(i).getOwner(),
					e.get(i).getPrice(), e.get(i).getDate()));
		}

		mAdapter.sf.setExpenses();
	}

	/**
	 * Metoda sluzaca pokazaniu okienka czekania na odpowiedz z serwera
	 */
	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Łączenie z serwerem");
		progressDialog.show();
	}

	/**
	 * Metoda sluzaca zamknieciu okienka czekania na odpowiedz z serwera
	 */
	public void hideProgressDial() {
		progressDialog.dismiss();
	}

	/**
	 * Metoda sluzaca pokazaniu toasta z odpowiedzia od serwera
	 * @param responseText odpowiedz od serwera
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