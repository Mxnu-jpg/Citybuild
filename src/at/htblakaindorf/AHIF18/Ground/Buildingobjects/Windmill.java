package at.htblakaindorf.AHIF18.Ground.Buildingobjects;

/**
 * <b>CityBuild</b><br>
 * Specified {@link Building} class for the {@link Windmill}
 *
 * @author Marcel Schmidl
 * @version 1.0
 * @since 16.05.2022
 */
public class Windmill extends Building {

    public Windmill(String name, boolean collision, int id, boolean building, int[] costs, int[] earnings, int col, int row) {
        super(name, collision, id, building, costs, earnings, col, row);
    }
}