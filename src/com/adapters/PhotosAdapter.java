package com.adapters;

import java.util.ArrayList;

import com.classes.Photo;
import com.classes.Prize;
import com.myfamily.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/**
 * Adapter listy odpowiedzialny za prawidlowe
 * wyswietlanie listy stringow, ktore sa nazwami zdjec na serwerze 
 * @author kwachu
 *
 */
public class PhotosAdapter extends ArrayAdapter<Photo> {

	private ArrayList<Photo> photos;
	private Context context;
	/**
	 * Główny konstruktor klasy
	 * @param context context aktywności
	 * @param photos lista ze zdjęciami
	 */
	public PhotosAdapter(Context context, ArrayList<Photo> photos) {
		super(context, R.layout.photo_row, photos);
		this.context = context;
		this.photos = photos;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;

		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.photo_row, parent, false);
		}

		Photo photo = photos.get(position);
		if (photo != null) {
			TextView photoName = (TextView) rowView
					.findViewById(R.id.photoRowTextView);
			if (photoName != null) {
				photoName.setText(photo.getName());
			}
			TextView points = (TextView) rowView.findViewById(R.id.photoDateRowTextView);
			if (points != null) {
				points.setText(photo.getDate());
			}
		}
		return rowView;
	}
}
