package com.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.classes.DataHolder;

/**
 * Klasa służąca do pobierania danych jako JSON z serwera. Adres serwera przekazywany jest w konstruktorze.
 * @author KMACIAZE
 *
 */
public class HttpHandler {

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
	public HttpHandler(String url, String[] dataArray) {
		this.url = url;
		this.dataArray = dataArray;
	}

	/**
	 * @return response text from web server with json objects
	 * @throws ClientProtocolException
	 * @throws IOException
	 * 
	 *             Simple method to connect to server and login
	 * 
	 */
	public String postDataLogin() throws ClientProtocolException, IOException {

		HttpPost post = new HttpPost(url); // http://malinowepi.no-ip.org/login.php
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("login", dataArray[0]));
		nameValuePairs.add(new BasicNameValuePair("password", dataArray[1]));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);
	}

	public String postDataUserRegister() throws ClientProtocolException,
			IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("login", dataArray[0]));
		nameValuePairs.add(new BasicNameValuePair("password", dataArray[1]));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);
	}

	public String postDataAddUserToFamily() throws ClientProtocolException,
			IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("login", dataArray[0]));
		nameValuePairs.add(new BasicNameValuePair("role", dataArray[1]));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);
	}

	public String postDataAddFamily() throws IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("name", dataArray[0]));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);

	}

	public String postDataRemove() throws IOException {
		HttpPost post = new HttpPost(url); // http://malinowepi.no-ip.org/login.php
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("action", dataArray[0]));
		nameValuePairs.add(new BasicNameValuePair("Id", dataArray[1]));
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

	public String postDataAddEvent() throws IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("name", dataArray[0]));
		nameValuePairs.add(new BasicNameValuePair("note", dataArray[1]));
		nameValuePairs.add(new BasicNameValuePair("date", dataArray[2]));
		nameValuePairs.add(new BasicNameValuePair("color", dataArray[3]));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);

	}

	public String postDataGetEvents() throws IOException {
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

	public String postDataAddPrizes() throws IOException {
		HttpPost post = new HttpPost(url); // http://malinowepi.no-ip.org/add_prize.php
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("name", dataArray[0]));
		nameValuePairs.add(new BasicNameValuePair("points_to_gain",
				dataArray[1]));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);
	}

	public String postDataGetUsers() throws IOException {
		HttpPost post = new HttpPost(url); // http://malinowepi.no-ip.org/login.php
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
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

	public String postDataGetPrizes() throws IOException {
		HttpPost post = new HttpPost(url); // http://malinowepi.no-ip.org/get_prizes.php
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("whos_prizes_login",
				dataArray[0]));
		nameValuePairs
				.add(new BasicNameValuePair("gained_or_all", dataArray[1]));
		nameValuePairs.add(new BasicNameValuePair("category", dataArray[2]));
		nameValuePairs.add(new BasicNameValuePair("date_from", "0"));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);

	}

	public String postDataAddTask() throws IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("task_to", dataArray[0]));
		nameValuePairs.add(new BasicNameValuePair("name", dataArray[1]));
		nameValuePairs.add(new BasicNameValuePair("what_to_do", dataArray[2]));
		nameValuePairs.add(new BasicNameValuePair("points", dataArray[3]));
		nameValuePairs.add(new BasicNameValuePair("deadline", dataArray[4]));
		nameValuePairs.add(new BasicNameValuePair("category", dataArray[5]));
		// category
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);

	}

	public String postDataVoteTask() throws IOException {
		HttpPost post = new HttpPost(url); // http://malinowepi.no-ip.org/login.php
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("done", "1"));
		nameValuePairs.add(new BasicNameValuePair("task_Id", dataArray[0]));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);

	}

	public String postDataGetTasks() throws IOException {
		HttpPost post = new HttpPost(url); // http://malinowepi.no-ip.org/login.php
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
		nameValuePairs.add(new BasicNameValuePair("session", DataHolder
				.getInstance().getSession()));
		nameValuePairs.add(new BasicNameValuePair("family_Id", DataHolder
				.getInstance().getStringFamilyId()));
		nameValuePairs.add(new BasicNameValuePair("direction", "all"));
		nameValuePairs.add(new BasicNameValuePair("type", "5"));
		nameValuePairs.add(new BasicNameValuePair("date_from", "0"));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		client = new DefaultHttpClient();
		response = client.execute(post);
		entity = response.getEntity();

		return EntityUtils.toString(entity);

	}

	public String postPicureSend() throws IOException {

		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		String fileName = dataArray[0];
		try {
			MultipartEntity entity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);

			entity.addPart("file", new FileBody(new File(dataArray[0])));

			httpPost.setEntity(entity);

			HttpResponse response = httpClient.execute(httpPost, localContext);

			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			response.getEntity().writeTo(outstream);
			return outstream.toString();
     
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "jakis blad z serwerem chyba";
	}

}
