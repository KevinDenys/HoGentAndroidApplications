package be.kevindenys.dotpict.util;

import android.provider.BaseColumns;

/**
 * Created by Kevin on 19/01/2018.
 */

public class SaveDatabaseClass {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dotpict.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    public static class SaveDBEntry implements BaseColumns {
        public static final String COLUMN_GRID = "grid";
        public static final String COLUMN_NAME = "name";
        public static final String TABLE_NAME = "table_saves";
    }
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SaveDBEntry.TABLE_NAME + " (" +
                    SaveDBEntry._ID + " INTEGER PRIMARY KEY," +
                    SaveDBEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    SaveDBEntry.COLUMN_GRID + TEXT_TYPE +
                     " )";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + SaveDBEntry.TABLE_NAME;
}
