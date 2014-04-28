package in.rajegannathan.grewordcards;

import in.rajegannathan.grewordcards.DatabaseContract.Words;
import in.rajegannathan.grewordcards.localdb.DBHelper;

import java.util.logging.Logger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends Activity {

	private static final Logger logger = Logger.getLogger(HomeActivity.class
			.getName());
	private EditText wordTextBox = null;
	private Button addWordsButton;
	private boolean resetButtonText = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		addWordsButton = (Button) findViewById(R.id.button_add);
		addWordsButton.setEnabled(false);

		wordTextBox = (EditText) findViewById(R.id.add_word);
		wordTextBox.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if(!resetButtonText){
					return;
				}
				resetButtonText = false;
				addWordsButton.setText(R.string.button_add);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (wordTextBox.getText().toString().trim().length() != 0) {
					addWordsButton.setEnabled(true);
				}
			}
		});
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

	public void viewWords(View view) {
		Intent intent = new Intent(this, ListWordsActivity.class);
		startActivity(intent);
	}
	
	public void playFlashCards(View view) {
		Intent intent = new Intent(this, FlashCardActivity.class);
		startActivity(intent);
	}

	public void parentClick(View view) {
		logger.info("in parent click");
		hideSoftInput();
	}

	public void addWord(View view) {
		String newWord = wordTextBox.getText().toString().trim();
		if (newWord.length() == 0) {
			return;
		}
		logger.info("in add Word " + newWord);
		
		DBHelper mDbHelper = new DBHelper(getApplicationContext());
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(Words.COLUMN_WORD, newWord);
//		values.put(Words.COLUMN_VIEWS, 0);
//		values.put(Words.COLUMN_CREATED_AT, System.currentTimeMillis());
//		values.put(Words.COLUMN_UPDATED_AT, System.currentTimeMillis());

		Object[] bindArgs = {newWord, System.currentTimeMillis(), System.currentTimeMillis(), newWord};
		logger.info("Insert SQL is "+Words.INSERT_SQL);
		logger.info("bindargs is "+ bindArgs.toString());
		db.execSQL(Words.INSERT_SQL, bindArgs);
//		db.insert(Words.TABLE_NAME, null, values);
		wordTextBox.setText("");
		addWordsButton.setText("Added '"+newWord+"'");
		resetButtonText = true;
		addWordsButton.setEnabled(false);
		hideSoftInput();
	}

	private void hideSoftInput() {
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null
				: getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
