package be.kevindenys.dotpict.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import be.kevindenys.dotpict.R;
import be.kevindenys.dotpict.adapters.SaveViewAdapter;
import be.kevindenys.dotpict.models.Grid;
import be.kevindenys.dotpict.models.Save;
import be.kevindenys.dotpict.util.SaveDatabaseHelper;
import be.kevindenys.dotpict.views.PixelGridView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kevin on 3/12/2017.
 */

public class GridActivity extends AppCompatActivity implements SaveViewAdapter.ItemClickListener {
    /** ButterKnife Code **/
    @BindView(R.id.saveGridToImage)
    Button saveGridToImage;
    @BindView(R.id.newGrid)
    Button newGrid;
    @BindView(R.id.saveGrid)
    Button saveGrid;
    @BindView(R.id.recSave)
    android.support.v7.widget.RecyclerView recyclerView;
    /** ButterKnife Code **/
    private App app;
    private SaveViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridtools);
        ButterKnife.bind(this);
        this.app = (App) getApplication();

        //Recyler

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Adapter

        //adapter = new SaveViewAdapter(this, app.getSaveBox().getAll(), app); //objectbox
        adapter = new SaveViewAdapter(this, app.getSaves(), app); //SQLTAG
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        //Buttons
        newGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseNewGridDialog(1, 25);
            }
        });
        saveGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseSaveDialog();
            }
        });
        saveGridToImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGridAsImage(app.getMyBoard());
            }
        });
    }


    //Wanneer er op een item uit de recylerview geklikt word, vragen we of ze zeker zijn dat ze dit willen laden
    @Override
    public void onItemClick(View view, final int position) {

        final int selected = position;

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(R.string.sureloading);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                R.string.Yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Grid loaded = app.loadGrid(selected); //objectbox
                        Grid loaded = app.getSaves().get(position).getGrid(); //SQLTAG
                        app.setMyBoard(new PixelGridView(getApplicationContext(), loaded.getSize(), app));
                        app.setTempHolder(loaded);
                        Toast.makeText(app, R.string.gridloadedmessage, Toast.LENGTH_SHORT).show();
                        endThisActivity();
                        dialog.dismiss();
                    }
                });

        builder1.setNegativeButton(
                R.string.No,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    private void endThisActivity() {
        finish();
      // Intent i = new Intent(getApplicationContext(), MainActivity.class);
      // getApplicationContext().startActivity(i);
    }

    public void chooseNewGridDialog(final int min, final int max) {

        //Used this tutorial https://www.youtube.com/watch?v=plnLs6aST1M

        //Create new AlertDialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(GridActivity.this);
        //Load the layout we created
        View mView = getLayoutInflater().inflate(R.layout.input_dialog, null);
        //Declare text and button (will be used later)
        final EditText choosenNumber = (EditText) mView.findViewById(R.id.userInputDialog);
        final Button ok = (Button) mView.findViewById(R.id.okbuton);
        //We give the view to the alertdialog created above and create it
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        //Show the dialog
        dialog.show();
        //Onclick listeners to set the grid
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
if(!choosenNumber.getText().toString().isEmpty()) {
    int returnInt = Integer.parseInt(choosenNumber.getText().toString());
    if (returnInt < min || returnInt > max) {
        choosenNumber.setError(getString(R.string.betweenmust)+ " " + min + " " + getString(R.string.andtitel)+ " " + max);
    } else {
        //Nieuw pixelgridview in het board zetten
        app.setMyBoard(new PixelGridView(getApplicationContext(), returnInt, app));
        //Tempholder weer opvullen
        app.setTempHolder(app.getMyBoard().getPixelGrid());
        dialog.dismiss();
        Toast.makeText(app, R.string.newgridmessage, Toast.LENGTH_SHORT).show();
        endThisActivity();
    }
}
            }
        });
    }

    public void chooseSaveDialog() {
        //Used this tutorial https://www.youtube.com/watch?v=plnLs6aST1M
        //Create new AlertDialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(GridActivity.this);
        //Load the layout we created
        View mView = getLayoutInflater().inflate(R.layout.input_save_dialog, null);
        //Declare text and button (will be used later)
        final EditText ChoosenName = (EditText) mView.findViewById(R.id.userInputDialog);
        final Button ok = (Button) mView.findViewById(R.id.okbuton);
        //We give the view to the alertdialog created above and create it
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        //Show the dialog
        dialog.show();
        //Onclick listeners to set the grid
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //We moeten een naam invullen
                if (ChoosenName.getText().toString().isEmpty()) {
                    ChoosenName.setError(getString(R.string.fillName));
                } else {
                    app.saveGrid(ChoosenName.getText().toString());
                    //adapter.updateList(app.getSaveBox().getAll()); //Objectbox
                    //Update forceren
                    adapter.notifyDataSetChanged();
                    //Bypassing the recyclerview not updating on first insert
                    if (adapter.getItemCount() == 0) {
                        finish();
                    } else {
                        dialog.cancel();
                    }

                }
            }
        });
    }

    //Grid opslaan als PNG op het toestel zelf
    public void saveGridAsImage(View view) {
        String state;
        state = Environment.getExternalStorageState();
        //Kijken of de media gekoppeld is (SD kaart)
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            //Root folder van afbeeldingen krijgen (Android: pictures)
            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            //Folder dotpict toewijzen
            File appfolder = new File(root.getAbsolutePath() + "/dotpict");
            //Nieuwe File toewijzen met als titel tijd nu in miliseconden
            File file = new File(appfolder, Long.toString(System.currentTimeMillis()) + ".png");
            try {
                //Maak alle mappen die nodig zijn om de afbeelding op te slaan
                appfolder.mkdirs();
                if (!file.exists()) {
                    //Als het bestand nog niet bestaat (in miliseconden is er geen kans dat het al bestaat creeren we het bestand
                    file.createNewFile();
                }
                //Fileoutputstream om het grid naar file op te slaan
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                //Maak een nieuwe bitmap met de breedte en hoogte van de weergave die we willen exporteren
                Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                //Maak een nieuw canvas met de bitmap
                Canvas canvas = new Canvas(bitmap);
                // Teken de gekozen weergave naar het canvas
                // De bitmap bevat nu het canvas met de geëxporteerde weergave
                view.draw(canvas);

                //Schrijf de bitmap naar de hierboven aangemaakte bestandsstroom
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Toast.makeText(app, getString(R.string.filesavedmessage) + file.getName(), Toast.LENGTH_SHORT).show();
                //De media scanner forceren om nieuwe afbeeldingen te zoeken en aan de gallerij toe te voegen: (emulator word nog altijd niet geupdate, manueel zoeken onder storage)
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + appfolder)));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(app, R.string.somethingwrong, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(app, R.string.somethingwrong, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
