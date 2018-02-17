package be.kevindenys.wieishet.activity;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.haha.perflib.Main;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;

import be.kevindenys.wieishet.R;
import be.kevindenys.wieishet.fragments.CharacterDetailFragment;
import be.kevindenys.wieishet.models.Character;
import be.kevindenys.wieishet.util.CharacterDbHelper;

/**
 * Created by Kevin on 3/12/2017.
 */

public class App extends Application {
    private CharacterDetailFragment cdf;
    //hmm
    private CharacterDbHelper datahelper;
    private ArrayList<Character> characters;
    private ArrayList<Character> newGameChars;

    private boolean gameState = false;
    private int gameDone = 0; //0 = no 1= lose 2=win
    private Character choosenChar = null;
    private Context dialogContext;

    private RefWatcher refWatcher;

//    //slides page 39 memory
//    public static RefWatcher getWatcher(Context context){
//        App app = (App) context.getApplicationContext();
//        return app.refWatcher;
//    }

public boolean getGameInProgress(){
        return gameState;
}
    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        refWatcher = LeakCanary.install(this);
    }

    public Context getDialogContext() {
        return dialogContext;
    }

    public void setDialogContext(Context dialogContext) {
        this.dialogContext = dialogContext;
    }

    public boolean isCharChoosen() {
        return gameState;
    }

    public int getGameDone() {
        return gameDone;
    }

    //App herstarten, wordt gebruikt na dat er database updates gebeurd zijn
    public void restartAppProgram(){

        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        System.exit(0);
    }

    public void setGameDone(int gameDone) {
        this.gameDone = gameDone;
    }

    public void setChoosenChar(Character chosen) {
        if(gameState == false){
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                cdf.makeInvis();
            }
            choosenChar = chosen;
            gameState = true;
            Toast.makeText(this, "Player 2 has to find: " + chosen.getName() , Toast.LENGTH_SHORT).show();
        }
    }

    public Character getChoosenChar() {
        return choosenChar;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    //Game settings reseten naar default settings
    public void restartGame(){
        this.gameState = false;
        this.choosenChar = null;
        this.gameDone = 0;
        this.characters.clear();
        this.characters.addAll(newGameChars);
        MainActivity x = (MainActivity) dialogContext;
        x.fillInFragments();
    }

    public ArrayList<Character> getNewGameChars() {
        return newGameChars;
    }

    public void setNewGameChars(ArrayList<Character> newGameChars) {
        this.newGameChars = newGameChars;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    //Game won
    public void won(Context c){
        gameDone = 2;
        showMessage(getString(R.string.winTitel), getString(R.string.wonmessage),null, c,"Okay");
    }
    //Game lost
    public void lost(Context c){
        gameDone = 1;
        showMessage(getString(R.string.lostTitel), getString(R.string.lostMessage,choosenChar.getName()),null, c, "Okay");
    }
    //Message dialog die we meerdere keren gebruiken
    public void showMessage(final String winlose, final String message, final Character character, Context context, String button) {

        //Create new AlertDialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        //Thanks to https://stackoverflow.com/questions/7803771/call-to-getlayoutinflater-in-places-not-in-activity
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View mView = inflater.inflate(R.layout.dialogscreen, null);
        //Declare text and button (will be used later)
        final TextView txttitelwin = (TextView) mView.findViewById(R.id.txtWF);
        final TextView txtmessage = (TextView) mView.findViewById(R.id.txtMessage);
        final Button ok = (Button) mView.findViewById(R.id.okbuton);
        ok.setText(button);
        //We give the view to the alertdialog created above and create it
        txtmessage.setText(message);
        txttitelwin.setText(winlose);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        //Show the dialog
        dialog.show();
        //Onclick listeners to set the grid
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(character == null){
                restartGame();
            }else{
                setChoosenChar(character);
            }
            dialog.cancel();
            }
        });
    }

    public CharacterDbHelper getDatahelper() {
        return datahelper;
    }

    public void setDatahelper(CharacterDbHelper datahelper) {
        this.datahelper = datahelper;
    }

    public CharacterDetailFragment getCdf() {
        return cdf;
    }

    public void setCdf(CharacterDetailFragment cdf) {
        this.cdf = cdf;
    }
}
