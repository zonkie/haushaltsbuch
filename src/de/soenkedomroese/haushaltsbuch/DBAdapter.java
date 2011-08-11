package de.soenkedomroese.haushaltsbuch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	int id = 0;
	public static final String KEY_ROWID = "_id";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_DIRECTION = "direction";
	public static final String KEY_ITEMNAME = "itemname";
	public static final String KEY_VALUE = "value";
	public static final String KEY_DATE = "date";

	private static final String TAG = "DBAdapter";

	private static final String DATABASE_NAME = "haushaltsbuch";
	private static final String DATABASE_TABLE = "haushaltsbuch";
	private static final int DATABASE_VERSION = 13;

	private static final String DATABASE_CREATE = "CREATE TABLE "
			+ DATABASE_TABLE + "(" 
			+ KEY_ROWID	+ " INTEGER PRIMARY KEY ASC,"
			+ KEY_CATEGORY + " TEXT NOT NULL,"
			+ KEY_DATE + " TEXT NOT NULL,"
			+ KEY_DIRECTION + " TEXT NOT NULL,"
			+ KEY_ITEMNAME + " TEXT NOT NULL,"
			+ KEY_VALUE + " TEXT NOT NULL"
			+ ")";

	private final Context context;

	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	// ---opens the database---
	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---closes the database---
	public void close() {
		DBHelper.close();
	}

	// ---insert an Entry into the database---
	public long insertExpense(String Category, String Date, String Direction, String Itemname, String Value) {
		long retval;
		try{
			Log.d("Soenke","insertExpense");
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_CATEGORY, ""+Category+"");
			initialValues.put(KEY_DATE, ""+Date+"");
			initialValues.put(KEY_DIRECTION, ""+ Direction+"");
			initialValues.put(KEY_ITEMNAME, ""+Itemname+"");
			initialValues.put(KEY_VALUE, ""+Value+"");
			Log.d("Soenke", "Insert: " + Category + " - " + Date + " - " + Direction + " - " + Itemname + " - " + Value);
			retval = db.insert(DATABASE_TABLE, null, initialValues);
		} catch (Exception e) {
			retval = 0;
		}
		return retval;
	}

	public long updateExpense(String RowId, String Category, String Date, String Direction,
			String Itemname, String Value) {
		long retval;
		try{
			Log.d("Soenke","updateExpense");
			ContentValues updateValues = new ContentValues();
			updateValues.put(KEY_CATEGORY, ""+Category+"");
			updateValues.put(KEY_DATE, ""+Date+"");
			updateValues.put(KEY_DIRECTION, ""+Direction+"");
			updateValues.put(KEY_ITEMNAME, ""+Itemname+"");
			updateValues.put(KEY_VALUE, ""+Value+"");
			Log.d("Soenke", "Update: " + Category + " - " + Date + " - " + Direction + " - " + Itemname + " - " + Value);
			retval = db.update(DATABASE_TABLE, updateValues, KEY_ROWID+"="+ Integer.parseInt(RowId), null);
		} catch (Exception e) {
			retval = 0;
		}
		return retval;
	}
	
	public int getEntryCount() {
		Cursor cursor = db.rawQuery("SELECT COUNT("+ KEY_ROWID +") FROM " + DATABASE_TABLE,
				null);
		if (cursor.moveToFirst()) {
			return cursor.getInt(0);
		}
		return cursor.getInt(0);

	}

	public int getMaxId() {
		Cursor cursor = db.rawQuery("SELECT MAX(" + KEY_ROWID + ") FROM "
				+ DATABASE_TABLE, null);
		if (cursor.moveToFirst()) {
			return cursor.getInt(0);
		}
		return cursor.getInt(0);

	}

	public String getLatestEntry() {

		id = getMaxId();
		Cursor cursor = db.rawQuery("SELECT " + KEY_CATEGORY + "|| '|' || "
				+ KEY_DIRECTION + "|| '|' || " + KEY_ITEMNAME + "|| '|' || "
				+ KEY_VALUE + "|| '|' || " + KEY_DATE + " AS entry FROM "
				+ DATABASE_TABLE + " WHERE " + KEY_ROWID + " = " + id, null);
		if (cursor.moveToFirst()) {
			return cursor.getString(0);
		}
		return cursor.getString(0);

	}

	public Cursor getList() {
		return db.rawQuery("" +
				"SELECT " + KEY_ROWID + ","
				+ KEY_ITEMNAME + "|| '|' || " + KEY_DIRECTION + "|| '|' || "+ KEY_VALUE
				+ " AS " + KEY_ITEMNAME
				+ " FROM "
				+ DATABASE_TABLE, null);
	}

	public Cursor fetchOne(long rowId) throws SQLException {

		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_CATEGORY, KEY_DATE, KEY_DIRECTION, KEY_ITEMNAME, KEY_VALUE }, KEY_ROWID + "=" + rowId, null, null,
				null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

}