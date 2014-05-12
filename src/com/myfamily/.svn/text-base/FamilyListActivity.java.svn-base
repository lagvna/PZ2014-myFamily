package com.myfamily;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.adapters.FamilyListAdapter;
import com.classes.DataHolder;
import com.classes.Family;

public class FamilyListActivity extends Activity {

	private ListView familyListView;
	private Button addFamilyButton;
	private ArrayList<Family> familyList = new ArrayList<Family>();
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_family_list);
		context = this;
		if (getIntent().getExtras() != null) {
			for (int i = 0; i < getIntent().getExtras().size(); i++) {
				familyList.add(((Family) (getIntent().getExtras()
						.getSerializable("" + i))));
			}
		}

		familyListView = (ListView) findViewById(R.id.familyListView);
		addFamilyButton = (Button) findViewById(R.id.addFamilyButton);

		addFamilyButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(context,
						AddFamilyActivity.class), 1);
			}
		});

		familyListView.setOnItemClickListener(new OnItemClickListener() { // tutaj
																			// pobieramy
																			// id
																			// rodziny
																			// i
																			// przysylamy
																			// dalej
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// Toast.makeText(getApplicationContext(),
						// "FamilyID: " + familyList.get(position).getId(),
						// Toast.LENGTH_LONG)
						// .show();
						DataHolder.getInstance().setFamilyId(
								familyList.get(position).getId());
						startActivity(new Intent(context, MainActivity.class));
					}
				});
		initializedList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.family_list, menu);
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {
				finish();
			}
		}
	}// onActivityResult

	public void initializedList() {
		FamilyListAdapter adapter;
		if (!familyList.isEmpty()) {
			adapter = new FamilyListAdapter(this, familyList);
		} else {
			familyList
					.add(new Family(-1, "Nie należysz do żadnej rodziny", -1));
			adapter = new FamilyListAdapter(this, familyList);
		}

		familyListView.setAdapter(adapter);
	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

	public void showToast(String responseText) {

		Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG)
				.show();
	}

}