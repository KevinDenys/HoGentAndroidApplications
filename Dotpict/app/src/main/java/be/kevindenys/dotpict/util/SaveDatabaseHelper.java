package be.kevindenys.dotpict.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import be.kevindenys.dotpict.models.Save;

/**
 * Created by Kevin on 19/01/2018.
 */

public class SaveDatabaseHelper extends SQLiteOpenHelper {
    private static String TAG = "SaveDatabaseHelper";

    public SaveDatabaseHelper(Context context) {
        super(context, SaveDatabaseClass.DATABASE_NAME, null, SaveDatabaseClass.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SaveDatabaseClass.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SaveDatabaseClass.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    //Insert
    public void putSaveInDb(Save c, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(SaveDatabaseClass.SaveDBEntry.COLUMN_NAME, c.getName());
        values.put(SaveDatabaseClass.SaveDBEntry.COLUMN_GRID, c.getGridJSON());
        db.insert(SaveDatabaseClass.SaveDBEntry.TABLE_NAME , null , values);
    }
    //Remove
    public boolean removeFromDatabase(Save s, SQLiteDatabase db){
        return db.delete(SaveDatabaseClass.SaveDBEntry.TABLE_NAME, SaveDatabaseClass.SaveDBEntry._ID + "=" + s.getId(), null) > 0;
    }
    //Read
    public ArrayList<Save> readAllDataFromDatabase(SQLiteDatabase db){

        ArrayList<Save> saves = new ArrayList<>();
        //Gegevens die we ophalen
        String[] projection = {
                SaveDatabaseClass.SaveDBEntry._ID,
                SaveDatabaseClass.SaveDBEntry.COLUMN_NAME,
                SaveDatabaseClass.SaveDBEntry.COLUMN_GRID
        };

        Cursor c = db.query(
                SaveDatabaseClass.SaveDBEntry.TABLE_NAME,    // De tabel om te queryen
                projection,                                 // De kolommen om terug te keren
                null, // De kolommen voor de WHERE-component
                null, // De waarden voor de WHERE-component
                null, // groepeer de rijen niet
                null, // niet filteren op rijgroepen
                null // De sorteervolgorde
        );
        //werkt zoals een iterator
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            saves.add(new Save(c.getLong(0),c.getString(1),c.getString(2)));
            c.moveToNext();
        }
        c.close();
        return saves;
    }
}
