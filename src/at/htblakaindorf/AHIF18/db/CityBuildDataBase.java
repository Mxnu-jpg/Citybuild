package at.htblakaindorf.AHIF18.db;

import at.htblakaindorf.AHIF18.Buildingname;
import at.htblakaindorf.AHIF18.GamePanel;
import at.htblakaindorf.AHIF18.Ground.Tile;
import at.htblakaindorf.AHIF18.UI;
import at.htblakaindorf.AHIF18.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CityBuildDataBase {
    private static CityBuildDataBase instance;

    private ArrayList<Tile> tiles;
    private ArrayList<Buildingname> buildings;
    private ArrayList<Buildingname> icons;

    private CityBuildDataBase() {
        tiles = new ArrayList<>();
        buildings = new ArrayList<>();
        icons = new ArrayList<>();

        try {
            //Costs: Wood, Stone, Iron, Gold
            setTiles(0, "/res/tiles/ground/Grass.png", "Grass", new String[]{""}, false, false);
            setTiles(1, "/res/tiles/ground/Tree.png", "Tree", new String[]{""}, true, false);
            setTiles(10, "/res/building/House1.png", "Villager Building", new String[]{""}, true, true);
            setTiles(11, "/res/building/Blacksmith.png", "Blacksmith", new String[]{""},true, true);
            setTiles(12, "/res/building/Church.png", "Church", new String[]{""},true, true);
            setTiles(13, "/res/building/Fisher.png", "Fisher", new String[]{""},true, true);
            setTiles(14, "/res/building/Windmill.png", "Windmill", new String[]{""},true, true);
            setTiles(15, "/res/building/Coal Mine.png", "Coal Mine", new String[]{""},true,true);
            setTiles(16, "/res/building/Iron Mine.png", "Iron Mine", new String[]{""},true, true);
            setTiles(17, "/res/building/WheatFarm.png", "Wheat Farm", new String[]{""},true, true);
            setTiles(30, "/res/tiles/ground/Farmer Wheatfield.png", "Wheatfield", new String[]{""},true, false);
            setTiles(31, "/res/icons/Bread.png", "Bread", new String[]{""},false, false);
            setTiles(32, "/res/icons/Iron_Ingot.png", "Iron",new String[]{""}, false, false);
            setTiles(33, "/res/icons/Wood.png", "Wood",new String[]{""}, false, false);
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

    public void setTiles(int id, String path, String name, boolean collision, boolean isBuilding) throws IOException {
        Tile tile = new Tile();
        tile.setCollision(collision);
        tile.setPath(path);
        tile.setImage(ImageIO.read(getClass().getResourceAsStream(path)));
        tile.setId(id);
        tile.setName(name);
        tile.setBuilding(isBuilding);
        tiles.add(tile);
    }

    public void setBuildings(UI ui) {
        for (Tile tile : tiles) {
            if (tile.getId() >= 10 && tile.getId() < 30) {
                buildings.add(new Buildingname(tile.getId(), tile.getPath(), tile.getName(), ui));
            }
            if (tile.getId() > 30 && tile.getId() < 40) {
                icons.add(new Buildingname(tile.getId(), tile.getPath(), tile.getName(), ui));
            }
        }
    }

    public Tile getTileById(int id) {
        return tiles.stream().filter(t -> t.getId() == id).findFirst().get();
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

    public ArrayList<Buildingname> getIcons() {
        return icons;
    }

}
