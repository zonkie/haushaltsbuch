package de.soenkedomroese.haushaltsbuch;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HaushaltsbuchDatabase extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "haushaltsbuch.db";
	private static final int DATABASE_VERSION = 1;

	public HaushaltsbuchDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	public void onCreate(SQLiteDatabase db) {
		// TODO implement something...
		db.execSQL("CREATE TABLE haushaltsbuch(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"category TEXT NOT NULL," +
				"direction TEXT NOT NULL," +
				"itemname TEXT NOT NULL," +
				"value TEXT NOT NULL," +
				"date TEXT NOT NULL" +
				")");
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		
	}

}
