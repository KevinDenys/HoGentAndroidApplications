package be.kevindenys.dotpict.activity;

import android.app.Application;
import android.graphics.Color;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;

import be.kevindenys.dotpict.R;
import be.kevindenys.dotpict.models.Grid;
import be.kevindenys.dotpict.models.MyObjectBox;
import be.kevindenys.dotpict.models.Save;
import be.kevindenys.dotpict.util.SaveDatabaseHelper;
import be.kevindenys.dotpict.views.PixelGridView;
import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by Kevin on 2/12/2017.
 */

public class App extends Application {
    private BoxStore boxStore;
    private Box<Save> saveBox;
    private int pickedColor = Color.RED;
    private int drawingState = 1; //1 draw, 2 erase, 3 circle, 4 fill
    private static final int CAMERA_REQUEST = 1888;
    private PixelGridView myBoard;
    private Grid tempHolder;
    private int circle;
    private RefWatcher refWatcher;


    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        refWatcher = LeakCanary.install(this);

        //Objectbox
        //Box store aanmaken
        //boxStore = MyObjectBox.builder().androidContext(App.this).build();
        //Savebox aanmaken, waar we save's gaan opslaan
        //saveBox = boxStore.boxFor(Save.class);
    }

    public int getPickedColor() {
        return pickedColor;
    }

    public void setPickedColor(int pickedColor) {
        this.pickedColor = pickedColor;
    }

    public int getDrawingState() {
        return drawingState;
    }

    public void setDrawingState(int drawingState) {
        this.drawingState = drawingState;
    }

    public PixelGridView getMyBoard() {
        return myBoard;
    }

    public void setMyBoard(PixelGridView myBoard) {
        this.myBoard = myBoard;
    }

    //Used for transfering at startup
    //Zorgt er ook voor dat de applicatie de grid in het geheugen houd
    //Voor het laden van een oude grid gebruiken we deze tempholder ook
    //Zie PixelGridView lijn 130
    public Grid getTempHolder() {
        return tempHolder;
    }

    public void setTempHolder(Grid tempHolder) {
        this.tempHolder = tempHolder;
    }

    //function that generates a Save and puts it in the savebox
    //We use SAVE model because we could not save a double array in objectbox, so we are using json model now
    public void saveGrid(String snaam){
       Save x = new Save(snaam,myBoard.getPixelGrid());
        //objectbox
       // saveBox.put(x);
        saves.add(x); //SQLTAG
        datahelper.putSaveInDb(x, datahelper.getWritableDatabase());
    }
    //Get grid using his index(recyclerview) to retrieve it from the box
    public Grid loadGrid(int index){
       return saveBox.getAll().get(index).getGrid();
    }
    //Set the circle size
    public void setCircle(int circle) {
        this.circle = circle;
    }
    //Get the circle size
    public int getCircle() {
        return circle;
    }
    //Get the saveBox
    public Box<Save> getSaveBox() {
        return saveBox;
    }

    //SQLLITE
    private ArrayList<Save> saves = new ArrayList<>();
    private SaveDatabaseHelper datahelper;
    public void setDatahelper(SaveDatabaseHelper datahelper) {
        this.datahelper = datahelper;
    }

    public ArrayList<Save> getSaves() {
        return saves;
    }

    public void setSaves(ArrayList<Save> saves) {
        this.saves = saves;
    }

    public SaveDatabaseHelper getDatahelper() {
        return datahelper;
    }

//    public static RefWatcher getWatcher(Context context){
//        App app = (App) context.getApplicationContext();
//        return app.refWatcher;
//    }
}
