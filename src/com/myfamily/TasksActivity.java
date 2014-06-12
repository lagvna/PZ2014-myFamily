package com.myfamily;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.classes.Task;
import com.fragments.FragmentCurrentTasks;
import com.fragments.FragmentOldTasks;
import com.http.HttpHandler;
import com.http.JSONParser;


public class TasksActivity extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	private ProgressDialog progressDialog = null;
	private String[] tabsNames = { "Zadania bieżące", "Zadania przedwanione " };
	final CharSequence[] items = { " Ocenione ", " Nieocenione ",
			" Dla mnie ", " Ode mnie " };
	private boolean[] selectedItems = new boolean[4];
	private AlertDialog dialog;
	private ArrayList<Task> tasksList;
	private boolean finish = false;
	private FragmentCurrentTasks currentFragment;
	private FragmentOldTasks oldFragment;
	private static TasksActivity activity;
	private Calendar calendar;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String currentDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tasks);
		activity = this;
		currentDate = sdf.format(new Date());
		Arrays.fill(selectedItems, Boolean.TRUE); // wypenianie tablicy
		selectedItems[0] = false;
		new GetTasks().execute();

	}
	
	public static TasksActivity getInstance(){
		   return  activity;
	}

	private void initializedFragments() {

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tasks, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.displaySettings:
			showPopUp();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Sprawdzanie danych");
		progressDialog.show();
	}

	 public void onBackPressed() {
        finish();
     }   
	
	/**
	 * method which hide Progress dialog
	 */
	public void hideProgressDial() {
		progressDialog.hide();
	}

	public void showToast(String responseText) {

		Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG)
				.show();
	}

	private void showPopUp() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Rodzaj wyświetlania");
		builder.setMultiChoiceItems(items, selectedItems,
				new DialogInterface.OnMultiChoiceClickListener() {
					// indexSelected contains the index of item (of which
					// checkbox checked)
					@Override
					public void onClick(DialogInterface dialog,
							int indexSelected, boolean isChecked) {
						if (isChecked) {
							// If the user checked the item, add it to the
							// selected items
							// write your code when user checked the checkbox
							selectedItems[indexSelected] = true;
							if(selectedItems[0]==selectedItems[1])
							{
								selectedItems[0]=true;
								selectedItems[1]=true;
							}
							if(selectedItems[2]==selectedItems[3])
							{
								selectedItems[2]=true;
								selectedItems[3]=true;
							}
						} else if (selectedItems[indexSelected] == false) {
							// Else, if the item is already in the array, remove
							// it
							// write your code when user Uchecked the checkbox
							selectedItems[indexSelected] = false;
						}
					}
				})
				// Set the action buttons
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						oldFragment.updateList(selectedItems);
						currentFragment.updateList(selectedItems);
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// Your code when user clicked on Cancel

							}
						});

		dialog = builder.create();// AlertDialog dialog; create like this
									// outside onClick
		dialog.show();
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			
			Bundle args = new Bundle();
			// args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position +
			// 1);
			// fragment.setArguments(args);
			// return fragment;

			switch (position) {
			case 0:
				currentFragment = new FragmentCurrentTasks();
				args.putSerializable("tasksList", getCurrentTask(tasksList));
				args.putBooleanArray("selected", selectedItems);
				currentFragment.setArguments(args);
				return currentFragment;
			case 1:
				oldFragment = new FragmentOldTasks();
				args.putSerializable("tasksList", getOldTask(tasksList));
				args.putBooleanArray("selected", selectedItems);
				oldFragment.setArguments(args);
				return oldFragment;
			}

			return null;

		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return tabsNames[position].toUpperCase(l);
			case 1:
				return tabsNames[position].toUpperCase(l);
			}
			return null;
		}
	}
	
	
	
	private ArrayList<Task> getOldTask(ArrayList<Task> tasks) {
    	
    	ArrayList<Task> tmp = new ArrayList<Task>();
    	if(tasks==null) {
    		tasks = new ArrayList<Task>();
    		tasks.add(new Task("", "Brak zadań", "", "0", "", "", ""));
    	}
    	for (int i = 0; i < tasks.size(); i++) {
			
    		Task task = tasks.get(i);
    		if(!ifActualTask(task.getDate())) {
    			tmp.add(task);
    		}
    		
		}
    	
    	return tmp;
    }
	
	private ArrayList<Task> getCurrentTask(ArrayList<Task> tasks) {
    	
    	ArrayList<Task> tmp = new ArrayList<Task>();
    	if(tasks==null) {
    		tasks = new ArrayList<Task>();
    		tasks.add(new Task("", "Brak zadań", "", "0", "", "", ""));
    	}
    	
		for (int i = 0; i < tasks.size(); i++) {
			
    		Task task = tasks.get(i);
    		if(ifActualTask(task.getDate())) {
    			tmp.add(task);
    		}
    		
		}
    	
    	
    	
    	return tmp;
    }
    
    private boolean ifActualTask(Date d) {
    	calendar = Calendar.getInstance(); 
    	 try {
			Date date = sdf.parse(currentDate);
			if(d.compareTo(date)>0) {
				return true;
			} else if (d.compareTo(date)<0) {
				return false;
			} else {
				return true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	return false;
    }

	public class GetTasks extends AsyncTask<Void, Void, Void> {

		// private TasksActivity callingActivity;
		private String responseText;
		private String[] dataArray;

		public GetTasks() {
			// this.callingActivity = callingActivity;
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
						"http://malinowepi.no-ip.org/get_tasks.php", null)
						.postDataGetTasks();
				System.out.println(responseText);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			

			JSONParser jp = new JSONParser(responseText);
			//showToast(responseText);
			try {
				ArrayList resultSet = jp.getTaskResult();
				if (resultSet.size() == 2) {
					tasksList = (ArrayList<Task>) resultSet.get(0);
					initializedFragments();
				} else if (resultSet.size() == 1) {
					//showToast(((String) ((ArrayList) resultSet.get(0)).get(1)));
					initializedFragments();
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hideProgressDial();
		}

	}

}
