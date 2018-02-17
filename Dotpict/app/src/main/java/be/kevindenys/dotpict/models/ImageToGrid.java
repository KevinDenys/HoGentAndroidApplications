package be.kevindenys.dotpict.models;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by Kevin on 3/12/2017.
 */

public class ImageToGrid {

    private Bitmap myImage;
    private int[][] myGrid;
    private int size;
    public ImageToGrid(Bitmap myImage, int size) {
        this.myImage = myImage;
        this.size = size;
        myGrid = new int[size][size];
    }
    //Functie die de image gaat verkleinen, naar de size van eht GRID
    //Draaien 270 omdat de foto anders op zijn zijkant staat
    public Bitmap resizeImage(){
        Matrix matrix = new Matrix();
        matrix.postRotate(270);
        Bitmap myScaled = Bitmap.createScaledBitmap(myImage, size, size, true);
        return Bitmap.createBitmap(myScaled, 0, 0, myScaled.getWidth(), myScaled.getHeight(), matrix, true);
    }
    //De afbeeldingen aflopen, en alle pixels in de INT(color) array steken
    //En de array terug geven
    public int[][] convertPixelToImage(){
        Bitmap x = resizeImage();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                myGrid[i][j] = x.getPixel(i,j);
            }
        }
        return myGrid;
    }
}
