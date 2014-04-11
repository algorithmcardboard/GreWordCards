package in.rajegannathan.grewordcards;

import in.rajegannathan.grewordcards.DatabaseContract.Wordcard;
import in.rajegannathan.grewordcards.localdb.DBHelper;

import java.util.logging.Logger;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class HomeActivity extends Activity {

	private static final Logger logger = Logger.getLogger(HomeActivity.class
			.getName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void viewWords(View view){
        Intent intent = new Intent(this, ListWordsActivity.class);
        startActivity(intent);
	}

	public void addWord(View view) {
		EditText wordTextBox = (EditText) findViewById(R.id.add_word);
		String newWord = wordTextBox.getText().toString();
		logger.info("in add Word " + newWord);

		DBHelper mDbHelper = new DBHelper(getApplicationContext());
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Wordcard.COLUMN_WORD, newWord);
		values.put(Wordcard.COLUMN_VIEWS, 0);
		values.put(Wordcard.COLUMN_CREATED_AT, System.currentTimeMillis());
		values.put(Wordcard.COLUMN_UPDATED_AT, System.currentTimeMillis());

		db.insert(Wordcard.TABLE_NAME, null, values);
		logger.info("word inserted successfully");
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_home, container,
					false);
			return rootView;
		}
	}

}
