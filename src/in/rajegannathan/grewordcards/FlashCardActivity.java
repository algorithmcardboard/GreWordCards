package in.rajegannathan.grewordcards;

import in.rajegannathan.grewordcards.DatabaseContract.Wordcard;
import in.rajegannathan.grewordcards.fragments.BoundaryFragment;
import in.rajegannathan.grewordcards.fragments.DerivativeFragment;
import in.rajegannathan.grewordcards.fragments.EtymologyFragment;
import in.rajegannathan.grewordcards.fragments.MeaningFragment;
import in.rajegannathan.grewordcards.fragments.UsageFragment;
import in.rajegannathan.grewordcards.fragments.WordFragment;
import in.rajegannathan.grewordcards.localdb.DBHelper;

import java.util.EnumSet;
import java.util.logging.Logger;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class FlashCardActivity extends Activity {

	enum Fragments {
		WordScreen("WordScreen", 0, 0), MeaningScreen("MeaningScreen", 0, 1), UsageScreen("UsageScreen", 0, 2), EtymologyScreen(
				"EtymologyScreen", 0, 3), DerivativeScreen("DerivativeScreen", 0, 4);

		private String name;
		private int resourceId;

		private int position;

		Fragments(String name, int resourceId, int position) {
			this.name = name;
			this.resourceId = resourceId;
			this.position = position;
		}

		private static final EnumSet<Fragments> allFragments = EnumSet.allOf(Fragments.class);
		public static final Fragments LAST_FRAGMENT = DerivativeScreen;
		public static final Fragments FIRST_FRAGMENT = WordScreen;

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

	private BoundaryFragment boundaryFragment;
	private WordFragment wordFragment;
	private MeaningFragment meaningFragment;
	private UsageFragment usageFragment;
	private EtymologyFragment etymologyFragment;
	private DerivativeFragment derivativeFragment;

	private DBHelper mDbHelper;
	private Cursor cursor;

	private boolean swiped = true, reachedEnd = false, reachedBeginning = true;

	GestureDetector gestureDetector;
	private int currentScreen = 0;
	private static final Logger logger = Logger.getLogger(FlashCardActivity.class.getName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_card);
		boundaryFragment = new BoundaryFragment();
		changeFragment(boundaryFragment);
		this.gestureDetector = new GestureDetector(getApplicationContext(), new MyGestureListener());
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
	protected void onPostCreate(Bundle savedInstanceState) {
		wordFragment = new WordFragment();
		meaningFragment = new MeaningFragment();
		usageFragment = new UsageFragment();
		etymologyFragment = new EtymologyFragment();
		derivativeFragment = new DerivativeFragment();

		mDbHelper = new DBHelper(getApplicationContext());
		cursor = getCursorForListView();
		
		if(!cursor.isAfterLast()){
			wordFragment.setText(cursor.getString(1));
			changeFragment(wordFragment);
		}
//		cursor.moveToPrevious();

		super.onPostCreate(savedInstanceState);
	}

	@Override
	public boolean onTouchEvent(android.view.MotionEvent event) {
		this.gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	};

	private Fragment getFragment(int position) {
		Fragments currentFragment = Fragments.getFragment(position);
		Fragment fragment = null;
		switch (currentFragment) {
		case WordScreen:
			fragment = wordFragment;
			break;
		case EtymologyScreen:
			fragment = etymologyFragment;
			break;
		case DerivativeScreen:
			fragment = derivativeFragment;
			break;
		case MeaningScreen:
			fragment = meaningFragment;
			break;
		case UsageScreen:
			fragment = usageFragment;
			break;
		}
		return fragment;
	}

	private void changeFragment(Fragment fragment) {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(R.id.full_screen_frame, fragment);
		ft.commit();
	}

	private void showPreviousFragment() {
		int oldScreen = currentScreen;
		currentScreen = (currentScreen - 1 + Fragments.TOTAL_FRAGMENTS) % Fragments.TOTAL_FRAGMENTS;
		if (oldScreen == Fragments.FIRST_FRAGMENT.position && currentScreen == Fragments.LAST_FRAGMENT.position) {
			currentScreen = Fragments.FIRST_FRAGMENT.position;
		}
		logger.info("CurrentScreen " + currentScreen + " current fragment " + Fragments.getFragment(currentScreen));

		if (currentScreen == Fragments.FIRST_FRAGMENT.position) {
			cursor.moveToPrevious();
			if (!cursor.isBeforeFirst()) {
				wordFragment.setCurrentWord(cursor.getString(1));
			} else {
				wordFragment.printViews();
			}
		}
		changeFragment(getFragment(currentScreen));
	}

	private void showNextFragment() {
		if(cursor.isBeforeFirst()){
			cursor.moveToFirst();
		}
		currentScreen = (currentScreen + 1) % Fragments.TOTAL_FRAGMENTS;
		logger.info("CurrentScreen " + currentScreen + " current fragment " + Fragments.getFragment(currentScreen));

		if (currentScreen == Fragments.FIRST_FRAGMENT.position) {
			nextWord(false);
		} else {
			changeFragment(getFragment(currentScreen));
		}
	}

	private void nextWord(boolean doubleTap) {
		logger.info("in next word");
		cursor.moveToNext();
		if (cursor.isAfterLast()) {
			currentScreen = Fragments.LAST_FRAGMENT.position;
			return;
		}
		currentScreen = Fragments.FIRST_FRAGMENT.position;
		
		if(doubleTap){
			wordFragment.setCurrentWord(cursor.getString(1));
		}else{			
			wordFragment.setText(cursor.getString(1));
		}
		changeFragment(getFragment(currentScreen));
	}

	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 150;
		private static final int SWIPE_THRESHOLD_VELOCITY = 200;

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			if(cursor.isBeforeFirst()){
				cursor.moveToFirst();
			}
			if(!cursor.isAfterLast()){
				nextWord(true);
			}
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
				showNextFragment();
				swiped = true;
			}

			if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
				logger.info("swiped up" + e1.getY() + " " + e2.getY());
				if (swiped) {
					showPreviousFragment();
				}
			}

			return super.onFling(e1, e2, velocityX, velocityY);
		}
	}
}