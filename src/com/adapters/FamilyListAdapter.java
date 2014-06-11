package com.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.classes.Family;
import com.myfamily.R;
/**
 * Klasa adapter do zarządzania listą rodzin
 * @author KMACIAZE
 */
public class FamilyListAdapter extends ArrayAdapter<Family> {

	private Context context;
	private ArrayList<Family> familys;
	/**
	 * Główny konstruktor klasy
	 * @param context context klasy
	 * @param familys lista rodzin
	 */
	public FamilyListAdapter(Context context, ArrayList<Family> familys) {
		super(context, R.layout.family_row, familys);
		this.context = context;
		this.familys = familys;

	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;

		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.family_row, parent, false);
		}

		Family family = familys.get(position);
		if (family != null) {
			TextView familyName = (TextView) rowView
					.findViewById(R.id.familyNameTextView);
			if (familyName != null) {
				familyName.setText(family.getFamilyName());
			}
			TextView role = (TextView) rowView
					.findViewById(R.id.familyRoleTextView);
			if (role != null) {
				if (family.getRole() == 1)
					role.setText("opiekun");
				if (family.getRole() == 0)
					role.setText("podopieczny");
			}
		}
		return rowView;
	}

}
