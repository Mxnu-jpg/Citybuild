package at.htblakaindorf.AHIF18.Ground.Buildingobjects;

/**
 * Specified {@link Building} class for {@link Water}
 *
 * @author Marcel Schmidl
 * @version 1.0 - 16.05.2022
 */
public class Water extends Building {

    public Water(String name, boolean collision, int id, boolean building, int[] costs, int[] earnings, int col, int row) {
        super(name, collision, id, building, costs, earnings, col, row);
    }
}
