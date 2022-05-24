package at.htblakaindorf.AHIF18;

public class Buildingname {
    String name;
    String path;
    UI ui;
    int id;

    public Buildingname(int id,String path, String name, UI ui) {
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
