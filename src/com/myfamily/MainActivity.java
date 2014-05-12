package com.myfamily;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	private String[] menuItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		menuItems = new String[4];
		menuItems[0] = "Notatki";
		menuItems[1] = "Dodaj użytkownika";
		menuItems[2] = "Zakupy";
		menuItems[3] = "Wydarzenia";

		
		//komentarz
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
				} else if (position == 1){
					Intent i = new Intent(MainActivity.this,
							AddUserActivity.class);
					MainActivity.this.startActivity(i);
				} else if (position == 2){
					/*Intent i = new Intent(MainActivity.this,
							CalendarActivity.class);
					MainActivity.this.startActivity(i);*/
				} else if (position == 3){
					Intent i = new Intent(MainActivity.this,
							CalendarActivity.class);
					MainActivity.this.startActivity(i);
				}

			}
		});
	}

}