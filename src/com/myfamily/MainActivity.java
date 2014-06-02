package com.myfamily;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.classes.DataHolder;
import com.classes.JSonReader;
import com.classes.JSonWriter;

public class MainActivity extends ListActivity {

	private String[] menuItems;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUri;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		DataHolder dataHolder = new DataHolder();
		JSonReader JSonReader = new JSonReader();
		JSonWriter jSonWriter = new JSonWriter();
		// JSonWriter.getInstance().deleteFile("events",
		// this.getApplicationContext());
		if (!JSonWriter.getInstance().ifExist("events",
				this.getApplicationContext())) {
			JSonWriter.getInstance().appendToFile("events",
					"0000-00-00 00:00:00" + "{\"events\":[]}", 0,
					this.getApplicationContext());
		}
		
		if (!JSonWriter.getInstance().ifExist("prizes",
				this.getApplicationContext())) {
			JSonWriter.getInstance().appendToFile("prizes",
					"0000-00-00 00:00:00" + "{\"prizes\":[]}", 0,
					this.getApplicationContext());
		}
		
		if (!JSonWriter.getInstance().ifExist("tasks",
				this.getApplicationContext())) {
			JSonWriter.getInstance().appendToFile("tasks",
					"0000-00-00 00:00:00" + "{\"tasks\":[]}", 0,
					this.getApplicationContext());
		}
		
		System.out.println(JSonReader.getInstance().readFile("events",
				this.getApplicationContext()));
		menuItems = new String[9];
		menuItems[0] = "Notatki";
		menuItems[1] = "Dodaj użytkownika";
		menuItems[2] = "Zakupy";
		menuItems[3] = "Wydarzenia";
		menuItems[4] = "Nagrody";
		menuItems[5] = "Wydatki";
		menuItems[6] = "Zadania";
		menuItems[7] = "Zdjecia";
		menuItems[8] = "Zrob zdjecie";

		// komentarz
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.element_cat, R.id.Row, menuItems);

		setListAdapter(adapter);
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					Intent i = new Intent(MainActivity.this,
							NotesActivity.class);
					MainActivity.this.startActivity(i);
				} else if (position == 1) {
					Intent i = new Intent(MainActivity.this,
							AddUserActivity.class);
					MainActivity.this.startActivity(i);
				} else if (position == 2) {
					Intent i = new Intent(MainActivity.this,
							ShoppingActivity.class);
					MainActivity.this.startActivity(i);
				} else if (position == 3) {
					Intent i = new Intent(MainActivity.this,
							CalendarActivity.class);
					MainActivity.this.startActivity(i);
				} else if (position == 4) {
					Intent i = new Intent(MainActivity.this,
							PrizeActivity.class);
					MainActivity.this.startActivity(i);
				} else if (position == 5)	{
					Intent i = new Intent(MainActivity.this,
							ExpenseActivity.class);
					MainActivity.this.startActivity(i);
				} else if (position == 6)	{
					Intent i = new Intent(MainActivity.this,
							TasksActivity.class);
					MainActivity.this.startActivity(i);
				} else if (position == 7)	{
					Intent i = new Intent(MainActivity.this,
							GalleryActivity.class);
					MainActivity.this.startActivity(i);
				} else if (position == 8)	{
					fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
					startActivityForResult(new Intent(
							
							MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
							MediaStore.EXTRA_OUTPUT, fileUri),
							CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				}


				
				
			}
		});
	}
	
	
	
	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"myFamily");
		System.out.println(mediaStorageDir.getAbsolutePath().toString());
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("myFamily", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}


}