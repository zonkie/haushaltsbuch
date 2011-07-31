package de.soenkedomroese.haushaltsbuch;

import android.app.ListActivity;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class EintraegeAnzeigen extends ListActivity {

	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;

	private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;

	private DBAdapter DBAdapter;
	private Cursor mNotesCursor;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.eintraegeanzeigen);

		// do something when the button is clicked
		try {
			DBAdapter = new DBAdapter(this);
			DBAdapter.open();
			fillData();
		} catch (Exception e) {
			Context context = getApplicationContext();
			CharSequence text = "I'm sorry, there was en Error reading the latest Entry from the Database! "
					+ e.getMessage();
			int duration = Toast.LENGTH_LONG;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}

	}

	private void fillData() {
		// Get all of the rows from the database and create the item list
		mNotesCursor = DBAdapter.getList();
		startManagingCursor(mNotesCursor);

		String[] from = new String[] { DBAdapter.KEY_ITEMNAME };

		int[] to = new int[] { R.id.text1 };

		// Now create a simple cursor adapter and set it to display
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.eintraegeanzeigen,
				mNotesCursor, from, to);
		setListAdapter(notes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.optExit:
			finish();
			return true;
		case R.id.optShowAll:
			final Intent intentShowAll = new Intent(this,
					EintraegeAnzeigen.class);
			startActivity(intentShowAll);
			return true;
		case R.id.optInfo:
			final Intent intentAbout = new Intent(this, About.class);
			startActivity(intentAbout);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return false;

	}
}
