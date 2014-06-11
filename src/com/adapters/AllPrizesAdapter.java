package com.adapters;

import java.util.ArrayList;

import com.classes.Prize;
import com.myfamily.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Klasa adapter do zarządzania listą nagród
 * @author KMACIAZE
 */
public class AllPrizesAdapter extends ArrayAdapter<Prize> {
	private Context context;    
    private ArrayList<Prize> prizes;
    /**
     * Główny konstruktor metody
     * @param context context aktywności
     * @param prizes lista nagród
     */
    public AllPrizesAdapter(Context context, ArrayList<Prize> prizes) {
        super(context,R.layout.family_row, prizes);
        this.context = context;
        this.prizes = prizes;

    }
    public View getView(int position, View convertView, ViewGroup parent) 
	{
		
		View rowView = convertView;
		
		
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.prize_row, parent, false);
		}
	
		Prize prize = prizes.get(position);
		if(prize != null) {
			TextView prizeName = (TextView) rowView.findViewById(R.id.textView1);
			if(prizeName != null) {
				prizeName.setText(prize.getName());
			}
			TextView points = (TextView) rowView.findViewById(R.id.textView2);
			if(points != null) {
				points.setText(prize.getPointsToGain());
			}
		}
		return rowView;		
	}
}

