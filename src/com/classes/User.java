package com.classes;

public class User {

	private String userName = null;
	private String role = null;
	
	public User() {
		
	}
	
	public User(String userName, String role) {
		this.userName = userName;
		this.role = role;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public String getUserRole() {
		return this.role;
	}
	
	public String toString() {
		return userName;
	}
	
	
}
