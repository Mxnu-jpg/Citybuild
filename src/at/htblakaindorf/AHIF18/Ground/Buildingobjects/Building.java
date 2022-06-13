package at.htblakaindorf.AHIF18.Ground.Buildingobjects;

import at.htblakaindorf.AHIF18.Ground.Tile;
/**
 * Superclass for all the possible objects to build on the map based on
 * {@link Tile}
 *
 * @author Marcel Schmidl
 * @version 1.1
 * */
public class Building extends Tile {
    int col, row;

    public Building(String name, boolean collision, int id, boolean building, int[] costs, int[] earnings, int col, int row) {
        super(name, id, costs, earnings, collision, building);
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
