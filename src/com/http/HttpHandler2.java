package com.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.classes.DataHolder;
/**
 * Klasa służąca do pobierania danych jako JSON z serwera. Adres serwera przekazywany jest w konstruktorze.
 * @author KMACIAZE
 *
 */
public class HttpHandler2 {

	private String url = null;
	private String dataArray[] = null;
	private HttpClient client;
	private HttpResponse response;
	private HttpEntity entity;

	/**
	 * Główny konstruktor klasy
	 * @param url String określający adres serwera
	 * @param dataArray tablica przechowująca parametry, która mają być wysłane w parametrze
	 */
	public HttpHandler2(String url, String[] dataArray) {
		this.url = url;
		this.dataArray = dataArray;
	}
	/**
	 * Metoda służąca do pobierania danych wywoływana na akcję pobierania wydatków
	 * @return Obiekt JSON 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String postDataGetExpenses() throws ClientProtocolException,
			IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("from_date", dataArray[0]));
		nameValuePairs.add(new BasicNameValuePair("to_date", dataArray[1]));
		nameValuePairs.add(new BasicNameValuePair("whos_expenses_login",
				dataArray[2]));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);
	}
	/**
	 * Metoda służąca do pobierania danych wywoływana na akcję dodawania wydatków
	 * @return Obiekt JSON 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String postDataAddExpense() throws ClientProtocolException,
			IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("name", dataArray[0]));
		nameValuePairs.add(new BasicNameValuePair("price", dataArray[1]));
		nameValuePairs.add(new BasicNameValuePair("note", dataArray[2]));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);
	}

	public String postDataAddNote() throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("content", dataArray[0]));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);
	}
	/**
	 * Metoda służąca do pobierania danych wywoływana na akcję ustawniania ceny
	 * @return Obiekt JSON 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String postDataSetPrice() throws ClientProtocolException,
			IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("price", dataArray[0]));
		nameValuePairs.add(new BasicNameValuePair("product_Id", dataArray[1]));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);
	}
	/**
	 * Metoda służąca do pobierania danych wywoływana na akcję dodawania produktu
	 * @return Obiekt JSON 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String postDataAddProduct() throws ClientProtocolException,
			IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("name", dataArray[0]));
		nameValuePairs.add(new BasicNameValuePair("category", dataArray[1]));
		nameValuePairs.add(new BasicNameValuePair("price", dataArray[2]));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);
	}
	/**
	 * Metoda służąca do pobierania danych wywoływana na akcję dodawania listy zakupów
	 * @return Obiekt JSON 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String postDataAddShoppingList() throws ClientProtocolException,
			IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("total_cost", dataArray[0]));
		System.out.println("WCHUJWAZNE: " + dataArray[0]);
		nameValuePairs.add(new BasicNameValuePair("name", dataArray[1]));
		nameValuePairs.add(new BasicNameValuePair("products", dataArray[2]));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);
	}
	/**
	 * Metoda służąca do pobierania danych wywoływana na akcję pobierania list zakupów
	 * @return Obiekt JSON 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String postDataGetShoppingLists() throws ClientProtocolException,
			IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("date_from", dataArray[0]));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);
	}
	/**
	 * Metoda służąca do pobierania danych wywoływana na akcję pobierania list zakupów
	 * @return Obiekt JSON 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String postDataGetShoppingList() throws ClientProtocolException,
			IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("shopping_list_Id",
				dataArray[0]));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);
	}
	/**
	 * Metoda służąca do pobierania danych wywoływana na akcję pobierania notatek
	 * @return Obiekt JSON 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String postDataGetNotes() throws ClientProtocolException,
			IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);
	}
	/**
	 * Metoda służąca do pobierania danych wywoływana na akcję pobierania produktów
	 * @return Obiekt JSON 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String postDataGetProducts() throws ClientProtocolException,
			IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("date_from", dataArray[0]));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);
	}

}