package com.shopping;

/**
 * Klasa niestandardowego rekordu dla ListView.
 * Obiekt definiuje jak wyglada rekord na liscie.
 * @author lagvna
 *
 */
public class CustomRow {
	/** ID ikony */
	public int icon;
	/** Tytul rekordu*/
	public String title;
	/** Dodatkowa informacja: cena lub data */
	public String extras;
	
	/**Pusty konstruktor*/
	public CustomRow()	{
		
	}
	/**
	 * Glowny konstruktor rekordu
	 * 
	 * @param icon id ikony
	 * @param title nazwa pliku
	 * @param data stworzenia pliku
	 * */
	public CustomRow(int icon, String title, String extras)	{
		this.icon = icon;
		this.title = title;
		this.extras = extras;
	}
}