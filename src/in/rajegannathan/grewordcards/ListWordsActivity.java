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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListWordsActivity extends ListActivity {

	private static final Logger logger = Logger
			.getLogger(ListWordsActivity.class.getName());
	private DBHelper mDbHelper;
	private SimpleCursorAdapter mAdapter;
	
	private Cursor getCursorForListView(){
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		String[] projection = { BaseColumns._ID, Wordcard.COLUMN_WORD,
				Wordcard.COLUMN_VIEWS };
		String sortOrder = Wordcard.COLUMN_WORD + " ASC";
		Cursor cursor = db.query(Wordcard.TABLE_NAME, projection, null, null,
				null, null, sortOrder);
		return cursor;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDbHelper = new DBHelper(getApplicationContext());

		String[] fromColumns = { Wordcard.COLUMN_WORD };
		int[] toViews = { R.id.singleWord };
		
		Cursor cursor = getCursorForListView();
		mAdapter = new SimpleCursorAdapter(this,
				R.layout.fragment_list_words, cursor, fromColumns, toViews, 0);

		setListAdapter(mAdapter);
		
		ListView wordList = getListView();
		registerForContextMenu(wordList);
		
/*		if (wordList != null) {
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
		}*/
	}
	
	@Override
	public void onCreateContextMenu(android.view.ContextMenu menu, View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
		logger.info("in create context view method");
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.word_context_menu, menu);
	};
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		logger.info("Selected item is " + item.getItemId());
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		logger.info("selected item is at "+info.id);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		db.delete(Wordcard.TABLE_NAME, Wordcard.COLUMN_ID+" = ?", new String[]{"" +info.id});
		
		Cursor cursor = getCursorForListView();
		mAdapter.changeCursor(cursor);
		
		return super.onContextItemSelected(item);
	};
	

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
