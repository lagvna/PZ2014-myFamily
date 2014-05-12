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

public class HttpHandler {

	private String url = null;
	private String dataArray[] = null;
	private HttpClient client;
	private HttpResponse response;
	private HttpEntity entity;

	/**
	 * @param url
	 *            - url address of web server
	 * @param dataArray
	 *            - String array to collect all data needed to connect to the
	 *            web server
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
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
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

}
