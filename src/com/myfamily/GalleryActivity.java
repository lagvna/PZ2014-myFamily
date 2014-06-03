package com.myfamily;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RecoverySystem.ProgressListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adapters.GalleryImageAdapter;
import com.async.SendPicture1;
import com.classes.CountingMultipartEntity;
import com.classes.DataHolder;
import com.classes.Utils;

public class GalleryActivity extends Activity {

	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;

	private Utils utils;
	private ImageView selectedImage;
	private Integer[] mImageIds = { R.drawable.actionremove,
			R.drawable.addevent, R.drawable.eye, R.drawable.background, };
	private ArrayList<String> imagePaths = new ArrayList<String>();
	private int position = 0;
	private ProgressDialog progressDialog;
	private ProgressBar progresBar;
	public ProgressDialog mProgressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		utils = new Utils(this);
		imagePaths = utils.getFilePaths();

		registerForContextMenu(findViewById(R.id.imageView1));
		// registerForContextMenu(findViewById(R.id.imageView1));
		// new GetPicture(null,this).execute();
		initialize();
		// System.out.println("ROZMIAR LISTY!!!::::"+imagePaths.size());
		/*
		 * if(!imagePaths.isEmpty()) { Bitmap image =
		 * decodeFile(imagePaths.get(0)); Matrix matrix = new Matrix();
		 * matrix.postRotate(90); Bitmap rotatedBitmap =
		 * Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(),
		 * matrix, true); selectedImage.setImageBitmap(rotatedBitmap); }
		 */

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS:

			return mProgressDialog;
		default:
			return null;
		}
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

				Bitmap image = decodeFile(imagePaths.get(position));
				Matrix matrix = new Matrix();
				matrix.postRotate(90);
				Bitmap rotatedBitmap = Bitmap.createBitmap(image, 0, 0,
						image.getWidth(), image.getHeight(), matrix, true);
				selectedImage.setImageBitmap(rotatedBitmap);
			}
		});

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(Menu.NONE, R.id.b_item, Menu.NONE, "Wyślij na serwer");
		menu.add(Menu.NONE, R.id.c_item, Menu.NONE, "Usuń");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {

		case R.id.c_item:
			removePicture(0);
			return true;

		case R.id.b_item:
			sendPicture();
			return true;

		}
		return super.onContextItemSelected(item);
	}

	private void removePicture(int index) {

		String path = imagePaths.get(position);
		imagePaths.remove(0);
		// new SendPicture(new String[]{path},this).execute();
		// System.out.println("TUTAJ JEST MOJA SCIAZKA::"+path);
		File file = new File(path);
		file.delete();
		initialize();

	}

	private void sendPicture() {
		String path = imagePaths.get(position);
		new SendPicture(new String[] { path }, this).execute();
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

	class SendPicture extends AsyncTask<HttpResponse, Integer, String> {

		public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
		long totalSize;
		private GalleryActivity callingActivity;
		private String responseText = null;
		private String dataArray[];

		public SendPicture(String dataArray[], GalleryActivity callingActivity) {
			this.callingActivity = callingActivity;
			this.dataArray = dataArray;
		}

		@Override
		protected void onPreExecute() {
			mProgressDialog = new ProgressDialog(GalleryActivity.this);
			mProgressDialog.setMessage("Poczekaj synu...!");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(HttpResponse... arg0) {

		
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(
					"http://malinowepi.no-ip.org/upload_file.php");

			try
			{
				CountingMultipartEntity multipartContent = new CountingMultipartEntity(new ProgressListener() {
					
					@Override
					public void onProgress(int progress) {
						mProgressDialog.setProgress((int) ((progress / (float) totalSize) * 100));
						
					}
				});
 
				// We use FileBody to transfer an image
				multipartContent.addPart("file", new FileBody(new File(dataArray[0])));
				multipartContent.addPart("session", new StringBody(DataHolder.getInstance()
						.getSession()));
				multipartContent.addPart("family_Id", new StringBody(DataHolder.getInstance()
						.getStringFamilyId()));
				totalSize = multipartContent.getContentLength();
 
				// Send it
				httpPost.setEntity(multipartContent);
				HttpResponse response = httpClient.execute(httpPost, localContext);
				String serverResponse = EntityUtils.toString(response.getEntity());
 
				responseText = serverResponse;
				return  serverResponse+"TUTAJ111";
			}
 
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return "TUTAJ";

		}
		protected void onProgressUpdate(String... progress) {
			mProgressDialog.setProgress(Integer
					.parseInt(progress[0]));
		}

		protected void onPostExecute(String result) {
			mProgressDialog.dismiss();
			callingActivity.showToast(responseText);
		}

	}

}
