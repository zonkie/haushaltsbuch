package de.soenkedomroese.haushaltsbuch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class EintragHinzufuegen extends Activity {
	public static final int AUSGEBEN_ID = Menu.FIRST; // Menu Item for first
														// Activity
	public static final String CATEGORY = "category";
	public static final String AMOUNT = "amount";
	public static final String DIRECTION = "direction";
	public static final String NAME = "name";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, AUSGEBEN_ID, Menu.NONE, R.string.mnuEintragen);
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void onClickEintragen(final View view) {
		try {
			
			final EditText name = (EditText) findViewById(R.id.txtName);
			
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

			intent.putExtra(CATEGORY, String.valueOf(categoryInt));
			intent.putExtra(AMOUNT, String.valueOf(amount.getText()));
			intent.putExtra(DIRECTION, String.valueOf(directionInt));
			intent.putExtra(NAME, String.valueOf(name.getText()));

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

			intentSubmit.putExtra(CATEGORY, String.valueOf(categoryInt));
			intentSubmit.putExtra(AMOUNT, String.valueOf(amount.getText()));
			intentSubmit.putExtra(DIRECTION, String.valueOf(directionInt));

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