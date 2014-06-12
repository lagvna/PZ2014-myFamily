package com.http;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.classes.Expense;
import com.classes.Note;
import com.classes.Product;
import com.classes.ShoppingList;
/**
 * Klasa służąca do parsowania obiektu JSON zapisanego w formacie String. 
 * @author KMACIAZE
 *
 */
public class JSONParser2 {

	private String inputStream;
	private ArrayList<ArrayList> resultArray = new ArrayList<ArrayList>();
	private ArrayList<Note> notesArray = null;
	private ArrayList<Product> productsArray = null;
	private ArrayList<ShoppingList> shoppingListArray = null;
	private ArrayList<String> stringArray = new ArrayList<String>();
	private ArrayList<Expense> expensesArray = null;
	private ShoppingList sl = null;
	/**
	 * Główny konstruktor klasy.
	 * @param inputStream obiekt JSON zapisany jako String
	 */
	public JSONParser2(String inputStream) {
		this.inputStream = inputStream;
	}
	/**
	 * Metoda parsująca JSONa zawierającego informacje zwrócone z akcji usuwania 
	 * @return lista z odpowiednimi informacjami z JSONa
	 */
	public String[] getRemoveSthResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");

		String[] data = new String[2];
		JSONObject jo = jSonArray.getJSONObject(0);
		data[0] = jo.getString("success");
		data[1] = jo.getString("message");

		return data;
	}
	/**
	 * Metoda parsująca JSONa zawierającego informacje zwrócone z akcji pobierania wydatków
	 * @return lista z odpowiednimi informacjami z JSONa
	 */
	public ArrayList<Expense> getGetExpensesResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");
		JSONObject jo = jSonArray.getJSONObject(0);
		String errorCode = jo.getString("success");

		if (!errorCode.equals("0")) {
			expensesArray = new ArrayList<Expense>();
			JSONArray exp = (JSONArray) jo.get("expenses");
			for (int i = 0; i < exp.length(); i++) {
				JSONObject object = exp.getJSONObject(i);
				expensesArray.add(new Expense(object.getInt("Id"), object
						.getString("name"), object.getString("note"), object
						.getString("whos_expenses"), object.getString("price"),
						object.getString("creation_date")));
				System.out.println(expensesArray.get(i).getName());
			}
		}

		return expensesArray;
	}
	/**
	 * Metoda parsująca JSONa zawierającego informacje zwrócone z akcji pobierania list zakupów
	 * @return lista z odpowiednimi informacjami z JSONa
	 */
	public String getAddShoppingListResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");

		JSONObject jo = jSonArray.getJSONObject(0);
		String errorCode = jo.getString("success");
		String message = jo.getString("message");
		System.out.println(message);

		return errorCode;
	}
	/**
	 * Metoda parsująca JSONa zawierającego informacje zwrócone z akcji dodawania wydatku
	 * @return lista z odpowiednimi informacjami z JSONa
	 */
	public int getAddExpenseResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");

		JSONObject jo = jSonArray.getJSONObject(0);
		String errorCode = jo.getString("success");
		String message = jo.getString("message");
		System.out.println(message);

		if (!errorCode.equals("0")) {
			return 1;
		} else {
			return 0;
		}
	}
	/**
	 * Metoda parsująca JSONa zawierającego informacje zwrócone z akcji ustawiania cen
	 * @return lista z odpowiednimi informacjami z JSONa
	 */
	public int getSetPriceResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");

		JSONObject jo = jSonArray.getJSONObject(0);
		String errorCode = jo.getString("success");
		String message = jo.getString("message");
		System.out.println(message);

		if (!errorCode.equals("0")) {
			return 1;
		} else {
			return 0;
		}
	}
	/**
	 * Metoda parsująca JSONa zawierającego informacje zwrócone z akcji dodawania produktu
	 * @return lista z odpowiednimi informacjami z JSONa
	 */
	public Product getAddProductResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");
		Product product;

		System.out.println("Input: " + inputStream);

		JSONObject jo = jSonArray.getJSONObject(0);
		String errorCode = jo.getString("success");
		String message = jo.getString("message");
		System.out.println(message);

		if (!errorCode.equals("0")) {
			JSONArray jo2 = jo.getJSONArray("created_product");
			JSONObject jo3 = jo2.getJSONObject(0);

			int id = jo3.getInt("Id");
			String category = jo3.getString("category");
			String name = jo3.getString("name");
			String price = jo3.getString("price");

			product = new Product(id, name, category, price);
		} else {
			product = new Product();
		}

		return product;
	}
	/**
	 * Metoda parsująca JSONa zawierającego informacje zwrócone z akcji pobierania list zakupów
	 * @return lista z odpowiednimi informacjami z JSONa
	 */
	public ShoppingList getGetShoppingListResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");
		JSONObject jo = jSonArray.getJSONObject(0);
		String errorCode = jo.getString("success");
		String msg = jo.getString("message");
		System.out.println(msg);

		if (!errorCode.equals("0")) {
			JSONObject jo2 = (JSONObject) jo.get("shopping_list");

			int id = jo2.getInt("Id");
			String owner = jo2.getString("owner");
			String name = jo2.getString("name");
			String totalCost = jo2.getString("total_cost");
			String date = jo2.getString("creation_date");

			productsArray = new ArrayList<Product>();
			JSONArray products = (JSONArray) jo2.get("products");

			for (int i = 0; i < products.length(); i++) {
				JSONObject object = products.getJSONObject(i);
				productsArray.add(new Product(object.getInt("Id"), object
						.getString("product_name"), object
						.getString("category"), object.getString("price")));
				System.out.println(productsArray.get(i).getName());
			}

			sl = new ShoppingList(id, owner, name, totalCost, date,
					productsArray);
		}

		return sl;
	}
	/**
	 * Metoda parsująca JSONa zawierającego informacje zwrócone z akcji pobierania list zakupów
	 * @return lista z odpowiednimi informacjami z JSONa
	 */
	public ArrayList getGetShoppingListsResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");
		System.out.println("SHOPPING LISTS: " + inputStream);
		JSONObject jo = jSonArray.getJSONObject(0);
		String errorCode = jo.getString("success");
		String msg = jo.getString("message");
		System.out.println(msg);
		if (!errorCode.equals("0")) {
			shoppingListArray = new ArrayList<ShoppingList>();
			JSONArray lists = (JSONArray) jo.get("new_shopping_lists");
			for (int i = 0; i < lists.length(); i++) {
				JSONObject object = lists.getJSONObject(i);
				shoppingListArray.add(new ShoppingList(object.getInt("Id"),
						object.getString("owner"), object.getString("name"),
						object.getString("total_cost"), object
								.getString("creation_date")));
				System.out.println(shoppingListArray.get(i).getName());
			}
		}

		return shoppingListArray;

		/*
		 * ArrayList<ShoppingList> shoppingLists = new
		 * ArrayList<ShoppingList>(); ArrayList<String> deletedShoppingListsId =
		 * new ArrayList<String>(); JSONArray jSonArray = new JSONArray("[" +
		 * inputStream + "]"); int n = jSonArray.length();
		 * 
		 * JSONObject jSonObject = jSonArray.getJSONObject(0); String errorCode
		 * = jSonObject.getString("success"); String message =
		 * jSonObject.getString("message"); String newEventCode; String
		 * deletedEventCode;
		 * 
		 * JSONArray shoppingLists; JSONArray deletedSL; JSONObject object;
		 * 
		 * try { if (!errorCode.equals("0")) { // sukces
		 * 
		 * newEventCode = jSonObject .getString("new_shopping_list_success");
		 * deletedEventCode = jSonObject
		 * .getString("deleted_shopping_lists_success");
		 * 
		 * if (!newEventCode.equals("0")) { // jesli sa nowe wydarzenia //
		 * doddania
		 * 
		 * shoppingLists = (JSONArray) jSonObject.get("new_shopping_lists"); for
		 * (int i = 0; i < shoppingLists.length(); i++) { object =
		 * shoppingLists.getJSONObject(i); shoppingLists.add(new
		 * ShoppingList(object.getInt("id"), object.getString("owner"), object
		 * .getString("name"), object .getString("total_cost"), object
		 * .getString("creation_date"))); }
		 * 
		 * resultArray.add(shoppingLists); } else {
		 * 
		 * String oldJson = JSonReader.getInstance().readFile( "shoppinglists",
		 * context); jSonArray = new JSONArray("[" + oldJson.substring(19) +
		 * "]"); jSonObject = jSonArray.getJSONObject(0); events = (JSONArray)
		 * jSonObject.get("events");
		 * 
		 * for (int i = 0; i < events.length(); i++) { object =
		 * events.getJSONObject(i); shoppingLists.add(new
		 * ShoppingList(object.getInt("id"), object.getString("owner"), object
		 * .getString("name"), object .getString("total_cost"), object
		 * .getString("creation_date"))); } resultArray.add(shoppingLists); }
		 * 
		 * if (!deletedEventCode.equals("0")) { // jesli sa jakies zadanie // do
		 * usuniecia z pliku
		 * 
		 * deletedEvents = (JSONArray) jSonObject .get("deleted_events"); for
		 * (int i = 0; i < deletedEvents.length(); i++) { object =
		 * deletedEvents.getJSONObject(i);
		 * deletedEventsId.add(object.getString("Id")); }
		 * resultArray.add(deletedEventsId); }
		 * 
		 * // makeEventsJSon(eventsList); stringArray.add(new
		 * String(errorCode)); stringArray.add(new String(message));
		 * stringArray.add(newEventCode); stringArray.add(deletedEventCode);
		 * resultArray.add(stringArray); return resultArray;
		 * 
		 * } else { stringArray.add(new String(errorCode)); stringArray.add(new
		 * String(message));
		 * 
		 * resultArray.clear(); resultArray.add(stringArray); return
		 * resultArray; }
		 * 
		 * } catch (JSONException e) { e.printStackTrace(); }
		 * 
		 * return resultArray;
		 */

	}
	/**
	 * Metoda parsująca JSONa zawierającego informacje zwrócone z akcji pobiernia produktów
	 * @return lista z odpowiednimi informacjami z JSONa
	 */
	public ArrayList<Product> getGetProductsResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");
		JSONObject jo = jSonArray.getJSONObject(0);
		String errorCode = jo.getString("success");

		System.out.println("PRODUCTS: " + inputStream);

		if (!errorCode.equals("0")) {
			productsArray = new ArrayList<Product>();
			JSONArray products = (JSONArray) jo.get("new_products");
			for (int i = 0; i < products.length(); i++) {
				JSONObject object = products.getJSONObject(i);
				productsArray.add(new Product(object.getInt("Id"), object
						.getString("name"), object.getString("category"),
						object.getString("price")));
			}
		}

		return productsArray;
	}
	/**
	 * Metoda parsująca JSONa zawierającego informacje zwrócone z akcji dodawania notatki
	 * @return lista z odpowiednimi informacjami z JSONa
	 */
	public Note getAddNoteResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");
		Note note;

		JSONObject jo = jSonArray.getJSONObject(0);
		String errorCode = jo.getString("success");

		if (!errorCode.equals("0")) {
			JSONObject jo2 = jo.getJSONObject("created_note");

			int id = jo2.getInt("Id");
			String author = jo2.getString("owner");
			String content = jo2.getString("content");
			String date = jo2.getString("creation_date");

			note = new Note(id, author, content, date);
		} else {
			note = new Note();
		}

		return note;
	}
	/**
	 * Metoda parsująca JSONa zawierającego informacje zwrócone z akcji pobierania notatek
	 * @return lista z odpowiednimi informacjami z JSONa
	 */
	public ArrayList<Note> getGetNotesResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");
		JSONObject jo = jSonArray.getJSONObject(0);
		String errorCode = jo.getString("success");

		if (!errorCode.equals("0")) {
			notesArray = new ArrayList<Note>();
			JSONArray notes = (JSONArray) jo.get("notes");
			for (int i = 0; i < notes.length(); i++) {
				JSONObject object = notes.getJSONObject(i);
				notesArray.add(new Note(object.getInt("Id"), object
						.getString("owner"), object.getString("content"),
						object.getString("creation_date")));
			}
		}

		return notesArray;
	}
}