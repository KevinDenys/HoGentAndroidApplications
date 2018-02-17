package be.kevindenys.wieishet.activity;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import be.kevindenys.wieishet.R;
import be.kevindenys.wieishet.activity.App;
import be.kevindenys.wieishet.fragments.CharacterDetailFragment;
import be.kevindenys.wieishet.fragments.CharacterMenuFragment;
import be.kevindenys.wieishet.models.Character;
import be.kevindenys.wieishet.util.CharacterDbHelper;

public class MainActivity extends AppCompatActivity {

    private CharacterDbHelper datahelper;
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (App) getApplication();
        app.setDialogContext(this); //problemen met context voor het dialoog oplossen..
        //Kijken of de application al characters in zijn geheugen heeft. Zo niet ophalen uit de databank
        if (app.getCharacters() == null) {
            getSupportLoaderManager().initLoader(0, null, loaderCallback);
        }
        if (savedInstanceState == null) {
            CharacterMenuFragment firstFragment = new CharacterMenuFragment();
            firstFragment.setApp(app);
            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft.add(R.id.flContainer, firstFragment);                                // add    Fragment
            ft.commit();                                                            // commit FragmentTransaction
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            CharacterDetailFragment secondFragment = new CharacterDetailFragment();
            Bundle args = new Bundle();
            if (app.getCharacters().size() > 0) {
                args.putSerializable("char", app.getCharacters().get(0));
                secondFragment.setArguments(args);
            }
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft2.add(R.id.flContainer2, secondFragment);                               // add    Fragment
            ft2.commit();                                                            // commit FragmentTransaction
            app.setCdf(secondFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_myguesswho) {
            app.restartGame(); //Game herstarten
        }
        if (id == R.id.action_reset) {
            app.getDatahelper().onUpgrade(app.getDatahelper().getWritableDatabase(),0,0); //Databank resetten
            app.restartAppProgram(); //Programma herstarten
        }
        return true;
    }

    public void fillInFragments() {


        // Instance of first fragment

        CharacterMenuFragment firstFragment = new CharacterMenuFragment();
        firstFragment.setApp(app);
        // Add Fragment to FrameLayout (flContainer), using FragmentManager
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
        ft.add(R.id.flContainer, firstFragment);                                // add    Fragment
        ft.commit();                                                            // commit FragmentTransaction


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //No idea if this is the correct way but I check if the container allready exists, if it does skip this. Otherwise we would have a second fragment on top
            View r = (View) findViewById(R.id.flContainer2);
            if (r == null) {
                CharacterDetailFragment secondFragment = new CharacterDetailFragment();
                Bundle args = new Bundle();
                args.putSerializable("char", app.getCharacters().get(0));
                secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle
                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
                ft2.add(R.id.flContainer2, secondFragment);                               // add    Fragment
                ft2.commit();
            }
            // commit FragmentTransaction
        }
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

//Database ophalen
    LoaderManager.LoaderCallbacks<Cursor> loaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            datahelper = new CharacterDbHelper(getApplicationContext());
            app.setDatahelper(datahelper);
            SQLiteDatabase db = datahelper.getWritableDatabase();
            if (!checkDb(db)) {
                datahelper.onUpgrade(db, 0, 0); //db clearen
                datahelper.putDataInDatabase(db); //db opvullen
            }
            app.setCharacters(datahelper.readAllDataFromDatabase(db));
            app.setNewGameChars(datahelper.readAllDataFromDatabase(db));
            datahelper.close();


            return null;

        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    //kijken of er iets in de table_characters zit, ja --> db is al opgevuld nee --> tabel is leeg
    public boolean checkDb(SQLiteDatabase db) {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + "table_characters", null);
        Boolean rowExists;

        if (mCursor.moveToFirst()) {
            rowExists = true; //Data

        } else {
            rowExists = false; //Leeg
        }
        return rowExists;
    }
}