package com.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.classes.DataHolder;
import com.expenses.DatePickerFragment;
import com.myfamily.ExpenseActivity;
import com.myfamily.R;

public class SummaryFragment extends Fragment {

	private Spinner user;
	private Button dateFrom;
	private Button dateTo;
	private Button previous;
	private Button next;
	private Button accept;
	private Button delete;
	private TextSwitcher noteArea;
	private int current;
	private TextView totalExpenses;

	private String userTxt;
	private ExpenseActivity ea;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_summary, container,
				false);

		user = (Spinner) rootView.findViewById(R.id.userList);
		dateFrom = (Button) rootView.findViewById(R.id.fromButton);
		dateTo = (Button) rootView.findViewById(R.id.toButton);
		previous = (Button) rootView.findViewById(R.id.prevExp);
		next = (Button) rootView.findViewById(R.id.nextExp);
		delete = (Button) rootView.findViewById(R.id.delExp);
		accept = (Button) rootView.findViewById(R.id.acceptChoices);
		totalExpenses = (TextView) rootView.findViewById(R.id.expenseAll_desc);
		noteArea = (TextSwitcher) rootView.findViewById(R.id.title_desc);

		ea = (ExpenseActivity) getActivity();

		noteArea.setFactory(new ViewFactory() {
			@Override
			public View makeView() {
				TextView myText = new TextView(getActivity());
				myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
				myText.setTextSize(10);
				return myText;
			}
		});

		Animation in = AnimationUtils.loadAnimation(getActivity(),
				R.anim.in_left);
		Animation out = AnimationUtils.loadAnimation(getActivity(),
				R.anim.out_right);

		noteArea.setInAnimation(in);
		noteArea.setOutAnimation(out);

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (current > 0) {
					current--;
					noteArea.setText(ea.expenseList.get(current).getName()
							+ "\n" + ea.expenseList.get(current).getNote()
							+ "\n" + ea.expenseList.get(current).getPrice()
							+ " zł\n" + ea.expenseList.get(current).getDate());
				}
			}
		});

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (current < ea.expenseList.size() - 1) {
					current++;
					noteArea.setText(ea.expenseList.get(current).getName()
							+ "\n" + ea.expenseList.get(current).getNote()
							+ "\n" + ea.expenseList.get(current).getPrice()
							+ " zł\n" + ea.expenseList.get(current).getDate());
				}
			}
		});

		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!ea.expenseList.isEmpty())	{
					if (current >= 0) {
						String toDel = "" + ea.expenseList.get(current).getId();
						ea.deleteExpense(toDel);
						if(!ea.expenseList.isEmpty())	{
							ea.expenseList.remove(current);
							setExpenses();
						}
					}
				} else	{
					Toast.makeText(getActivity(), "Musisz najpierw pobrać wydatki!",
							Toast.LENGTH_SHORT).show();
				}
				/*
				 * current--; if (current < 0) { noteArea.setText(""); } else {
				 * noteArea.setText(ea.expenseList.get(current).getNote() + "\n"
				 * + ea.expenseList.get(current).getPrice() + " zł" + "\n" +
				 * ea.expenseList.get(current).getDate()); } }
				 */}
		});

		dateFrom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment();
				Bundle extras = new Bundle();
				extras.putInt("button", 0);
				newFragment.setArguments(extras);
				newFragment.show(getChildFragmentManager(), "datePicker");
			}
		});

		dateTo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment();
				Bundle extras = new Bundle();
				extras.putInt("button", 1);
				newFragment.setArguments(extras);
				newFragment.show(getChildFragmentManager(), "datePicker");
			}
		});

		accept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String fromDate = getFromDate();
				String toDate = getToDate();

				String us = DataHolder.getInstance().getLogin();

				userTxt = user.getSelectedItem().toString();

				if (userTxt.equals(us)) {
					userTxt = "0";
				}

				String login = userTxt;

				if (fromDate.equals("") || toDate.equals("")) {
					Toast.makeText(getActivity(), "Podaj datę!",
							Toast.LENGTH_SHORT).show();
				} else {
					ea.expenseList.clear();
					ea.getExpenses(fromDate, toDate, login);
				}

			}
		});

		return rootView;
	}

	public void setExpenses() {
		current = ea.expenseList.size() - 1;

		if (current >= 0) {
			noteArea.setText(ea.expenseList.get(current).getName() + "\n"
					+ ea.expenseList.get(current).getNote() + "\n"
					+ ea.expenseList.get(current).getPrice() + " zł\n"
					+ ea.expenseList.get(current).getDate());
		}

		totalExpenses.setText("" + countTotal() + " zł");
		ea.mAdapter.cf.updateGraph();
	}

	public float countTotal() {
		float total = 0;

		for (int i = 0; i < ea.expenseList.size(); i++) {
			total += Float.parseFloat(ea.expenseList.get(i).getPrice());
		}
		return total;
	}

	public void addListenersOnSpinnersItemSelection() {
		user.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private String getFromDate() {
		return dateFrom.getText().toString();
	}

	private String getToDate() {
		return dateTo.getText().toString();
	}

	public void addItemsOnUserSpinner() {

		List<String> list = new ArrayList<String>();
		for (int i = 0; i < ea.users.size(); i++) {
			list.add(ea.users.get(i).getUserName());
		}

		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_dropdown_item,
				list);
		user.setAdapter(spinnerArrayAdapter);

	}
}