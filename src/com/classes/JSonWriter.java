package com.classes;

import java.io.File;
import java.io.FileWriter;

import android.content.Context;
/**
 * Klasa służąca do zapisywania JSONa do pliku
 * @author KMACIAZE
 *
 */
public class JSonWriter {

	public static JSonWriter jSonWriter = new JSonWriter();
	/**
	 * Metoda zwracająca instancje klasy.
	 * @return
	 */
	public static JSonWriter getInstance() {
		return jSonWriter;
	}
	/**
	 * Metoda dodająca do JSON kolejne informacje
	 * @param fileName nazwa pliku, w którym przechowywany jest JSON
	 * @param text dodajemy
	 * @param writeMode czy zapisujemy
	 * @param context kontekst aktywności
	 */
	public void appendToFile(String fileName, String text, int writeMode,
			Context context) {

		FileWriter f;
		File file = new File(context.getFilesDir(), fileName);
		if (writeMode == 0) {// pusty plik, do ktorego dodajemy tekst

			try {
				f = new FileWriter(file);
				f.write(text);
				f.flush();
				f.close();
			} catch (Exception a) {
				a.printStackTrace();
			}
		} else { // dopisywanie do pliku juz istniejacego

			try {
				f = new FileWriter(file, true);
				f.write(text);
				f.flush();
				f.close();
			} catch (Exception a) {
				a.printStackTrace();
			}

		}
	}
	/**
	 * Metoda usuwająca danych plik
	 * @param fileName nazwa pliku do usunięcia
	 * @param context context aktywności
	 */
	public void deleteFile(String fileName, Context context) {
		File file = new File(context.getFilesDir(), fileName);
		file.delete();
	}
	/**
	 * Metoda sprawdzająca czy plik istnieje
	 * @param fileName nazwa pliku do sprawdzenia
	 * @param context conext aktywności
	 * @return true, jeżeli istnieje, false wpp
	 */
	public boolean ifExist(String fileName, Context context) {

		File file = new File(context.getFilesDir(), fileName);
		System.out.println(context.getFilesDir());
		if (file.exists()) {
			return true;
		}

		return false;
	}

}