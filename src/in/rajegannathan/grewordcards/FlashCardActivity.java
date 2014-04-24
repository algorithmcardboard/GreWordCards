package in.rajegannathan.grewordcards;

import in.rajegannathan.grewordcards.DatabaseContract.Wordcard;
import in.rajegannathan.grewordcards.localdb.DBHelper;

import java.util.EnumSet;
import java.util.logging.Logger;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

public class FlashCardActivity extends Activity {

	enum Fragments {
		WordScreen("WordScreen", R.id.fullscreen_content, 0), MeaningScreen("MeaningScreen", R.id.fullscreen_content, 1), UsageScreen(
				"UsageScreen", R.id.fullscreen_content, 2), EtymologyScreen("EtymologyScreen", R.id.fullscreen_content, 3), DerivativeScreen(
				"DerivativeScreen", R.id.fullscreen_content, 4);

		private String name;
		private int resourceId;

		private int position;

		Fragments(String name, int resourceId, int position) {
			this.name = name;
			this.resourceId = resourceId;
			this.position = position;
		}

		private static final EnumSet<Fragments> allFragments = EnumSet.allOf(Fragments.class);

		public static final int TOTAL_FRAGMENTS = allFragments.size();

		public String getName() {
			return name;
		}

		public int getResourceId() {
			return resourceId;
		}

		public int getPosition() {
			return position;
		}

		public static Fragments getFragment(int position) {
			for (Fragments fragment : allFragments) {
				if (fragment.position == position) {
					return fragment;
				}
			}
			return null;
		}
	}

	GestureDetector gestureDetector;
	private int currentScreen = 0;
	private static final Logger logger = Logger.getLogger(FlashCardActivity.class.getName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_card);

		this.gestureDetector = new GestureDetector(getApplicationContext(), new MyGestureListener());
	}

	@Override
	public boolean onTouchEvent(android.view.MotionEvent event) {
		this.gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	};

	private void movePrevious() {
		currentScreen = (currentScreen - 1 + Fragments.TOTAL_FRAGMENTS) % Fragments.TOTAL_FRAGMENTS;
		logger.info("CurrentScreen " + currentScreen + " current fragment " + Fragments.getFragment(currentScreen));
	}

	private void moveNext() {
		currentScreen = (currentScreen + 1) % Fragments.TOTAL_FRAGMENTS;
		logger.info("CurrentScreen " + currentScreen + " current fragment " + Fragments.getFragment(currentScreen));
	}

	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

		private DBHelper mDbHelper;
		private Cursor cursor;
		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 150;
		private static final int SWIPE_THRESHOLD_VELOCITY = 200;

		public MyGestureListener() {
			mDbHelper = new DBHelper(getApplicationContext());
			cursor = getCursorForListView();
		}

		private Cursor getCursorForListView() {
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			String[] projection = { BaseColumns._ID, Wordcard.COLUMN_WORD, Wordcard.COLUMN_VIEWS };
			String sortOrder = Wordcard.COLUMN_VIEWS + " ASC";
			Cursor cursor = db.query(Wordcard.TABLE_NAME, projection, null, null, null, null, sortOrder);
			cursor.moveToFirst();
			return cursor;
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			TextView wordText = (TextView) findViewById(R.id.fullscreen_content);
			if (cursor.isAfterLast()) {
				logger.info("cursor.isAfterLast is " + cursor.isAfterLast());
				wordText.setTypeface(Typeface.DEFAULT);
				wordText.setText("Reached the end of your list. Swipe up for previous words.");
				return false;
			}
			String word = cursor.getString(1);
			wordText.setTypeface(null, Typeface.BOLD);
			wordText.setText(word);

			logger.info("in double tab event" + (cursor.isAfterLast() ? "" : cursor.getString(1)));
			cursor.moveToNext();

			return super.onDoubleTap(e);
		};

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			logger.info("in fling event");

			if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH) {
				logger.info("in swipe max off path");
				return false;
			}

			if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
				logger.info("swiped down" + e1.getY() + " " + e2.getY());
				moveNext();
			}

			if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
				logger.info("swiped up" + e1.getY() + " " + e2.getY());
				movePrevious();
			}

			return super.onFling(e1, e2, velocityX, velocityY);
		}
	}
}