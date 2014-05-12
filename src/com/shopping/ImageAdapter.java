package com.shopping;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.classes.Product;
import com.myfamily.R;

public class ImageAdapter extends BaseAdapter {
	private int NumberOfItems;
	private Bitmap[] bitmap;
	private Context context;
	private LayoutInflater layoutInflater;
	private ArrayList<Product> products;

	public ImageAdapter(Context c, ArrayList<Product> products) {
		context = c;
		layoutInflater = LayoutInflater.from(context);
		this.products = products;
		NumberOfItems = products.size();
		bitmap = new Bitmap[NumberOfItems];

		for (int i = 0; i < NumberOfItems; i++) { // TODO trzeba bedzie
													// zahardkodowac obrazki
			bitmap[i] = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.ic_launcher);
		}
	}

	public Product getProduct(int pos) {
		Product p = products.get(pos);

		return p;
	}

	@Override
	public int getCount() {
		return bitmap.length;
	}

	@Override
	public Object getItem(int position) {
		return bitmap[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View grid;

		if (convertView == null) {
			grid = new View(context);
			grid = layoutInflater.inflate(R.layout.image, null);
		} else {
			grid = (View) convertView;
		}

		ImageView imageView = (ImageView) grid.findViewById(R.id.image);
		imageView.setImageBitmap(bitmap[position]);
		TextView textView = (TextView) grid.findViewById(R.id.title);
		textView.setText(products.get(position).getName());

		return grid;
	}
}