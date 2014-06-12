package com.classes;

/**
 * @author kwachu
 * Klasa reprezentujaca uzytkownika w systemie
 */
public class User {

	private String userName = null;
	private String role = null;
	
	/**
	 * glowny konstruktor klasy
	 * @param userName
	 * @param role
	 */
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
