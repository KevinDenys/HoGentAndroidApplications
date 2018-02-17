package be.kevindenys.dotpict.activity;


import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import be.kevindenys.dotpict.R;
import be.kevindenys.dotpict.fragment.MainGridFragment;
import be.kevindenys.dotpict.fragment.OptionFragment;
import be.kevindenys.dotpict.util.SaveDatabaseHelper;

public class MainActivity extends AppCompatActivity {
    private App app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.app = (App) getApplication();

        getSupportLoaderManager().initLoader(0, null, loaderCallback); //SQLTAG

        MainGridFragment fragment1 = new MainGridFragment();
        fragment1.setApp(app); //app doorgeven aan fragment1
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.flContainer, fragment1);
        ft.commit();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            OptionFragment secondFragment = new OptionFragment();
            secondFragment.setApp(app); //app doorgeven aan fragment2
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
            ft2.add(R.id.flContainer2, secondFragment);
            ft2.commit();
        }


    }

    //Word gebruikt enkel in de TESTS..
    public App getApp() {
        return app;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app = null; //Fixed a canary leak
    }

    @Override
    protected void onPause() {
        super.onPause();


    }


    private SaveDatabaseHelper datahelper;
    LoaderManager.LoaderCallbacks<Cursor> loaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {   //SQLTAG
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            datahelper = new SaveDatabaseHelper(getApplicationContext());
            app.setDatahelper(datahelper);
            SQLiteDatabase db = datahelper.getWritableDatabase();
            app.setSaves(datahelper.readAllDataFromDatabase(db));
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
}
