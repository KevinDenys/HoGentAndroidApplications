package be.kevindenys.dotpict.models;

/**
 * Created by Kevin on 2/12/2017.
 */

public class Grid {
    private int size;
    private Pixel[][] pixelGrid;

    public Grid(int size) {
        this.size = size;
        pixelGrid = new Pixel[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                pixelGrid[i][j] = new Pixel();
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Pixel[][] getPixelGrid() {
        return pixelGrid;
    }

    public Pixel getElement(int i, int j) {
        return pixelGrid[i][j];
    }

}
