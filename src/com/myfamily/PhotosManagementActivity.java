package com.myfamily;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adapters.PhotosAdapter;
import com.async.GetPicture;
import com.classes.Photo;
import com.http.HttpHandler;
import com.http.JSONParser;

public class PhotosManagementActivity extends Activity {

	private ListView photosListView;
	private Context context;
	private ArrayList<Photo> photosList = new ArrayList<Photo>();
	private ProgressDialog progressDialog;
	private PhotosAdapter adapter;
	private Photo selectedPhoto;
	private String noPhotosString = "Brak zdjęć!";
	public ProgressDialog mProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_photos_management);
		context = this;
		photosListView = (ListView) findViewById(R.id.photosList);
		registerForContextMenu(findViewById(R.id.photosList));
        	
       	
		new GetPhotos().execute();
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(Menu.NONE, R.id.b_item, Menu.NONE, "Pobierz");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {

		case R.id.b_item:
			//deleteList((int) menuInfo.position);
			new GetPicture(new String[]{photosList.get((int) menuInfo.position).getId()},this).execute();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photos_management, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private void initializeList() {
		if(photosList != null && !photosList.isEmpty()) {
			adapter = new PhotosAdapter(context, photosList);
		} else {
			ArrayList<Photo> tempList = new ArrayList<Photo>();
			tempList.add(new Photo(noPhotosString ,"",""));
			adapter = new PhotosAdapter(context, tempList);
		}
		photosListView.setAdapter(adapter);
	}
	class GetPhotos extends AsyncTask<Void, Void, Void> {

		private String responseText;
		private String[] dataArray;

		public GetPhotos() {
			
			/*String str = JSonReader.getInstance().readFile("prizes", context);
			String temp = str.substring(0, 19);
			if (temp.equals("0000-00-00 00:00:00")) {
				dataArray[3] = "0";
			} else {
				dataArray[3] = temp;
			}*/
		}

		@Override
		protected void onPreExecute() {
			showProgressDial();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				responseText = new HttpHandler(
						"http://malinowepi.no-ip.org/get_photos.php", dataArray)
						.postDataGetPhotos();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			hideProgressDial();
			showToast(responseText);
			JSONParser jp = new JSONParser(responseText);
			try {
				ArrayList resultSet = jp.getPhotosResult();
				if (resultSet.size() == 2) {
					photosList = (ArrayList<Photo>) resultSet.get(0);
					//jp.makePrizesJSon(myPrizesList,context,1);
				} else if (resultSet.size() == 1) {
					showToast((String) ((ArrayList) resultSet.get(0)).get(1));
				}
				
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			initializeList();
		}

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
