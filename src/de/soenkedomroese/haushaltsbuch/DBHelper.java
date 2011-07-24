package de.soenkedomroese.haushaltsbuch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	private static final String Tag = DBHelper.class.getSimpleName();
	
	public static final String DB_NAME = "Expenses";
	public static final int DB_VERSION = 1;
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE haushaltsbuch(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"category TEXT NOT NULL," +
				"direction TEXT NOT NULL," +
				"itemname TEXT NOT NULL," +
				"value TEXT NOT NULL," +
				"date TEXT NOT NULL" +
				")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	

}
