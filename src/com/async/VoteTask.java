package com.async;

import java.io.IOException;

import android.os.AsyncTask;

import com.fragments.FragmentCurrentTasks;
import com.fragments.FragmentOldTasks;
import com.http.HttpHandler;
import com.myfamily.AddFamilyActivity;

public class VoteTask extends AsyncTask<Void, Void, Void> {

	private AddFamilyActivity callingActivity;
	private String responseText = null;
	private String dataArray[];
	FragmentCurrentTasks ca = null;
	FragmentOldTasks ca1 = null;

	public VoteTask(String taskId, FragmentCurrentTasks ca) {
		dataArray = new String[1];
		dataArray[0] = taskId;
		this.ca = ca;
	}

	public VoteTask(String taskId, FragmentOldTasks ca) {
		dataArray = new String[1];
		dataArray[0] = taskId;
		this.ca1 = ca;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		try {

			responseText = new HttpHandler(
					"http://malinowepi.no-ip.org/evaluate_task.php", dataArray)
					.postDataVoteTask();
			System.out.println(responseText);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
    protected void onPostExecute(Void result) {
    	if(ca!=null) {
    		ca.showToast(responseText);
    	}
        if(ca1!=null) {
        	ca1.showToast(responseText);
        }
    }

}
