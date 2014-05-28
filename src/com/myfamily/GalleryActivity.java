package com.myfamily;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.adapters.GalleryImageAdapter;
import com.classes.Utils;

public class GalleryActivity extends Activity {
	private Utils utils;
	ImageView selectedImage;
	private Integer[] mImageIds = { R.drawable.actionremove,
			R.drawable.addevent, R.drawable.eye, R.drawable.background, };
	private ArrayList<String> imagePaths = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		utils = new Utils(this);
		imagePaths = utils.getFilePaths();

		Gallery gallery = (Gallery) findViewById(R.id.gallery);
		selectedImage = (ImageView) findViewById(R.id.imageView1);
		gallery.setSpacing(1);
		gallery.setAdapter(new GalleryImageAdapter(this));

		// clicklistener for Gallery
		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(GalleryActivity.this,
						"Your selected position = " + position,
						Toast.LENGTH_SHORT).show();
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

}
