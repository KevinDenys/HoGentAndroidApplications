package be.kevindenys.dotpict.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.squareup.leakcanary.RefWatcher;

import be.kevindenys.dotpict.R;
import be.kevindenys.dotpict.activity.App;
import be.kevindenys.dotpict.activity.GridActivity;
import be.kevindenys.dotpict.models.Grid;
import be.kevindenys.dotpict.models.ImageToGrid;
import be.kevindenys.dotpict.views.PixelGridView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Kevin on 2/12/2017.
 */

public class OptionFragment extends Fragment {
    private static final int CAMERA_REQUEST = 1888;
    /** ButterKnife Code **/
    @BindView(R.id.gridOptions)
    Button gridOptions;
    @BindView(R.id.choosecolor)
    Button choosecolor;
    @BindView(R.id.rgTools)
    RadioGroup rgTools;
    @BindView(R.id.state1)
    RadioButton state1;
    @BindView(R.id.state2)
    RadioButton state2;
    @BindView(R.id.state3)
    RadioButton state3;
    @BindView(R.id.state4)
    RadioButton state4;
    @BindView(R.id.takeImag)
    Button takeImag;
    /** ButterKnife Code **/
    private App app;
    private Unbinder unbinder;
    public void setApp(App app) {
        this.app = app;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tools, parent, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(final View rootView, Bundle savedInstanceState) {
        app = (App) getActivity().getApplication();
        //Setting the right option back
           if(app != null){
            switch (app.getDrawingState()){
                case 1: state1.setChecked(true); break;
                case 2: state2.setChecked(true); break;
                case 3: state3.setChecked(true); break;
                case 4: state4.setChecked(true); break;
                default:state1.setChecked(true); break;
            }
        }

        rgTools.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == state1.getId()){
                    app.setDrawingState(1);
                }
                if(i == state2.getId()){
                    app.setDrawingState(2);
                }
                if(i == state3.getId()){
                    chooseCircleSizeDialog(2, app.getMyBoard().getSize()-1); //anders is de circel nooit zichtbaar
                    app.setDrawingState(3);
                }
                if(i == state4.getId()){
                    app.setDrawingState(4);
                }
            }
        });
    }

    //We gebruiken deze library: https://github.com/QuadFlask/colorpicker
    @OnClick(R.id.choosecolor)
    public void showColorPicker(){
        ColorPickerDialogBuilder
                .with(getContext())
                .setTitle(getString(R.string.btnChooseColor))
                .initialColor(app.getPickedColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {

                    }
                })
                .setPositiveButton(getString(R.string.Ok), new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        app.setPickedColor(selectedColor);
                    }
                })
                .setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    @OnClick(R.id.gridOptions)
    public void showGridOptions(){
        Intent i = new Intent(getContext(), GridActivity.class);
        getActivity().startActivity(i);
    }
    //Eerst checken of we permissies hebben, zo niet worden deze gevraagd: normaal vanaf Android 6 zouden deze popups moeten verschijnen
    @OnClick(R.id.takeImag)
    public void startCamera(){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST);

        }else{
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }
    //Wanneer de activiteit terug komt van de camera applicatie
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            //Smart me got thinking
            //Waarom het moeilijker maken dan het is, we gaan de foto gewoon verkleinen naar de size van ons grid, en dan pixel per pixel de kleur opvragen en deze in het grid
            //Steken, zo moeten we geen moeilijke algoritmes gebruiken om de kleur te bepalen..
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageToGrid itg = new ImageToGrid(photo, app.getMyBoard().getPixelGrid().getSize());
            int myImage[][] = itg.convertPixelToImage();
            for (int i = 0; i < myImage[0].length; i++) {
                for (int j = 0; j < myImage[0].length; j++) {
                    app.getMyBoard().setPixel(i,j, myImage[i][j]);
                }
            }
        }
        Toast.makeText(app, getString(R.string.done), Toast.LENGTH_SHORT).show();
    }

    //popup dialog waarin cirkel groote gevraagd word, aan de hand van een minimum en maximum
    public void chooseCircleSizeDialog(final int min, final int max){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.input_dialog, null);
        final EditText choosenNumber = (EditText) mView.findViewById(R.id.userInputDialog);
        final Button ok = (Button) mView.findViewById(R.id.okbuton);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!choosenNumber.getText().toString().isEmpty()) {
                    int returnInt = Integer.parseInt(choosenNumber.getText().toString());
                    if (returnInt < min || returnInt > max) {
                        choosenNumber.setError(getString(R.string.mustbe) + min + getString(R.string.and) + max);
                    } else {
                        app.setCircle(returnInt);
                        dialog.dismiss();
                    }
                }
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        app = null;
//        RefWatcher refWatcher = App.getWatcher(getActivity());
//        refWatcher.watch(this);
    }
}
