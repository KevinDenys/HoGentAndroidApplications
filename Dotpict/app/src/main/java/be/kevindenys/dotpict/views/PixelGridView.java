package be.kevindenys.dotpict.views;


import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Parcelable;

import android.util.AttributeSet;
import android.util.DisplayMetrics;

import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;

import be.kevindenys.dotpict.activity.App;
import be.kevindenys.dotpict.models.Grid;

/**
 * Created by Kevin on 2/12/2017.
 */

public class PixelGridView extends GridLayout{
    private Grid pixelGrid;
    private PixelElementView[][] myPixelElements;
    private int size;
    private App app;

    public void setPixelGridView(Grid tempHolder, int size, App app) {
        removeAllViews();
        this.app = app;
        this.size = size;
        this.pixelGrid = tempHolder;
        myPixelElements = new PixelElementView[size][size];
        int width = 0;
        this.setRowCount(size);
        this.setColumnCount(size);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            width = calculateCardHeight();

        }else{
            width = calculateCardWidth();

        }

        for(int i = 0; i< size; i++){
            for(int j= 0; j <size; j++){
                PixelElementView v = new PixelElementView(getContext(),tempHolder.getElement(i,j),app);
                myPixelElements[i][j] = v;
                if(tempHolder.getPixelGrid()[i][j].getColor() != 0){
                    v.setElementColor(tempHolder.getPixelGrid()[i][j].getColor());
                }

                addView(v, width,width);
            }
        }
    }

    public PixelGridView(Context context, int size, App app) {
        super(context);
        pixelGrid = new Grid(size);
        setPixelGridView(pixelGrid, size, app);
    }

    public PixelGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PixelGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PixelGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //Thanks to Eothein 2048
    private int calculateCardWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(displayMetrics);
        int actbarh=getActionBarHeight();
        //This will remove all decimals
        return Math.min(displayMetrics.widthPixels,displayMetrics.heightPixels - actbarh) / size;
    }

    //Zelfde functie als hierboven maar dan aangepast zodat wanneer er in landscape gewerkt word, we het vierkant mooi in de layout screen krijgen
    private int calculateCardHeight(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(displayMetrics);
        int actbarh=getActionBarHeight();
        //This will remove all decimals
        return Math.min(displayMetrics.heightPixels,displayMetrics.heightPixels - 240) / size;
    }
//2048 code
    public int getActionBarHeight() {
        final TypedArray ta = getContext().getTheme().obtainStyledAttributes(
                new int[] {android.R.attr.actionBarSize});
        int actionBarHeight = (int) ta.getDimension(0, 0);

        return actionBarHeight;
    }

    public Grid getPixelGrid() {
        return pixelGrid;
    }

    public void setPixelGrid(Grid pixelGrid) {
        this.pixelGrid = pixelGrid;
    }

    public PixelElementView[][] getMyPixelElements() {
        return myPixelElements;
    }

    public void setMyPixelElements(PixelElementView[][] myPixelElements) {
        this.myPixelElements = myPixelElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    //Word aangeroepen bij draaien, switchen van fragments.
    //Zo verliezen we onze grid niet.
    @Override
    protected Parcelable onSaveInstanceState() {
        app.setTempHolder(pixelGrid);
        return super.onSaveInstanceState();

    }
    public void setPixel(int x, int y, int color){
        //Outofboundstesting, anders krijgen we null pointers :/
        if((x >= 0 && x < myPixelElements[0].length) && (y >= 0 && y < myPixelElements[0].length)){
            myPixelElements[x][y].setElementColor(color);
        }
    }
    //Drawing circle using the https://en.wikipedia.org/wiki/Midpoint_circle_algorithm
    //Meer uitleg, https://www.youtube.com/watch?v=2giLRr1vNqQ
    public void drawCircle2(int x0, int y0, int radius, int color) {
        int x = radius - 1;
        int y = 0;
        int dx = 1;
        int dy = 1;
        int err = dx - (radius << 1);

        while (x >= y) {
            setPixel(x0 + x, y0 + y, color);
            setPixel(x0 + y, y0 + x, color);
            setPixel(x0 - y, y0 + x, color);
            setPixel(x0 - x, y0 + y, color);
            setPixel(x0 - x, y0 - y, color);
            setPixel(x0 - y, y0 - x, color);
            setPixel(x0 + y, y0 - x, color);
            setPixel(x0 + x, y0 - y, color);

            if (err <= 0) {
                y++;
                err += dy;
                dy += 2;
            }
            if (err > 0) {
                x--;
                dx += 2;
                err += (-radius << 1) + dx;
            }
        }

    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);

    }
}
 //   The signed left shift operator "<<" shifts a bit pattern to the left
// The signed right shift operator ">>" shifts a bit pattern to the right.
//The unsigned right shift operator ">>>" shifts a zero into the leftmost position