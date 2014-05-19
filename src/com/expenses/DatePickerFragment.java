package com.expenses;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;

import com.myfamily.R;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	private int button;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		button = getButton();

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	private int getButton() {
		Bundle extras = getArguments();
		int tmp = extras.getInt("button");

		return tmp;
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {

		String date = "";
		date = date + year + "-" + (month + 1) + "-" + day;
		if (button == 0) {
			((Button) getActivity().findViewById(R.id.fromButton))
					.setText(date);
		} else {
			((Button) getActivity().findViewById(R.id.toButton)).setText(date);
		}
	}
}