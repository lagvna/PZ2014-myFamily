package com.async;

import java.io.IOException;

import org.json.JSONException;

import android.os.AsyncTask;

import com.classes.DataHolder;
import com.http.HttpHandler;
import com.http.JSONParser;
import com.myfamily.AddFamilyActivity;
import com.myfamily.GalleryActivity;
import com.myfamily.PhotosManagementActivity;

public class GetPicture extends AsyncTask<Void, Void, Void>{

	private PhotosManagementActivity callingActivity;
	private String responseText = null;
	private String dataArray[];
	 
	
	public GetPicture(String dataArray[],PhotosManagementActivity callingActivity) {
		this.callingActivity = callingActivity;
		this.dataArray = dataArray;
	}
	
	@Override
	protected void onPreExecute() {
		callingActivity.showProgressDial();
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		try {
			responseText = new HttpHandler(
					"http://malinowepi.no-ip.org/download_file.php", dataArray)
					.postGetPicture();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

		
		
	}

	@Override
	protected void onPostExecute(Void result) {
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
