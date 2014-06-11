package com.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.classes.User;
import com.myfamily.R;
/**
 * Klasa adapter do zarządzania listą użytkowników w Spinnerze
 * @author KMACIAZE
 */
public class UserSpinnerAdapter extends ArrayAdapter<User> {
	private Activity context;
	private ArrayList<User> data = null;
	/**
	 * Główny konstruktor klasy
	 * @param context context aktywności
	 * @param resource id
	 * @param data lista z użytkownikami
	 */
    public UserSpinnerAdapter(Activity context, int resource, ArrayList<User> data)
    {
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
        return super.getView(position, convertView, parent);   
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {   // This view starts when we click the spinner.
        View row = convertView;
        if(row == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_layout, parent, false);
        }

        User item = data.get(position);

        if(item != null)
        {   // Parse the data from each object and set it.
            TextView userNameTextView = (TextView) row.findViewById(R.id.userNameSpinner);
            if(userNameTextView != null)
            	userNameTextView.setText(item.getUserName());

        }

        return row;
    }
}

