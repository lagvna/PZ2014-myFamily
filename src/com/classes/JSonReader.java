package com.classes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

/**
 * Klasa służąca do czytania obiektu JSON z pliku
 * @author KMACIAZE
 *
 */
public class JSonReader {

	public static JSonReader jSonReader = new JSonReader();

	/**
	 * Główny konstruktor klasy
	 * @return
	 */
	public static JSonReader getInstance() {
		return jSonReader;
	}
	/**
	 * Metoda czytająca plik
	 * @param fileName nazwa pliku
	 * @param context context aktywności
	 * @return obiekt JSON jako String
	 */
	public String readFile(String fileName, Context context) {

		String ret = "";
		// File file = new File(context.getFilesDir(),fileName);
		try {
			InputStream inputStream = context.openFileInput(fileName);

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ((receiveString = bufferedReader.readLine()) != null) {
					stringBuilder.append(receiveString);
				}

				inputStream.close();
				ret = stringBuilder.toString();
			}
		} catch (FileNotFoundException e) {
			Log.e("login activity", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
		}

		return ret;
	}

}