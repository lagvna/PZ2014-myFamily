package com.fragments;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adapters.TasksListAdapter;
import com.async.RemoveSth;
import com.async.VoteTask;
import com.classes.Task;
import com.myfamily.R;

/**
 * Fragment przypiety do aktywnosci zwiazanej z zadaniami, wyswietlajacy stare,
 * wykonane i ocenione juz zadania
 * 
 * @author kwachu
 * 
 */
public class FragmentOldTasks extends Fragment {

	private ListView tasksListView;
	private ArrayList<Task> tasksList;
	private boolean[] selectedItems;
	private Task selectedTask;
	TasksListAdapter adapter;
	private boolean remove = false;
	private boolean vote = false;
	Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_old_tasks,
				container, false);
		tasksListView = (ListView) rootView.findViewById(R.id.oldTasksListView);
		context = rootView.getContext();
		Bundle bundle = getArguments();
		tasksList = (ArrayList<Task>) bundle.get("tasksList");
		selectedItems = bundle.getBooleanArray("selected");

		initializedList();

		tasksListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long id) {
				// TODO Auto-generated method stub

				Log.v("long clicked", "pos: " + pos);
				selectedTask = adapter.getItem(pos);
				if (!selectedTask.getName().equals("Brak zadań")) {
					showDialog(selectedTask.getName(), selectedTask.getToDo());
				}

				return true;
			}

		});

		return rootView;
	}

	/**
	 * Metoda inicjalizujaca liste i odswiezajaca widok listowy
	 */
	public void initializedList() {

		ArrayList<Task> tempList = ifShouldBeSelect(tasksList);
		if (!tempList.isEmpty()) {
			adapter = new TasksListAdapter(this.getActivity(), tempList,
					selectedItems);
		} else {
			tempList.add(new Task("", "Brak zadań", "", "", "", "", ""));
			adapter = new TasksListAdapter(this.getActivity(), tempList,
					selectedItems);
		}
		tasksListView.setAdapter(adapter);
	}

	/**
	 * Metoda aktualizujaca liste wybranych rekordow
	 * 
	 * @param selectedItems
	 */
	public void updateList(boolean[] selectedItems) {
		this.selectedItems = selectedItems;
		initializedList();
	}

	private ArrayList<Task> ifShouldBeSelect(ArrayList<Task> tasks) {

		ArrayList<Task> tempList = new ArrayList<Task>();

		int count = 0;
		boolean[] boolTask;
		if (tasks != null) {
			for (int i = 0; i < tasks.size(); i++) {
				count = 0;
				boolTask = tasks.get(i).getOptions();
				for (int j = 0; j < boolTask.length; j++) {

					if (selectedItems[j] == boolTask[j]) {
						count++;
					}
				}
				if (count >= 2) {
					count = 0;
					tempList.add(tasks.get(i));
				}
			}
		}
		return tempList;
	}

	void showDialog(String title, String note) {

		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.custom_dialog_task);
		dialog.setTitle(title);

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.taskNoteTextView);
		text.setText(note);

		CheckBox vote1 = (CheckBox) dialog.findViewById(R.id.checkBox2);
		CheckBox delete = (CheckBox) dialog.findViewById(R.id.checkBox1);

		vote1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (FragmentOldTasks.this.remove && FragmentOldTasks.this.vote) {
					Toast.makeText(context, "Dozwolna jest tylko jedna opcja",
							Toast.LENGTH_LONG).show();
				} else {
					if (!FragmentOldTasks.this.vote) {
						FragmentOldTasks.this.vote = true;
					} else
						FragmentOldTasks.this.vote = false;

				}
			}
		});
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (FragmentOldTasks.this.remove && FragmentOldTasks.this.vote) {
					Toast.makeText(context, "Dozwolna jest tylko jedna opcja",
							Toast.LENGTH_LONG).show();
				} else {
					if (!FragmentOldTasks.this.remove)
						FragmentOldTasks.this.remove = true;
					else
						FragmentOldTasks.this.remove = false;
				}
			}
		});

		Button dialogOkButton = (Button) dialog
				.findViewById(R.id.taskdialogOkButton);

		dialogOkButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (FragmentOldTasks.this.vote && !FragmentOldTasks.this.remove) {

					if (selectedTask.getForWho().equals("0")) {
						dialog.dismiss();
						Toast.makeText(
								context,
								"Nie mozesz ocenić zadania przeznaczonego dla Ciebie!",
								Toast.LENGTH_LONG).show();
						// dialog.dismiss();
					} else if (selectedTask.ifVoted()) {
						dialog.dismiss();
						Toast.makeText(context,
								"Nie mozesz ocenić ocenionego zadania",
								Toast.LENGTH_LONG).show();
					}

					else {

						new VoteTask(selectedTask.getId(),
								FragmentOldTasks.this).execute();
						Toast.makeText(context, "Zadanie ocenione :)",
								Toast.LENGTH_LONG).show();
						int a = adapter.getPosition(selectedTask);
						adapter.getItem(a).setVoted();
						adapter.notifyDataSetChanged();
					}

					dialog.dismiss();
				}

				if (!FragmentOldTasks.this.vote && FragmentOldTasks.this.remove) {

					if (selectedTask.getForWho().equals("0")) {
						dialog.dismiss();
						Toast.makeText(
								context,
								"Nie mozesz usunąć zadania przeznaczonego dla Ciebie!",
								Toast.LENGTH_LONG).show();

					} else if (selectedTask.ifVoted()) {
						dialog.dismiss();
						Toast.makeText(context,
								"Nie mozesz usunąć ocenionego zadania",
								Toast.LENGTH_LONG).show();

					} else {

						new VoteTask(selectedTask.getId(),
								FragmentOldTasks.this).execute();
						new RemoveSth(selectedTask.getId(), "Zadanie");
						adapter.remove(selectedTask);
						adapter.notifyDataSetChanged();
						Toast.makeText(context, "Zadanie usunięte",
								Toast.LENGTH_LONG).show();
					}

					/*
					 * TasksActivity.getInstance().finish(); startActivity(new
					 * Intent(context, TasksActivity.class));
					 */
					dialog.dismiss();
				}
				FragmentOldTasks.this.vote = false;
				remove = false;

			}
		});

		Button dialogCancelButton = (Button) dialog
				.findViewById(R.id.taskdialogCancelButton);
		// if button is clicked, close the custom dialog
		dialogCancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentOldTasks.this.vote = false;
				remove = false;
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	/**
	 * Metoda wyswietlajaca odpowiedz z serwera
	 * 
	 * @param text
	 *            odpowiedz z serwera
	 */
	public void showToast(String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

}
