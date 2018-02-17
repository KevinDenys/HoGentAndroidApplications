package be.kevindenys.wieishet.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import be.kevindenys.wieishet.data.DataCharacterHandler;
import be.kevindenys.wieishet.models.Character;

/**
 * Created by Kevin on 3/12/2017.
 */

public class CharacterDbHelper extends SQLiteOpenHelper {
    private static String TAG = "CharacterDbHelper";

    public CharacterDbHelper(Context context) {
        super(context, CharacterDbClass.DATABASE_NAME, null, CharacterDbClass.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CharacterDbClass.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(CharacterDbClass.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    //Update character
    public boolean updateInDatabase(Character c, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(CharacterDbClass.ChracterDBEntry.COLUMN_GAME, c.getGameID());
        values.put(CharacterDbClass.ChracterDBEntry.COLUMN_IMAGE, c.getCharacterImage());
        values.put(CharacterDbClass.ChracterDBEntry.COLUMN_NAME, c.getName());
        values.put(CharacterDbClass.ChracterDBEntry.COLUMN_DESCRIPTION, c.getDescription());
        return db.update(CharacterDbClass.ChracterDBEntry.TABLE_NAME,values,CharacterDbClass.ChracterDBEntry._ID + "=" + c.getDatabaseID(), null) > 0;
    }
    //Remove character
    public boolean removeFromDatabase(Character c,SQLiteDatabase db){
        return db.delete(CharacterDbClass.ChracterDBEntry.TABLE_NAME, CharacterDbClass.ChracterDBEntry._ID + "=" + c.getDatabaseID(), null) > 0;
    }

    //1 character in databank seteken
    public void putCustomCharacterInDb(Character c, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(CharacterDbClass.ChracterDBEntry.COLUMN_GAME, 0);
        values.put(CharacterDbClass.ChracterDBEntry.COLUMN_IMAGE, c.getCharacterImage());
        values.put(CharacterDbClass.ChracterDBEntry.COLUMN_NAME, c.getName());
        values.put(CharacterDbClass.ChracterDBEntry.COLUMN_DESCRIPTION, c.getDescription());
        db.insert(CharacterDbClass.ChracterDBEntry.TABLE_NAME , null , values);
    }
//Alle characters van de datacharacterhandler in de databank steken
    public void putDataInDatabase(SQLiteDatabase db){
        for(int i = 0; i < DataCharacterHandler.DATA_CHARACTERS.length; i++){
            ContentValues values = new ContentValues();
            values.put(CharacterDbClass.ChracterDBEntry.COLUMN_GAME, 1);
            values.put(CharacterDbClass.ChracterDBEntry.COLUMN_IMAGE, DataCharacterHandler.DATA_CHARACTERS[i].getCharacterImage());
            values.put(CharacterDbClass.ChracterDBEntry.COLUMN_NAME, DataCharacterHandler.DATA_CHARACTERS[i].getName());
            values.put(CharacterDbClass.ChracterDBEntry.COLUMN_DESCRIPTION, DataCharacterHandler.DATA_CHARACTERS[i].getDescription());
            db.insert(CharacterDbClass.ChracterDBEntry.TABLE_NAME , null , values);
        }
    }

    public ArrayList<Character> readAllDataFromDatabase(SQLiteDatabase db){
        Log.d("Loading","Loaded");
        ArrayList<Character> characters = new ArrayList<>();
        //Gegevens die we ophalen
        String[] projection = {
                CharacterDbClass.ChracterDBEntry._ID,
                CharacterDbClass.ChracterDBEntry.COLUMN_GAME,
                CharacterDbClass.ChracterDBEntry.COLUMN_IMAGE,
                CharacterDbClass.ChracterDBEntry.COLUMN_NAME,
                CharacterDbClass.ChracterDBEntry.COLUMN_DESCRIPTION

        };

        Cursor c = db.query(
                CharacterDbClass.ChracterDBEntry.TABLE_NAME,    // De tabel om te queryen
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
            characters.add(new Character(c.getInt(0),c.getInt(1),c.getInt(2),c.getString(3),c.getString(4)));
            c.moveToNext();
        }
        c.close();
        return characters;
    }
}

