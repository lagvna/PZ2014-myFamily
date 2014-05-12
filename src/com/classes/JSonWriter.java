package com.classes;

import java.io.File;
import java.io.FileWriter;


public class JSonWriter {

	public static JSonWriter jSonWriter = new JSonWriter();

	public static JSonWriter getInstance() {
		return jSonWriter;
	}

	public void appendToFile(String fileName, String text, int writeMode) {

		FileWriter f;
		if (writeMode == 0) {// pusty plik, do ktorego dodajemy tekst

			try {
				f = new FileWriter(fileName);
				f.write(text);
				f.flush();
				f.close();
			} catch (Exception a) {
				a.printStackTrace();
			}
		} else { // dopisywanie do pliku juz istniejacego

			try {
				f = new FileWriter(fileName,true);
				f.write(text);
				f.flush();
				f.close();
			} catch (Exception a) {
				a.printStackTrace();
			}

		}
	}

	private void deleteFile(String fileName) {
		File file = new File(fileName);
		file.delete();
	}
}
