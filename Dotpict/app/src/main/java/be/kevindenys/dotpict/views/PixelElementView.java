package be.kevindenys.dotpict.views;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import be.kevindenys.dotpict.R;
import be.kevindenys.dotpict.activity.App;
import be.kevindenys.dotpict.models.Grid;
import be.kevindenys.dotpict.models.Pixel;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by Kevin on 2/12/2017.
 */

public class PixelElementView extends FrameLayout {
    private Pixel pixel;
    private TextView pixelText;
    private App app;

    public PixelElementView(Context context, Pixel pixel, App app) {
        super(context);
        this.pixel = pixel;
        this.app = app;
        initPixelView();
    }
//must haves
    public PixelElementView(@NonNull Context context, @Nullable AttributeSet attrs, Pixel pixel) {
        super(context, attrs);
        this.pixel = pixel;
        initPixelView();
    }

    public PixelElementView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, Pixel pixel) {
        super(context, attrs, defStyleAttr);
        this.pixel = pixel;
        initPixelView();
    }

    public PixelElementView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes, Pixel pixel) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.pixel = pixel;
        initPixelView();
    }

    private void initPixelView(){
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.element, this, true);
        pixelText = (TextView)findViewById(R.id.pixel);
        //Touching
        pixelText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int state = app.getDrawingState();

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && state == 4) {
                    //Fill mode
                    //Checken of kleur de zelfde is, anders is er een oneindige loop, door de recursie
                    if (pixel.getColor() != app.getPickedColor()) {
                        int ij[] = getIndexInBoard();
                        fillArea(ij[0], ij[1], pixel.getColor(), app.getPickedColor());
                    }
                } else if (state == 1 || state == 2) {
                    //Swipe
                    //Eigenlijk gaan we elke pixel controleren terwijl we swipen, of onze vinger op die pixel staat
                    //Hiervoor hebben we 2 loops nodig voor rij en kolom
                    //We gebruiken dezelfde code voor drawing mode en voor eraser mode
                    for (int r = 0; r < app.getMyBoard().getMyPixelElements().length; r++) {
                        for (int c = 0; c < app.getMyBoard().getMyPixelElements().length; c++) {
                            if (app.getDrawingState() == 1) {
                                //gekozen kleur
                                app.getMyBoard().getMyPixelElements()[r][c].swiping((int) motionEvent.getRawX(), (int) motionEvent.getRawY(), app.getPickedColor());
                            } else if(app.getDrawingState() == 2) {
                                //leegmaken
                                app.getMyBoard().getMyPixelElements()[r][c].swiping((int) motionEvent.getRawX(), (int) motionEvent.getRawY(), Color.WHITE);
                            }

                        }
                    }
                 }else if(app.getDrawingState() == 3){
                    //circle
                int ij[] = getIndexInBoard();
                app.getMyBoard().drawCircle2(ij[0],ij[1], app.getCircle(),app.getPickedColor());
            }

                return true;
            }
        });

    }

    //Methods
    //Finds the I and J from the array inside the board
    private int[] getIndexInBoard(){
        int ij[] = new int[2];

        for(int r = 0; r < app.getMyBoard().getMyPixelElements().length; r++){
            for(int c = 0; c < app.getMyBoard().getMyPixelElements().length; c++){
                if(app.getMyBoard().getMyPixelElements()[r][c] == this){
                    ij[0] = r;
                    ij[1] = c;
                }
            }}
        return ij;
    }
    public TextView getPixelTextView() {
        return pixelText;
    }
    //Fill method
    //We gaan hier recursive te werk, we starten bij punt Row|Column
    //Dan gaan we naar boven onder links en rechts en als de kleur het zelfde is als de start kleur, dan wil het zeggen dat we nog steeds moeten opvullen
    //Hier kleuren we de pixel dan in de gekozen kleur nieuwe kleur
    public void fillArea(int row, int column, int startColor, int newColor){

        Grid grid = app.getMyBoard().getPixelGrid();
        Pixel currentElement = grid.getElement(row, column);

        // Fill the current element
        currentElement.setColor(newColor);

        //Because we want a border around the color we have to use a drawable
        Drawable mDrawable = getContext().getResources().getDrawable(R.drawable.pixelborder);
        mDrawable.setColorFilter(currentElement.getColor(), PorterDuff.Mode.MULTIPLY);
        //Set new 'color' with border
        app.getMyBoard().getMyPixelElements()[row][column].getPixelTextView().setBackground(mDrawable);
//Gebasseerd op het floodfill algoritme: https://nl.wikipedia.org/wiki/Floodfill-algoritme
        if(row > 0) {
            Pixel boven = grid.getElement(row - 1, column);
            if(boven.getColor() == startColor){
                //Recursief terug starten
                fillArea(row -1, column, startColor, newColor);
            }
        }
        if(row < grid.getSize() - 1){
            Pixel onder = grid.getElement(row + 1, column);
            if(onder.getColor() == startColor){
                fillArea(row + 1, column, startColor, newColor);
            }
        }
        if(column > 0){
            Pixel links = grid.getElement(row, column - 1);
            if(links.getColor() == startColor){
                fillArea(row, column - 1, startColor, newColor);
            }
        }
        if(column < grid.getSize() - 1){
            Pixel rechts = grid.getElement(row , column + 1);
            if(rechts.getColor() == startColor){
                fillArea(row , column + 1, startColor, newColor);
            }
        }

    }
    //Special thanks to Jonathan for finding the base function => Tweaked by Kevin Denys
    //x & y zijn vinger locatie
    private void swiping(int x, int y, int pickedColor) {
        // Array aanmaken voor de locatie
        int[] location = new int[2];
        super.getLocationOnScreen(location); //locatie van het pixelelementview
        if(inLocation(location,x,y)){ //kijken of onze vinger in de locatie is
            if(pickedColor != pixel.getColor()){ // als het niet dezelfde kleur heeft
                setElementColor(pickedColor); //kleuren we die pixel
            }

        }
    }

    //test if x and y are in that rectangle
    //ja gewoon wiskunde..
    private boolean inLocation(int[] location, int x, int y){
        Rect rect = new Rect(location[0], location[1], location[0]+getWidth(),location[1]+getHeight());
        return rect.contains(x,y);
    }

    public void setElementColor(int color){
        pixel.setColor(color);
        //Convert color to drawable(with border)
        Drawable mDrawable = getContext().getResources().getDrawable(R.drawable.pixelborder);
        mDrawable.setColorFilter(pixel.getColor(), PorterDuff.Mode.MULTIPLY);
        //Set new 'color' with border
        pixelText.setBackground(mDrawable);
    }

    public Pixel getPixel() {
        return pixel;
    }
}