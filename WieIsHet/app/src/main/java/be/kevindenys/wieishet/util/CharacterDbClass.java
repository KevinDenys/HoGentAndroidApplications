package be.kevindenys.wieishet.util;

import android.provider.BaseColumns;

/**
 * Created by Kevin on 3/12/2017.
 */

public class CharacterDbClass {
    private CharacterDbClass(){}

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "characterdb.db";

    public static class ChracterDBEntry implements BaseColumns {
        public static final String COLUMN_GAME = "game";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String TABLE_NAME = "table_characters";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    //Data base table aan maken, ID is primary key, maar we gebruiken ID die basecolums heeft
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ChracterDBEntry.TABLE_NAME + " (" +
                    ChracterDBEntry._ID + " INTEGER PRIMARY KEY," +
                    ChracterDBEntry.COLUMN_GAME + INTEGER_TYPE + COMMA_SEP +
                    ChracterDBEntry.COLUMN_IMAGE + INTEGER_TYPE + COMMA_SEP +
                    ChracterDBEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    ChracterDBEntry.COLUMN_DESCRIPTION + TEXT_TYPE + " )";


    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ChracterDBEntry.TABLE_NAME;
}

