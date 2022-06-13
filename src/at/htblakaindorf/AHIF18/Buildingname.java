package at.htblakaindorf.AHIF18;

/**
 * Dataclass for each {@link at.htblakaindorf.AHIF18.Ground.Buildingobjects.Building} based on its name and
 * imagepath
 *
 * @author Manuel Reinprecht
 * @author Marcel Schmidl
 * @version 1.1 - 18.04.2022
 */

public class Buildingname {
    String name;
    String path;
    UI ui;
    int id;

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
