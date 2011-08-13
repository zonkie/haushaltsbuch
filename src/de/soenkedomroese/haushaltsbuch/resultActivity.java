package de.soenkedomroese.haushaltsbuch;

import java.sql.Date;

import de.soenkedomroese.haushaltsbuch.DBAdapter;

import android.R.drawable;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class resultActivity extends Activity {
	DBAdapter db = new DBAdapter(this);

	@Override
	public void onCreate(Bundle icicle) {

		super.onCreate(icicle);
		setContentView(R.layout.result);
		Log.d("Soenke","fetch extras");
		final Bundle extras = getIntent().getExtras();
		db.open();
		if (extras != null) {
			Log.d("Soenke","Extras != null");
			try {
				Context context = getApplicationContext();
				Log.d("Soenke","context getApplicationContext");
				CharSequence bubbleText = "";
				String rowid = extras.getString(DBAdapter.KEY_ROWID);
				if ( rowid.equals(EintragHinzufuegen.NEWID)) {
					Log.d("Soenke","ID == " + EintragHinzufuegen.NEWID + " ( " + rowid  +" )");
					db.insertExpense(
							extras.getString(EintragHinzufuegen.CATEGORY),
							"",
							extras.getString(EintragHinzufuegen.DIRECTION),
							extras.getString(EintragHinzufuegen.NAME),
							extras.getString(EintragHinzufuegen.AMOUNT));
					bubbleText = "The expense was added successfully!";
				} else {
					Log.d("Soenke","ID != " + EintragHinzufuegen.NEWID + ", but : " + rowid );
					db.updateExpense(extras.getString(DBAdapter.KEY_ROWID),
							extras.getString(EintragHinzufuegen.CATEGORY),
							"",
							extras.getString(EintragHinzufuegen.DIRECTION),
							extras.getString(EintragHinzufuegen.NAME),
							extras.getString(EintragHinzufuegen.AMOUNT));
					bubbleText = "The expense was updated successfully!";
				}

				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, bubbleText, duration);
				toast.show();

			} catch (Exception e) {
				db.close();
				Context context = getApplicationContext();
				CharSequence text = "I'm sorry, there was en Error writing the Entry into the Database! "+ e.getMessage();
				int duration = Toast.LENGTH_LONG;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();

			}
			db.close();

			final TextView resultName = (TextView) findViewById(R.id.txtResultName);
			resultName.setText(extras.getString(EintragHinzufuegen.NAME));
			
			final TextView resultCategory = (TextView) findViewById(R.id.txtResultCategory);
			resultCategory.setText(extras.getString(EintragHinzufuegen.CATEGORY)); // String.valueOf(extras.get(EintragHinzufuegen.CATEGORY).toString()));

			final TextView resultAmount = (TextView) findViewById(R.id.txtResultAmount);
			resultAmount.setText(extras.getString(EintragHinzufuegen.AMOUNT));

			final ImageView directionImg = (ImageView) findViewById(R.id.imgResultDirection);
			final TextView resultDirection = (TextView) findViewById(R.id.txtResultDirection);
			
			if (extras.getString(EintragHinzufuegen.DIRECTION).equalsIgnoreCase("1")) {
				directionImg.setImageResource(R.drawable.direction_in);
				resultDirection.setText(R.string.radioIn);
			} else {
				directionImg.setImageResource(R.drawable.direction_out);
				resultDirection.setText(R.string.radioOut);
			}
			final TextView date = (TextView) findViewById(R.id.txtResultDate);
			date.setText(extras.getString(EintragHinzufuegen.DATE));
			
			
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
