package com.async;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.classes.DataHolder;
import com.myfamily.PhotosManagementActivity;

/**
 * AsyncTask pozwalajacy na wywoalnie metody odpowiedzialnej
 * za pobieranie danych z serwera. Wywoluje metode, ktora 
 * pozwala pobierac zdjecia z serwera
 * @author kwachu
 *
 */
public class GetPicture extends AsyncTask<HttpResponse, Integer, String>{

	private PhotosManagementActivity callingActivity;
	private String responseText = null;
	private String dataArray[];
	private HttpEntity entity;
	
	public GetPicture(String dataArray[],PhotosManagementActivity callingActivity) {
		this.callingActivity = callingActivity;
		this.dataArray = dataArray;
	}
	
	@Override
	protected void onPreExecute() {
		callingActivity.mProgressDialog = new ProgressDialog(callingActivity);
		callingActivity.mProgressDialog.setMessage("Poczekaj synu...!");
		callingActivity.mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		callingActivity.mProgressDialog.setCancelable(false);
		callingActivity.mProgressDialog.show();
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(HttpResponse... arg0) {
		/*try {
			responseText = new HttpHandler(
					"http://malinowepi.no-ip.org/download_file.php", dataArray)
					.postGetPicture();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;*/
		
		
		long filesize;
		long totalSize;
		long len;
		long totalDownload = 0;
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
		FileOutputStream fileOutput;
		String path = android.os.Environment.getExternalStorageDirectory()
				+ File.separator + DataHolder.PHOTO_ALBUM;
		byte[] buffer = new byte[200000];
		String url = "http://malinowepi.no-ip.org/download_file.php";

		DefaultHttpClient client = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpPost post = new HttpPost(url);

		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("file_Id", dataArray[0]));
		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			HttpResponse execute = client.execute(post,localContext);
			InputStream content = execute.getEntity().getContent();

			totalSize = filesize = execute.getEntity().getContentLength();
			System.out.println("FILE SIZE"+filesize+"");
			fileOutput = new FileOutputStream(new File(path,
					"file_copyformserver.jpg"));

			/*while ((len = content.read(buffer, 0, 1024)) > 0)*/ 
			
			while (filesize>0){
				filesize -= len = content.read(buffer, 0, 200000);
				totalDownload += len;
				callingActivity.mProgressDialog.setProgress((int) ((totalDownload / (float) totalSize) * 100));
				fileOutput.write(buffer, 0, (int)len);
				Thread.sleep(100);
			}
			
			fileOutput.close();
			entity = execute.getEntity();
			return "ok";//EntityUtils.toString(entity);

		} catch (Exception e) {
			e.printStackTrace();
		}


		return "cos nie tak";
	}

	@Override
	protected void onPostExecute(String result) {
		callingActivity.hideProgressDial();
		callingActivity.mProgressDialog.dismiss();
		callingActivity.showToast(responseText);
		
		//callingActivity.showToast(DataHolder.getInstance().getSession());
		//JSONParser jp = new JSONParser(responseText);
		/*try {
			String loginResult = jp.getAddFamilyResult();
			String tempArr[] = loginResult.split(":");
			if (tempArr[0].equals("0")) {
				callingActivity.showToast("Cos poszlo zle: " + tempArr[1]);
			} else {
				callingActivity.familyCreated();
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}
	
	
}
