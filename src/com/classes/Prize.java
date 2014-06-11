package com.classes;

import java.io.Serializable;
/**
 * Klasa reprezentująca nagrodę
 * @author KMACIAZE
 *
 */
public class Prize implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6479466892134899461L;
	private String name = "";
	private String pointsToGain = "";
	private String Id = "";
	private String gained_date = "";
	private String whos = "";
	private String category = "";
	/**
	 * Konstruktor klasy
	 * @param name nazwa nagrody
	 * @param pointsToGain liczba punktór do zdobycia nagrody
	 * @param Id id nagrody
	 */
	public Prize(String name, String pointsToGain, String Id) {
		this.setName(name);
		this.setPointsToGain(pointsToGain);
		this.setId(Id);
	}
	/**
	 * Główny konstruktor klasy
	 * @param name nazwa nagrody
	 * @param Id id nagrody
	 * @param whos czyja nagroda
	 * @param gained_date data zdobycia nagrody
	 * @param category kategoria nagrody
	 */
	public Prize(String name, String Id, String whos, String gained_date,String category) {
		this.setName(name);
		this.setId(Id);
		this.setGained_date(gained_date);
		this.setWhos(whos);
		this.category = category;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPointsToGain() {
		return "Wymagana iloÅ›Ä‡ punktÃ³w: " + pointsToGain;
	}

	public void setPointsToGain(String pointsToGain) {
		this.pointsToGain = pointsToGain;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}
	public String getGained_date() {
		return gained_date;
	}
	public void setGained_date(String gained_date) {
		this.gained_date = gained_date;
	}
	public String getWhos() {
		return whos;
	}
	public void setWhos(String whos) {
		this.whos = whos;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
}

