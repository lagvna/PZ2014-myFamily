package com.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.classes.Prize;
import com.myfamily.R;
/**
 * Klasa adapter do zarządzania listą zdobytych nagród
 * @author KMACIAZE
 */
public class MyPrizesAdapter extends ArrayAdapter<Prize> {
	private Context context;
	private ArrayList<Prize> prizes;
	/**
	 * Główny konstruktor klasy
	 * @param context context aktywności
	 * @param prizes lista z nagrodami
	 */
	public MyPrizesAdapter(Context context, ArrayList<Prize> prizes) {
		super(context, R.layout.my_prize_row, prizes);
		this.context = context;
		this.prizes = prizes;

	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;

		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.my_prize_row, parent, false);
		}

		Prize prize = prizes.get(position);
		if (prize != null) {
			TextView prizeName = (TextView) rowView
					.findViewById(R.id.myPrizeRowTextView);
			if (prizeName != null) {
				prizeName.setText(prize.getName());
			}
			TextView points = (TextView) rowView.findViewById(R.id.myPrizeDateRowTextView);
			if (points != null) {
				points.setText(prize.getGained_date());
			}
		}
		return rowView;
	}
}

