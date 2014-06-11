package com.myfamily;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONException;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.adapters.UserSpinnerAdapter;
import com.classes.User;
import com.http.HttpHandler;
import com.http.JSONParser;

/**
 * @author kwachu
 * Aktywnosc odpowiedzialna, ze dodawanie nowych zadan.
 */
public class AddTaskActivity extends Activity implements DatePickerDialog.OnDateSetListener {

	private Button dataPicker;
	private Button addTask;
	private EditText pointsEditText;
	private EditText taskNameEditText;
	private EditText whatToDoEditText;
	private Context context;
	private Spinner userSpinner;
	private Spinner categorySpinner;
	private boolean userChanged = true;
	protected User selectedUser;
	private ArrayList<User> familyUsers = new ArrayList<User>();
	private String taksTo = "";
	private String taskName ="";
	private String whatToDo = "";
	private String points = "";
	private String deadline ="";
	private String category = "Sprzątanie";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		context = this;
		dataPicker = (Button) findViewById(R.id.getDateButton);
		addTask = (Button) findViewById(R.id.addTaskButton);
		taskNameEditText = (EditText) findViewById(R.id.newTaskNameTextView);
		whatToDoEditText = (EditText) findViewById(R.id.taskNoteEditText1);
		addTask.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AddTask().execute();
				showToast("Zadanie zostało dodane");
				TasksActivity.getInstance().finish();
				finish();
				startActivity(new Intent(context, TasksActivity.class));
			}
		});
		
		dataPicker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(0);
			}
		});
		
		pointsEditText = (EditText) findViewById(R.id.taskPointsTextView);
		
		userSpinner = (Spinner) findViewById(R.id.taskUserSpinner);
		categorySpinner = (Spinner) findViewById(R.id.taskCategorySpinner);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.categories, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categorySpinner.setAdapter(adapter);
		addListenersOnSpinnersItemSelection();
        getFamilyUsersAndInitializeSpinner();

		
		
	}
	
	/**
	 * inicjalizacja spinera z uzytkownikami rodziny  
	 */
	public void getFamilyUsersAndInitializeSpinner() {
		new GetUsers().execute();
	}
	
	/**
	 * dodawanie listenerow do spinera z uzytownikami 
	 */
	public void addListenersOnSpinnersItemSelection() {
    	userSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				User tempUser = (User) userSpinner.getSelectedItem();
				if(tempUser != null && selectedUser != null && tempUser.getUserName().equals(selectedUser.getUserName())) {
					userChanged = false;
				}
				else {
					userChanged = true;
					selectedUser = tempUser;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				category =  categorySpinner.getSelectedItemPosition()+1+"";
				showToast("wybrana kategora: " + category);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    }

	/**
	 * @param responseText
	 * metoda odpowiedzialna za pokazywanie Tosta na ekranie 
	 */
	public void showToast(String responseText) {

		Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG)
				.show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_task, menu);
		return true;
	}

	@Override
	 @Deprecated
	 protected Dialog onCreateDialog(int id) {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
	  return new DatePickerDialog(this, datePickerListener, year, month, day);
	 }

	 private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
	  public void onDateSet(DatePicker view, int selectedYear,
	    int selectedMonth, int selectedDay) {
	   dataPicker.setText(selectedDay + " . " + (selectedMonth + 1) + " . "
	     + selectedYear);
	   deadline = selectedYear+"."+(selectedMonth + 1)+"." + selectedDay;
	  }
	 };

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		
	}
	public void addItemsOnUserSpinner() {
    	
    	UserSpinnerAdapter spinnerArrayAdapter = new UserSpinnerAdapter(this,
    			android.R.layout.simple_spinner_item, familyUsers);
    	userSpinner.setAdapter(spinnerArrayAdapter);

    }
	
	private String[] getDataArray() {
		
		taksTo = userSpinner.getSelectedItem().toString();
		taskName = taskNameEditText.getText().toString();
		whatToDo = whatToDoEditText.getText().toString();
		points = pointsEditText.getText().toString();
		//deadline = revertData(deadline);
		
		return new String[] {taksTo,taskName,whatToDo,points,deadline,category};
	}
	
	private String revertData(String st) {

		String tmp[] = st.split(".");
		return tmp[2]+"."+tmp[1]+"."+tmp[0];
	}
	
	/**
	 * @author kwachu
	 * Klasa diedziczaca po AsyncTask. Odpowidzialna za laczenie z serwerem oraz pobieraniu 
	 * uzytkownikow danej rodziny 
	 */
	public class GetUsers extends AsyncTask<Void, Void, Void> {

		
		private String responseText = null;
		
		public GetUsers() {
		}
		
		@Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	    }
	 
	    @Override
	    protected Void doInBackground(Void... arg0) {
	        try {
	        	responseText = new HttpHandler(
						"http://malinowepi.no-ip.org/get_family_users.php", null)
						.postDataGetUsers();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	    @Override
	    protected void onPostExecute(Void result) {
	        JSONParser jp = new JSONParser(responseText);
			try {
				familyUsers = jp.getUsersResult();
				addItemsOnUserSpinner();
			} catch (JSONException e) {
				//Toast.makeText(pa,e.toString(),2000);
			}
	    }
	}
	
	
	/**
	 * @author kwachu
	 * Klasa dziedziczaca po AsyncTaks. Pozwala na dodawanie nowych zadan do bazy 
	 * danych na serwerze 
	 */
	public class AddTask extends AsyncTask<Void, Void, Void> {
		
		String r;
		public AddTask() {
		}
		
		@Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	    }
	 
	    @Override
	    protected Void doInBackground(Void... arg0) {
	        try {
	        	r= new HttpHandler(
						"http://malinowepi.no-ip.org/add_task.php", getDataArray())
						.postDataAddTask();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	    @Override
	    protected void onPostExecute(Void result) {
	       showToast(r);

	    }
		
	}
	
	
	
	

}
