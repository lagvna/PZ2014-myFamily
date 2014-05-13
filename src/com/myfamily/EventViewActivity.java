package com.myfamily;

import java.util.ArrayList;
import java.util.TreeSet;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.async.RemoveSth;
import com.classes.MyEvent;
import com.tyczj.extendedcalendarview.CalendarProvider;

public class EventViewActivity extends ListActivity implements OnTouchListener {

	private MyCustomAdapter mAdapter;
	private ArrayList<MyEvent> myEvents;
	private ListView lv;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		myEvents = (ArrayList<MyEvent>) getIntent().getSerializableExtra(
				"events");

		context = this;
		lv = getListView();
		initializeListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// showToast("no jestem");
			}

		});

	}

	private void removeEvent(String id) {

		getContentResolver().delete(CalendarProvider.CONTENT_URI,
				CalendarProvider.LOCATION + "=?", new String[] { id });
		new RemoveSth(id, "Wydarzenie").execute();
		CalendarActivity.repaintCalendar();
	}

	private void initializeListView() {
		mAdapter = new MyCustomAdapter();

		if (myEvents.isEmpty()) {
			mAdapter.addSeparatorItem("Brak wydarzeń");
		}

		for (int i = 0; i < myEvents.size(); i++) {
			MyEvent me = myEvents.get(i);
			mAdapter.addSeparatorItem("Wydarzenie " + (i + 1));
			mAdapter.addItem("Nazwa: " + me.getName());
			mAdapter.addItem("Kiedy: " + me.getDate());
			// mAdapter.addItem(me.getOrganizer());
			mAdapter.addItem("Notatka: " + me.getNote());
			mAdapter.addItem("Id: " + me.getId());
		}
		setListAdapter(mAdapter);
	}

	/*
	 * public void showToast(String text){
	 * 
	 * Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG) .show();
	 * }
	 */
	// Adapter Class
	private class MyCustomAdapter extends BaseAdapter {

		private static final int TYPE_ITEM = 0;
		private static final int TYPE_SEPARATOR = 1;
		private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;

		private ArrayList<String> mData = new ArrayList<String>();
		private LayoutInflater mInflater;

		private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();

		public MyCustomAdapter() {
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void addItem(final String item) {
			mData.add(item);
			notifyDataSetChanged();
		}

		public void addSeparatorItem(final String item) {
			mData.add(item);
			// save separator position
			mSeparatorsSet.add(mData.size() - 1);
			notifyDataSetChanged();
		}

		@Override
		public int getItemViewType(int position) {
			return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR
					: TYPE_ITEM;
		}

		@Override
		public int getViewTypeCount() {
			return TYPE_MAX_COUNT;
		}

		public int getCount() {
			return mData.size();
		}

		public String getItem(int position) {
			return mData.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			final int pos = position;
			int type = getItemViewType(position);
			if (convertView == null) {
				holder = new ViewHolder();
				switch (type) {
				case TYPE_ITEM:
					convertView = mInflater.inflate(R.layout.item1, null);
					holder.textView = (TextView) convertView
							.findViewById(R.id.text);
					break;
				case TYPE_SEPARATOR:
					convertView = mInflater.inflate(R.layout.item2, null);
					ImageButton eventRemoveButton = (ImageButton) convertView
							.findViewById(R.id.eventRemoveButton);

					eventRemoveButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							try {

								removeEvent(myEvents.get(pos % 4).getId());
								myEvents.remove(pos % 4);

							} catch (Exception e) {
								e.printStackTrace();
							}

							initializeListView();
							// showToast("kupa"+ pos%4);
						}
					});
					holder.textView = (TextView) convertView
							.findViewById(R.id.textSeparator);
					break;
				}
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.textView.setText(mData.get(position));
			return convertView;
		}

	}

	public static class ViewHolder {
		public TextView textView;
	}

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	public void showToast(String responseText) {

		Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG)
				.show();
	}
}