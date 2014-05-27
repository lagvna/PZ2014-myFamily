package com.classes;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id = "";
	

	private String name = "";
	private String toDo = "";
	private String done = "";
	private String dateString = "2001-01-201414:00:00";
	private Date date = null;
	private String forWho = "";
	private String fromWho = "";
	private boolean[] options = new boolean[4];

	public Task(String id, String name, String toDo, String done,
			String deadline, String forWho, String fromWho) {

		this.id = id;
		this.name = name;
		this.toDo = toDo;
		this.done = done;
		this.dateString = deadline;
		this.forWho = forWho;
		this.fromWho = fromWho;
		
		if(done.equals("0")) {
			options[1] = true;
		} else {
			options[0] = true;
		}
		
		if (forWho.equals("0")) {
			options[2] = true;
		} else {
			options[3] = true;
		}
		
		if(fromWho.equals("0")) {
			options[3] = true;
		} else {
			options[2] = true;
		}
	}

	public boolean ifVoted() {
		if(done.equals("1")) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setVoted() {
		done ="1";
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToDo() {
		return toDo;
	}

	public void setToDo(String toDo) {
		this.toDo = toDo;
	}

	public String getPoints() {
		return done;
	}

	public void setPoints(String points) {
		if(points.equals("0")){
			options[1] = true;
		} else {
			options[0] = true;
		}
		this.done = points;
	}

	public String getDateString() {
		if(dateString.length()>10)
			return dateString.substring(0,11);
		else
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Date getDate() {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Date();
	}

	public void setDate(String date) {
		this.dateString = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss")
				.format(this.date);
	}

	public String getForWho() {
		return forWho;
	}

	public void setForWho(String forWho) {
		if(forWho.equals("0")) {
			options[2] = true;
		} else {
			options[3] = true;
		}
		this.forWho = forWho;
	}

	public String getFromWho() {
		return fromWho;
	}

	public void setFromWho(String fromWho) {
		if(fromWho.equals("0")) {
			options[3] = true;
		} else {
			options[2] = true;
		}
		this.fromWho = fromWho;
	}
	
	public boolean[] getOptions() {
		return options;
	}

	public void setOptions(boolean[] options) {
		this.options = options;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
