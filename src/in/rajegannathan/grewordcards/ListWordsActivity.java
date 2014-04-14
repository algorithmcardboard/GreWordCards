package in.rajegannathan.grewordcards;

import in.rajegannathan.grewordcards.DatabaseContract.Wordcard;
import in.rajegannathan.grewordcards.localdb.DBHelper;

import java.util.logging.Logger;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListWordsActivity extends ListActivity {

	private static final Logger logger = Logger
			.getLogger(ListWordsActivity.class.getName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DBHelper mDbHelper = new DBHelper(getApplicationContext());
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		String[] fromColumns = { Wordcard.COLUMN_WORD };
		int[] toViews = { R.id.singleWord };

		String[] projection = { BaseColumns._ID, Wordcard.COLUMN_WORD,
				Wordcard.COLUMN_VIEWS };
		String sortOrder = Wordcard.COLUMN_VIEWS + " DESC";
		Cursor cursor = db.query(Wordcard.TABLE_NAME, projection, null, null,
				null, null, sortOrder);
		SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this,
				R.layout.fragment_list_words, cursor, fromColumns, toViews, 0);

		setListAdapter(mAdapter);

		ListView wordList = getListView();
		if (wordList != null) {
			wordList.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					logger.info("longPress on item. Position is "+position+" id is "+id);
					return false;
				}
			});
		} else {
			logger.info("wordList is empty");
			ListView listView = getListView();
			logger.info("listview is "+listView.toString());
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
}
