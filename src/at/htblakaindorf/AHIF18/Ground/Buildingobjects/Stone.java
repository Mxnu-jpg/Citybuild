package at.htblakaindorf.AHIF18.Ground.Buildingobjects;

/**
 * <b>CityBuild</b><br>
 * Specified {@link Building} class for {@link Stone}
 *
 * @author Marcel Schmidl
 * @version 1.0
 * @since 16.05.2022
 */
public class Stone extends Building {

    public Stone(String name, boolean collision, int id, boolean building, int[] costs, int[] earnings, int col, int row) {
        super(name, collision, id, building, costs, earnings, col, row);
    }
}
