package at.htblakaindorf.AHIF18.Ground.Buildingobjects;

import at.htblakaindorf.AHIF18.Ground.Tile;

/**
 * Specified {@link Building} class for {@link Grass}
 *
 * @author Marcel Schmidl
 * @version 1.0 - 16.05.2022
 */
public class Grass extends Building {

    public Grass(String name, boolean collision, int id, boolean building, int[] costs, int[] earnings, int col, int row) {
        super(name, collision, id, building, costs, earnings, col, row);
    }
}
