package in.rajegannathan.grewordcards;

import android.provider.BaseColumns;

public class DatabaseContract {

	private DatabaseContract() {
	}

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "in.rajegannathan.grewordcards.db";
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String INTEGER_TYPE = " INTEGER";

	public static final class Wordcard implements BaseColumns {
		private Wordcard() {
		}

		public static final String TABLE_NAME = "word_list";
		public static final String COLUMN_WORD = "word";
		public static final String COLUMN_VIEWS = "views";
		public static final String COLUMN_CREATED_AT = "created_at";
		public static final String COLUMN_UPDATED_AT = "updated_at";
		
		public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
				+ " (" + BaseColumns._ID + INTEGER_TYPE + " PRIMARY KEY, "
				+ COLUMN_WORD + TEXT_TYPE + COMMA_SEP 
				+ COLUMN_VIEWS + INTEGER_TYPE + COMMA_SEP
				+ COLUMN_CREATED_AT + INTEGER_TYPE + COMMA_SEP
				+ COLUMN_UPDATED_AT + INTEGER_TYPE + " )";

		public static final String DELETE_TABLE_SQL = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}
}