package at.htblakaindorf.AHIF18;

/**
 * <b>CityBuild</b><br>
 * Dataclass for each {@link at.htblakaindorf.AHIF18.Ground.Tile} based on its name and
 * imagepath
 *
 * @author Manuel Reinprecht
 * @author Marcel Schmidl
 * @version 1.1
 * @since 18.04.2022
 */

public class Buildingname {
    private String name;
    private String path;
    private UI ui;
    private int id;

    public Buildingname(int id, String path, String name, UI ui) {
        this.name = name;
        this.path = path;
        this.ui = ui;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
