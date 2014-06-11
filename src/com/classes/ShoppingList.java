package com.classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasa definiujaca obiekt listy zakupow w aplikacji
 * 
 * @author lagvna
 * 
 */
public class ShoppingList implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id = -1;
	private String owner = null;
	private String name = null;
	private String totalCost = null;
	private String date = null;
	private ArrayList<Product> products = null;

	public ShoppingList() {

	}

	/**
	 * Pomocniczy konstruktor klasy, w ktorej niezdefiniowane sa produkty
	 * zawarte na liscie zakupow
	 * 
	 * @param id
	 *            id listy
	 * @param owner
	 *            autor listy
	 * @param name
	 *            nazwa listy
	 * @param totalCost
	 *            suma za liste
	 * @param date
	 *            data zakupow
	 */
	public ShoppingList(int id, String owner, String name, String totalCost,
			String date) {
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.totalCost = totalCost;
		this.date = date;
	}

	/**
	 * Glowny konstruktor klasy
	 * 
	 * @param id
	 *            id listy
	 * @param owner
	 *            autor listy
	 * @param name
	 *            nazwa listy
	 * @param totalCost
	 *            suma za liste
	 * @param date
	 *            data zakupow
	 * @param products
	 *            lista produktow na liscie
	 */
	public ShoppingList(int id, String owner, String name, String totalCost,
			String date, ArrayList<Product> products) {
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.totalCost = totalCost;
		this.date = date;
		this.setProducts(products);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

}