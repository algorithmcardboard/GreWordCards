package in.rajegannathan.grewordcards;

import java.util.logging.Logger;

import in.rajegannathan.grewordcards.DatabaseContract.Wordcard;
import in.rajegannathan.grewordcards.localdb.DBHelper;
import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class ListWordsActivity extends Activity {
	
	private static final Logger logger = Logger.getLogger(ListWordsActivity.class
			.getName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_words);
		
		DBHelper mDbHelper = new DBHelper(getApplicationContext());
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		
		String [] projection = {
			Wordcard.COLUMN_WORD,
			Wordcard.COLUMN_VIEWS
		};
		String sortOrder = Wordcard.COLUMN_VIEWS + " DESC";
		
		Cursor cursor = db.query(Wordcard.TABLE_NAME, projection, null, null, null, null, sortOrder);
		logger.info(cursor.toString());
		
		logger.info(""+cursor.getCount());
		logger.info(cursor.getColumnNames()[0].toString()+" "+cursor.getColumnNames()[1].toString());
		
		cursor.moveToFirst();
		while(cursor.isAfterLast() == false){
			logger.info(cursor.getString(1));
			logger.info(cursor.getString(0));
			cursor.moveToNext();
		}

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_words, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_list_words,
					container, false);
			return rootView;
		}
	}

}
