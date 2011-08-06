package de.soenkedomroese.haushaltsbuch;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class EintragHinzufuegen extends Activity {
	
	public static final int AUSGEBEN_ID = Menu.FIRST; // Menu Item for first
	public static final String CATEGORY = "category";
	public static final String AMOUNT = "amount";
	public static final String DIRECTION = "direction";
	public static final String NAME = "name";
	public static final String DATE = "date";

	private EditText name;
	private Spinner category;
	private Spinner direction;
	private EditText amount;
	private EditText date;
	private Long mRowId;
	
	private Cursor mCursor;
	

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		name = (EditText) findViewById(R.id.editName);
		category = (Spinner) findViewById(R.id.spinnerCategories);
		direction = (Spinner) findViewById(R.id.spinnerDirection);
		amount = (EditText) findViewById(R.id.editTextAmount);
		date = (EditText) findViewById(R.id.editDate);

			
		mRowId = null;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mRowId = extras.getLong(DBAdapter.KEY_ROWID);
			Log.w("woot?", "RowID" + String.valueOf(mRowId));
			DBAdapter db = new DBAdapter(this);
			db.open();
			mCursor = db.fetchOne(mRowId);
			startManagingCursor(mCursor);
			
			name.setText(mCursor.getString(4));
			category.setSelection(extras.getInt(CATEGORY), true);
			direction.setSelection(extras.getInt(DIRECTION), true);
			amount.setText(mCursor.getString(5));
			date.setText(mCursor.getString(2));
			stopManagingCursor(mCursor);
			mCursor.close();
			db.close();
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, AUSGEBEN_ID, Menu.NONE, R.string.mnuEintragen);
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void onClickEintragen(final View view) {
		try {
			
			final EditText name = (EditText) findViewById(R.id.editName);
			
			final Spinner chooseCategory = (Spinner) findViewById(R.id.spinnerCategories);
			final int pos = chooseCategory.getSelectedItemPosition();
			final int[] categories = getResources().getIntArray(
					R.array.categories_values);
			final int categoryInt = categories[pos];

			// Amount from editText
			final EditText amount = (EditText) findViewById(R.id.editTextAmount);

			// direction from RadioGroup
			final Spinner direction = (Spinner) findViewById(R.id.spinnerDirection);
			final int dpos = direction.getSelectedItemPosition();
			final int[] directions = getResources().getIntArray(
					R.array.directionValues);
			final int directionInt = directions[dpos];

			final Intent intent = new Intent(this, resultActivity.class);
			if (mRowId != null) {
				intent.putExtra(DBAdapter.KEY_ROWID, String.valueOf(mRowId));
			} else {
				intent.putExtra(DBAdapter.KEY_ROWID, "new");
			}
			intent.putExtra(NAME, String.valueOf(name.getText()));
			intent.putExtra(CATEGORY, String.valueOf(categoryInt));
			intent.putExtra(AMOUNT, String.valueOf(amount.getText()));
			intent.putExtra(NAME, String.valueOf(directionInt));
			intent.putExtra(DATE, String.valueOf(date.getText()));

			startActivity(intent);
			
		} catch (Exception e) {
			// TODO: handle exception

		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case AUSGEBEN_ID:
			// Category from Spinner
			final Spinner chooseCategory = (Spinner) findViewById(R.id.spinnerCategories);
			final int CategoryPos = chooseCategory.getSelectedItemPosition();
			final int[] categories = getResources().getIntArray(
					R.array.categories_values);
			final int categoryInt = categories[CategoryPos];

			// direction from RadioGroup
			final Spinner direction = (Spinner) findViewById(R.id.spinnerDirection);
			final int DirectionPos = direction.getSelectedItemPosition();
			final int[] directions = getResources().getIntArray(
					R.array.directionValues);
			final int directionInt = directions[DirectionPos];

			// Amount from editText
			final EditText amount = (EditText) findViewById(R.id.editTextAmount);

			final Intent intentSubmit = new Intent(this, resultActivity.class);
			
			intentSubmit.putExtra(DBAdapter.KEY_ROWID, String.valueOf(mRowId));	
			intentSubmit.putExtra(NAME, String.valueOf(name.getText()));
			intentSubmit.putExtra(CATEGORY, String.valueOf(categoryInt));
			intentSubmit.putExtra(AMOUNT, String.valueOf(amount.getText()));
			intentSubmit.putExtra(NAME, String.valueOf(directionInt));
			intentSubmit.putExtra(DATE, String.valueOf(date.getText()));

			startActivity(intentSubmit);

			break;
		case R.id.optInfo:
			final Intent intentAbout = new Intent(this, About.class);
			startActivity(intentAbout);
			break;
		case R.id.optShowAll:
			final Intent intentShowAll = new Intent(this, EintraegeAnzeigen.class);
			startActivity(intentShowAll);
			return true;
		case R.id.optExit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		return false;

	}

}