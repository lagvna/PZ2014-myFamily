package com.classes;

import java.io.File;
import java.io.FileWriter;

import android.content.Context;

public class JSonWriter {

	public static JSonWriter jSonWriter = new JSonWriter();

	public static JSonWriter getInstance() {
		return jSonWriter;
	}

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

	public void deleteFile(String fileName, Context context) {
		File file = new File(context.getFilesDir(), fileName);
		file.delete();
	}

	public boolean ifExist(String fileName, Context context) {

		File file = new File(context.getFilesDir(), fileName);
		System.out.println(context.getFilesDir());
		if (file.exists()) {
			return true;
		}

		return false;
	}

}