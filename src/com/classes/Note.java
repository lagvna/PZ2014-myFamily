package com.classes;

import java.io.Serializable;

public class Note implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id = -1;
	private String owner = null;
	private String content = null;
	private String date = null;
	
	public Note()	{
		
	}
	
	public Note(int id, String owner, String content, String date)	{
		this.id = id;
		this.owner = owner;
		this.content = content;
		this.date = date;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}