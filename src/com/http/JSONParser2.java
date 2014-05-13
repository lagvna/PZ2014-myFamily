package com.http;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.classes.Note;
import com.classes.Product;
import com.classes.ShoppingList;

public class JSONParser2 {

	private String inputStream;
	private ArrayList<Note> notesArray = null;
	private ArrayList<Product> productsArray = null;
	private ArrayList<ShoppingList> shoppingListArray = null;
	private ShoppingList sl = null;

	public JSONParser2(String inputStream) {
		this.inputStream = inputStream;
	}

	
	public String[] getRemoveSthResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");

		String[] data = new String[2];
		JSONObject jo = jSonArray.getJSONObject(0);
		data[0] = jo.getString("success");
		data[1] = jo.getString("message");
		
		return data;
	}
	
	public String getAddShoppingListResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");

		JSONObject jo = jSonArray.getJSONObject(0);
		String errorCode = jo.getString("success");
		String message = jo.getString("message");
		System.out.println(message);

		return errorCode;
	}

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

	public Product getAddProductResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");
		Product product;

		JSONObject jo = jSonArray.getJSONObject(0);
		String errorCode = jo.getString("success");
		String message = jo.getString("message");
		System.out.println(message);

		if (!errorCode.equals("0")) {
			JSONObject jo2 = jo.getJSONObject("created_product");

			int id = jo2.getInt("Id");
			String category = jo2.getString("category");
			String name = jo2.getString("name");
			String price = jo2.getString("price");

			product = new Product(id, name, category, price);
		} else {
			product = new Product();
		}

		return product;
	}

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

	public ArrayList<ShoppingList> getGetShoppingListsResult()
			throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");
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
	}

	public ArrayList<Product> getGetProductsResult() throws JSONException {
		JSONArray jSonArray = new JSONArray("[" + inputStream + "]");
		JSONObject jo = jSonArray.getJSONObject(0);
		String errorCode = jo.getString("success");

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