package com.async;

import java.io.IOException;

import android.os.AsyncTask;

import com.http.HttpHandler;
import com.myfamily.GalleryActivity;

/**
 * Klasa wywolujaca zdarzenie asynchroniczne odpowiadajace za wyslanie zdjecia na serwer
 * @author lagvna
 *
 */
public class SendPicture1 extends AsyncTask<String, Integer, String>{

	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	
	private GalleryActivity callingActivity;
	private String responseText = null;
	private String dataArray[];
	
	
	public SendPicture1(String dataArray[],GalleryActivity callingActivity) {
		this.callingActivity = callingActivity;
		this.dataArray = dataArray;
	}
	
	@Override
	protected void onPreExecute() {
		callingActivity.showProgressDial();
		callingActivity.showDialog(DIALOG_DOWNLOAD_PROGRESS);
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
			responseText = new HttpHandler(
					"http://malinowepi.no-ip.org/upload_file.php", dataArray)
					.postPicureSend();
			//publishProgress(callingActivity.progress);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

		
		
	}
	
	protected void onProgressUpdate(String... progress) {        
	    callingActivity.mProgressDialog.setProgress(Integer.parseInt(progress[0]));
	}

	protected void onPostExecute(String result) {
		callingActivity.hideProgressDial();
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
