package com.myfamily;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.adapters.GalleryImageAdapter;
import com.async.SendPicture;
import com.classes.Utils;

public class GalleryActivity extends Activity {
	private Utils utils;
	private ImageView selectedImage;
	private Integer[] mImageIds = { R.drawable.actionremove,
			R.drawable.addevent, R.drawable.eye, R.drawable.background, };
	private ArrayList<String> imagePaths = new ArrayList<String>();
	private int position = 0;
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		utils = new Utils(this);
		imagePaths = utils.getFilePaths();

		registerForContextMenu(findViewById(R.id.imageView1));
		//registerForContextMenu(findViewById(R.id.imageView1));
		
		initialize();

		Bitmap image = decodeFile(imagePaths.get(0));
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		Bitmap rotatedBitmap = Bitmap.createBitmap(image, 0, 0,
				image.getWidth(), image.getHeight(),
				matrix, true);
		selectedImage.setImageBitmap(rotatedBitmap);
		
	}
	
	public void initialize() {
		Gallery gallery = (Gallery) findViewById(R.id.gallery);
		selectedImage = (ImageView) findViewById(R.id.imageView1);
		gallery.setSpacing(1);
		gallery.setAdapter(new GalleryImageAdapter(this));

		// clicklistener for Gallery
		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				/*Toast.makeText(GalleryActivity.this,
						"Your selected position = " + position,
						Toast.LENGTH_SHORT).show();*/
				// show the selected Image
				// selectedImage.setImageResource(mImageIds[position]);
				Bitmap image = decodeFile(imagePaths.get(position));
				Matrix matrix = new Matrix();
				matrix.postRotate(90);
				Bitmap rotatedBitmap = Bitmap.createBitmap(image, 0, 0,
						image.getWidth(), image.getHeight(),
						matrix, true);
				selectedImage.setImageBitmap(rotatedBitmap);
			}
		});
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(Menu.NONE, R.id.b_item, Menu.NONE, "Usuń");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {

		case R.id.b_item:
			removePicture(0);
			return true;
		}
		return super.onContextItemSelected(item);
	}
	
	private void removePicture(int index) {
		
		String path = imagePaths.get(position);
		imagePaths.remove(0);
		new SendPicture(new String[]{path},this).execute();
		System.out.println("TUTAJ JEST MOJA SCIAZKA::"+path);
		//File file = new File(path);
		//file.delete();
		//initialize();
		
		
	}

	public static Bitmap decodeFile(String f) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;

			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = 250;

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			while (o.outWidth / scale / 2 >= REQUIRED_SIZE
					&& o.outHeight / scale / 2 >= REQUIRED_SIZE)
				scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}
	
	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Sprawdzanie danych");
		progressDialog.show();
	}

	/**
	 * method which hide Progress dialog
	 */
	public void hideProgressDial() {
		progressDialog.hide();
	}

	/**
	 * @param responseText
	 */
	public void showToast(String responseText) {

		Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG)
				.show();
	}
	

}
