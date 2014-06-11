package com.myfamily;

/*! \mainpage meFamily v1.0 strona główna...
 *
 * Program zrealizowany na potrzeby kursu Projekt Zespolowy 2013/2014.
 *
 * Dotyczy zagadnienia calosciowej realizacji aplikacji na system Android.
 *
 * Jest to aplikacja na system operacyjny android, wspomagajaca zycie rodziny lub grupy osob mieszkajacej razem.
 * Dzieki niej mamy screntralizowane miejsce, w ktorej takie rzeczy jak wydatki, zakupy czy wazne wydarzenia z zycia grupy
 * mozna efektywnie administrowac.
 * 
 * W aplikacji mozna kontrolowac wydatki, przegladac listy zakupow, przydzielac uzytkownikom zadania oraz przyznawac im za to nagrody,
 * robic i przesylac zdjecia, notatki, i przegladac wydarzenia rodzinne.
 * 
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Klasa splash screena uruchamiajacego i wyswietlajacego logo aplikacji.
 * 
 * @author lagvna
 * 
 */
public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);

		/**
		 * Watek sluzacy przetrzymaniu splash screena przez dwie sekundy.
		 */
		final Thread splashThread = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				Intent intent = new Intent(SplashActivity.this,
						LoginActivity.class);
				SplashActivity.this.startActivity(intent);
				SplashActivity.this.finish();
			}
		};
		splashThread.start();
	}
}