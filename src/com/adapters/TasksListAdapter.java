package com.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.classes.Task;
import com.myfamily.R;



public class TasksListAdapter extends ArrayAdapter<Task> {

	private Context context;    
    private ArrayList<Task> tasksList;
    private Calendar calendar;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String currentDate;
    
    public TasksListAdapter(Context context, ArrayList<Task> tasks,boolean[] selectedItem) {
        super(context,R.layout.task_row, tasks);
        this.context = context;
        currentDate = sdf.format(new Date());
        this.tasksList = tasks;

    }


    public View getView(int position, View convertView, ViewGroup parent) 
	{
		
		View rowView = convertView;
		
		
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.task_row, parent, false);
		}
	
	
		Task task = tasksList.get(position);

			
			if(task != null) {
				
				//if(!ifActualTask(task.getDate())) { // jesli nieatualne
				
					TextView taskNameTextView= (TextView) rowView.findViewById(R.id.taskNameTextView);
					if(taskNameTextView != null) {
						taskNameTextView.setText(task.getName());
					}
					if(!(taskNameTextView != null && taskNameTextView.getText().equals("Brak zadań"))) {
						
						TextView deadlineTextView = (TextView) rowView.findViewById(R.id.deadlineTextView);
						if(deadlineTextView != null) {
							deadlineTextView.setText(task.getDateString());
						}
						TextView forWhoTextView = (TextView) rowView.findViewById(R.id.forWhoTextView);
						if(forWhoTextView != null) {
							if(task.getForWho().equals("0")) 
								forWhoTextView.setText("wykonuje: "+"ja");
							else
								forWhoTextView.setText("wykonuje: "+task.getForWho());
						}
						TextView voteTextView = (TextView) rowView.findViewById(R.id.voteTextView);
						if(voteTextView != null) {
							if(task.ifVoted())
							{
								voteTextView.setText("Ocenione");
							} else  {
								voteTextView.setText("Nieocenione");
							}
							
						}
						
					}
					
				//}
			}
				
		
		return rowView;	
	}	
    
    private ArrayList<Task> getOldTask(ArrayList<Task> tasks) {
    	
    	ArrayList<Task> tmp = new ArrayList<Task>();
    	for (int i = 0; i < tasks.size(); i++) {
			
    		Task task = tasks.get(i);
    		if(ifActualTask(task.getDate())) {
    			tmp.add(task);
    		}
    		
		}
    	
    	return tmp;
    }
    
    private boolean ifActualTask(Date d) {
    	
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
    
}
