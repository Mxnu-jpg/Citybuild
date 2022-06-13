package at.htblakaindorf.AHIF18.Ground.Buildingobjects;

import at.htblakaindorf.AHIF18.Ground.Tile;

/**
 * <b>CityBuild</b><br>
 * Specified {@link Building} class for {@link Flowers}
 *
 * @author Marcel Schmidl
 * @version 1.0
 * @since 16.05.2022
 */
public class Flowers extends Building {
    public Flowers(String name, boolean collision, int id, boolean building, int[] costs, int[] earnings, int col, int row) {
        super(name, collision, id, building, costs, earnings, col, row);
    }
}
