package com.myfamily;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.async.AddNote;
import com.async.GetNotes;
import com.async.RemoveNote;
import com.classes.Note;

public class NotesActivity extends Activity {
	/** Widget pozwalajacy na przerzucanie notatek */
	private TextSwitcher noteArea;
	/** Lista wczytujaca notatki z bazy do ich obslugi w klasie */
	public ArrayList<Note> notes;
	private TextView date;
	private TextView author;
	/** Licznik ilosci notatek. Wspomaga obsluge listy. */
	private int current;
	private ProgressDialog progressDialog;
	private Animation inPrev;
	private Animation outPrev;
	private Animation inNext;
	private Animation outNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_notes);

		author = (TextView) findViewById(R.id.author);
		date = (TextView) findViewById(R.id.date);
		noteArea = (TextSwitcher) findViewById(R.id.noteArea);
		noteArea.setFactory(new ViewFactory() {
			@Override
			public View makeView() {
				TextView myText = new TextView(NotesActivity.this);
				myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
				myText.setTextSize(20);
				return myText;
			}

		});

		inPrev = AnimationUtils.loadAnimation(this, R.anim.in_left);
		outPrev = AnimationUtils.loadAnimation(this, R.anim.out_right);
		inNext = AnimationUtils.loadAnimation(this, R.anim.in_right);
		outNext = AnimationUtils.loadAnimation(this, R.anim.out_left);

		noteArea.setInAnimation(inPrev);
		noteArea.setOutAnimation(outPrev);

		notes = new ArrayList<Note>();

		getNotes();
	}

	/**
	 * Metoda obslugi przycisku usuwania aktualnej notatki.
	 * 
	 * @param view
	 *            aktualny widok aplikacji
	 */
	public void deleteNote(View view) { // TODO

		if (current >= 0) {
			// current--;
			new RemoveNote("" + notes.get(current).getId(), "Notatka", this)
					.execute();
		}
	}

	public void delete() {
		notes.remove(current);

		if (current < 0) {
			noteArea.setText("");
		} else {
			current--;
			noteArea.setText(notes.get(current).getContent());
			author.setText(notes.get(current).getOwner());
			date.setText(notes.get(current).getDate());
		}
	}

	private void getNotes() {
		new GetNotes(NotesActivity.this).execute();
	}

	/**
	 * Metoda przypisująca notatki z bazy danych do listy.
	 * 
	 * @return zwraca liste notatek z bazy
	 */
	public void assignNotes(ArrayList<Note> n) {
		int len = n.size();

		for (int i = 0; i < len; i++) {
			notes.add(new Note(n.get(i).getId(), n.get(i).getOwner(), n.get(i)
					.getContent(), n.get(i).getDate()));
		}

		current = notes.size() - 1;

		if (current > 0) {
			noteArea.setText(notes.get(current).getContent());
			author.setText(notes.get(current).getOwner());
			date.setText(notes.get(current).getDate());
		}
	}

	/**
	 * Metoda obslugi przycisku dodawania notatki.
	 * 
	 * @param view
	 *            aktualny widok aplikacji
	 */
	public void addNote(View view) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Wprowadź notatkę");
		alert.setMessage("Notatka");

		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String tmp = input.getText().toString();
				new AddNote(tmp, NotesActivity.this).execute();
			}
		});

		alert.setNegativeButton("Anuluj",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// nic
					}
				});

		alert.show();
	}

	public void setNewNote(Note n) {
		notes.add(n);
		current = notes.size() - 1;

		noteArea.setText(notes.get(current).getContent());
		author.setText(notes.get(current).getOwner());
		date.setText(notes.get(current).getDate());
	}

	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Łączenie z serwerem");
		progressDialog.show();
	}

	/**
	 * method which hide Progress dialog
	 */
	public void hideProgressDial() {
		progressDialog.hide();
	}

	/**
	 * @param responseText
	 */
	public void showToast(String responseText) {
		System.err.println(responseText);
	}

	/**
	 * Metoda obslugi przycisku przegladania poprzedniej notatki.
	 * 
	 * @param view
	 *            aktualny widok aplikacji
	 */
	public void previousNote(View view) {
		noteArea.setInAnimation(inPrev);
		noteArea.setOutAnimation(outPrev);
		if (current > 0) {
			current--;
			noteArea.setText(notes.get(current).getContent());
			author.setText(notes.get(current).getOwner());
			date.setText(notes.get(current).getDate());
		}
	}

	/**
	 * Metoda obslugi przycisku przegladania nastepnej notatki.
	 * 
	 * @param view
	 *            aktualny widok aplikacji
	 */
	public void nextNote(View view) {
		noteArea.setInAnimation(inNext);
		noteArea.setOutAnimation(outNext);
		if (current < notes.size() - 1) {
			current++;
			noteArea.setText(notes.get(current).getContent());
			author.setText(notes.get(current).getOwner());
			date.setText(notes.get(current).getDate());
		}
	}
}