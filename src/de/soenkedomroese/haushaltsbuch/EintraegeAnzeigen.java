package de.soenkedomroese.haushaltsbuch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class EintraegeAnzeigen extends Activity {
	DBAdapter db = new DBAdapter(this);
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		setContentView(R.layout.eintraegeanzeigen);
		
		db.open();
		// do something when the button is clicked
		try
		{ 
			String LatestEntry = db.getLatestEntry();
			TextView latestShow = (TextView) findViewById(R.id.txtShowLatestEntry);
			latestShow.setText(LatestEntry);
		} catch (Exception e) {
			Context context = getApplicationContext();
			CharSequence text = "I'm sorry, there was en Error reading the latest Entry from the Database! " + e.getMessage();
			int duration = Toast.LENGTH_LONG;
	
			
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
		db.close();
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
			final Intent intentShowAll = new Intent(this, EintraegeAnzeigen.class);
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
