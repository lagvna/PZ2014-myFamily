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

/**
 * Klasa adaptera dla widoku produktow, ktory wyswietla odpowiednie ikonki produktow predefiniowanych oraz ich nazwy
 * @author lagvna
 *
 */
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

		for (int i = 0; i < NumberOfItems; i++) {
			if (products.get(i).getName().equals("Chleb wieloziarnisty")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.chlebwieloziarnisty);
			} else if (products.get(i).getName().equals("Chleb graham")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.chlebgraham);
			} else if (products.get(i).getName().equals("Ciabata")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.ciabatta);
			} else if (products.get(i).getName().equals("Bagietka")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.bagietka);
			} else if (products.get(i).getName().equals("Mleko 3%")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.mleko);
			} else if (products.get(i).getName().equals("Mleko 1%")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.mleko);
			} else if (products.get(i).getName().equals("Jogurt naturalny")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.jogurt);
			} else if (products.get(i).getName().equals("Jogurt owocowy")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.jogurt);
			} else if (products.get(i).getName().equals("Śmietana")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.smietana);
			} else if (products.get(i).getName().equals("Serek wiejski")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.serekwiejski);
			} else if (products.get(i).getName().equals("Pasta do zębów")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.pastadozebow);
			} else if (products.get(i).getName().equals("Proszek do prania")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.proszekdoprania);
			} else if (products.get(i).getName().equals("Krem do rąk")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.kremdorak);
			} else if (products.get(i).getName().equals("Szampon")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.szampon);
			} else if (products.get(i).getName().equals("Dezodorant")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.dezodorant);
			} else if (products.get(i).getName().equals("Woda 0.5l")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.woda);
			} else if (products.get(i).getName().equals("Woda 1.5l")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.woda);
			} else if (products.get(i).getName().equals("Coca-cola")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.cocacola);
			} else if (products.get(i).getName().equals("Sok pomarańczowy")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.sokpomaranczowy);
			} else if (products.get(i).getName().equals("Piwo")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.piwo);
			} else if (products.get(i).getName().equals("Wódka")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.wodka);
			} else if (products.get(i).getName().equals("Jabłka 1kg")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.jablka);
			} else if (products.get(i).getName().equals("Papierosy")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.papierosy);
			} else if (products.get(i).getName().equals("Spodnie")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.spodnie);
			} else if (products.get(i).getName().equals("Ręcznik")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.recznik);
			} else if (products.get(i).getName().equals("Banany 1kg")) {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.banany);
			} else {
				bitmap[i] = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.produkt);
			}
		}
	}

	/**
	 * Metoda zwracajaca produkt na danej (kliknietej) pozycji
	 * @param pos pozycja kliknietego produktu
	 * @return wybrany produkt
	 */
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