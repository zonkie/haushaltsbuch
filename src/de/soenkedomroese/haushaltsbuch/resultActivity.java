package de.soenkedomroese.haushaltsbuch;

import java.sql.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.TimeUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class resultActivity extends Activity {

	@Override
	public void onCreate(Bundle icicle){
		
		super.onCreate(icicle);
		setContentView(R.layout.result);
		
		final Bundle extras = getIntent().getExtras();
		
		if (extras != null){
			//final ArrayKeyValueMapper mapme = new ArrayKeyValueMapper();
			//mapme.
			HaushaltsbuchDatabase hDb = new HaushaltsbuchDatabase(getBaseContext());
			SQLiteDatabase dbconn = hDb.getReadableDatabase();
			
			//"INSERT INTO haushaltsbuch (category,direction,itemname,direction,value,date) values(?,?,?,?,?,?)"
			SQLiteStatement stmtInsert = dbconn.compileStatement("INSERT INTO haushaltsbuch (category,direction,itemname,value,date) values(?,?,?,?,?)");
			stmtInsert.bindString(1, extras.getString(EintragHinzufuegen.CATEGORY));// Category
			stmtInsert.bindString(2, extras.getString(EintragHinzufuegen.DIRECTION));//direction
			stmtInsert.bindString(3, "");//itemname
			stmtInsert.bindString(4, extras.getString(EintragHinzufuegen.AMOUNT));// value
			stmtInsert.bindString(5, "");//date
			
			stmtInsert.execute();
			stmtInsert.close();
			
			final TextView resultCategory = (TextView) findViewById(R.id.txtResultCategory);
			resultCategory.setText(extras.getString(EintragHinzufuegen.CATEGORY)); // String.valueOf(extras.get(EintragHinzufuegen.CATEGORY).toString()));

			final TextView resultAmount = (TextView) findViewById(R.id.txtResultAmount);
			resultAmount.setText(extras.getString(EintragHinzufuegen.AMOUNT));

			final TextView resultDirection = (TextView) findViewById(R.id.txtResultDirection);
			resultDirection.setText(extras.getString(EintragHinzufuegen.DIRECTION));

			final TextView status = (TextView) findViewById(R.id.textView1);
			status.setText(R.string.msgSuccess);

		} else {
			final TextView status = (TextView) findViewById(R.id.textView1);
			status.setText(R.string.msgError);

		}
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
