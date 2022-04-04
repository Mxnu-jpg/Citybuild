package at.htblakaindorf.AHIF18.db;

import at.htblakaindorf.AHIF18.Buildingname;
import at.htblakaindorf.AHIF18.GamePanel;
import at.htblakaindorf.AHIF18.Ground.Tile;
import at.htblakaindorf.AHIF18.UI;
import at.htblakaindorf.AHIF18.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityBuildDataBase {
    private static CityBuildDataBase instance;

    private ArrayList<Tile> tiles;
    private ArrayList<Buildingname> buildings;

    private CityBuildDataBase() {
        tiles = new ArrayList<>();
        buildings = new ArrayList<>();

        try {
            setTiles(0, "/tiles/ground/Grass.png", "Grass", false);
            setTiles(1, "/tiles/ground/Tree.png", "Tree", true);
            setTiles(10, "/building/House1.png", "Villager Building", true);
            setTiles(11, "/res/building/Blacksmith.png", "Blacksmith", true);
            setTiles(12, "/res/building/Church.png", "Church", true);
            setTiles(13, "/res/building/Fisher.png", "Fisher", true);
            setTiles(14, "/res/building/Windmill.png", "Windmill", true);
            setTiles(15, "/res/building/Coal Mine.png", "Coal Mine", true);
            setTiles(16, "/res/building/Iron Mine.png", "Iron Mine", true);
            setTiles(17, "/res/building/WheatFarm.png", "Wheat Farm", true);
            setTiles(30, "/res/tiles/ground/Farmer Wheatfield.png", "Wheatfield", true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static CityBuildDataBase getInstance() {
        if (instance == null) {
            instance = new CityBuildDataBase();
        }
        return instance;

    }

    public void setTiles(int id, String path, String name, boolean collision) throws IOException {
        Tile tile = new Tile();
        tile.setCollision(collision);
        tile.setPath(path);
        tile.setImage(ImageIO.read(getClass().getResourceAsStream(path)));
        tile.setId(id);
        tile.setName(name);

        tiles.add(tile);
    }

    public void setBuildings(UI ui) {
        for (Tile tile : tiles) {
            if (tile.getId() >= 10 && tile.getId() < 30) {
                buildings.add(new Buildingname(tile.getId(), tile.getPath(), tile.getName(), ui));
            }
        }
    }

    public Tile getTileById(int id) {
        return tiles.get(id);
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTileSize(GamePanel gp) {
        UtilityTool uTool = new UtilityTool();

        for (Tile tile : tiles) {
            tile.image = uTool.scaleImage(tile.image, gp.getTileSize(), gp.getTileSize());
        }
    }

    public ArrayList<Buildingname> getBuildings() {
        return buildings;
    }

}
