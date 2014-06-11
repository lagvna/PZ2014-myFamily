package com.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myfamily.ExpenseActivity;
import com.myfamily.R;

/**
 * Fragment zwiazany z aktywnoscia zarzadzania wydatkami, dzieki ktorej mozemy
 * dodac nowy wydatek do serwera.
 * 
 * @author lagvna
 * 
 */
public class NewExpenseFragment extends Fragment {

	private EditText title;
	private EditText description;
	private EditText expense;
	private Button accept;
	private ExpenseActivity ea;

	private String expTitle;
	private String expDescription;
	private String expCost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_new_expense,
				container, false);

		ea = (ExpenseActivity) getActivity();

		title = (EditText) rootView.findViewById(R.id.title_desc);
		description = (EditText) rootView.findViewById(R.id.desc_desc);
		expense = (EditText) rootView.findViewById(R.id.cost_desc);
		accept = (Button) rootView.findViewById(R.id.acceptButton);

		title.setSelected(false);
		description.setSelected(false);
		expense.setSelected(false);

		accept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!title.getText().toString().equals("")
						&& !expense.getText().toString().equals("")) {
					expTitle = title.getText().toString();
					expDescription = description.getText().toString();
					expCost = expense.getText().toString();

					ea.addExpense(expTitle, expDescription, expCost);

					title.setText("");
					description.setText("");
					expense.setText("");

					Toast.makeText(getActivity(), "Dodano nowy wydatek",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), "Wprowadź tytuł i/lub kwotę",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		return rootView;
	}
}