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

/**
 * Static Database for the map and building data
 *
 * @author Marcel Schmidl, Manuel Reinprecht
 * @version 1.2
 */
public class CityBuildDataBase {
    private static CityBuildDataBase instance;

    private ArrayList<Tile> tiles;
    private ArrayList<Buildingname> buildings;
    private ArrayList<Buildingname> icons;
    private ArrayList<Tile> mapList;

    private CityBuildDataBase() {
        tiles = new ArrayList<>();
        buildings = new ArrayList<>();
        icons = new ArrayList<>();
        mapList = new ArrayList<>();

        try {
            //Costs: Wood, Stone, Iron, Gold
            setTiles(0, "/res/tiles/ground/Grass.png", "Grass", new int[]{},new int[]{}, false, false,false);
            setTiles(1, "/res/tiles/ground/Sand.png", "Sand", new int[]{},new int[]{}, false, false,false);
            setTiles(2, "/res/tiles/ground/Flowers.png", "Flowers", new int[]{},new int[]{}, false, false,false);
            setTiles(3, "/res/tiles/ground/Tree.png", "Tree", new int[]{},new int[]{}, true, false,false);
            setTiles(4, "/tiles/ground/coalfield.png", "Colefield", new int[]{},new int[]{}, false, false,false);
            setTiles(5, "/tiles/ground/ironfield bad.png", "Ironfield Bad", new int[]{},new int[]{}, false, false,false);
            setTiles(6, "/res/tiles/ground/IronField.png", "Ironfield Good", new int[]{},new int[]{}, false, false,false);
            setTiles(10, "/res/building/House1.png", "Villager Building", new int[]{50,0,0,0},new int[]{}, true, true,true);
            setTiles(11, "/res/building/Blacksmith.png", "Blacksmith", new int[]{80,10,0,100},new int[]{0, 0, 00, 0},true, true,true);
            setTiles(12, "/res/building/Church.png", "Church", new int[]{0,300,10,1000},new int[]{0, 0, 0, 100},true, true,true);
            setTiles(13, "/res/building/Fisher.png", "Fisher", new int[]{20,0,0,0},new int[]{25, 0, 0, 0},true, true,false);
            setTiles(14, "/res/building/Windmill.png", "Windmill", new int[]{200,0,0,10},new int[]{0,0,0,0},true, true,true);
            setTiles(15, "/res/building/Coal Mine.png", "Coal Mine", new int[]{50,0,0,50},new int[]{0, 0, 0, 0},true,true,false);
            setTiles(16, "/res/building/Iron Mine.png", "Iron Mine", new int[]{70,5,0,100},new int[]{0, 0, 50, 0},true, true,false);
            setTiles(17, "/res/building/WheatFarm.png", "Wheat Farm", new int[]{45,0,0,50},new int[]{50,0,0,0},true, true,true);
            setTiles(18, "/res/building/Bakery.png", "Bakery", new int[]{45,0,0,50},new int[]{0,0,0,0},true, true,true);
            setTiles(30, "/res/tiles/ground/Farmer Wheatfield.png", "Wheatfield", new int[]{},new int[]{},true, false,true);
            setTiles(31, "/res/icons/Bread.png", "Bread", new int[]{},new int[]{},false, false,false);
            setTiles(32, "/res/icons/Wood.png", "Wood",new int[]{},new int[]{}, false, false,false);
            setTiles(33, "/res/icons/Stone.png", "Stone",new int[]{},new int[]{}, false, false,false);
            setTiles(34, "/res/icons/Iron_Ingot.png", "Iron",new int[]{},new int[]{}, false, false,false);
            setTiles(35, "/res/icons/Gold_Ingot.png", "Gold",new int[]{},new int[]{}, false, false,false);
            setTiles(36, "/res/icons/Delete_Building.png", "Delete",new int[]{},new int[]{}, false, false,false);
            setTiles(37, "/res/icons/Settings.png", "Settings",new int[]{},new int[]{}, false, false,false);
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

    public void setTiles(int id, String path, String name,int[] costs, int[] earnings, boolean collision, boolean isBuilding, boolean normal) throws IOException {
        Tile tile = new Tile();
        tile.setCollision(collision);
        tile.setPath(path);
        tile.setImage(ImageIO.read(getClass().getResourceAsStream(path)));
        tile.setId(id);
        tile.setCosts(costs);
        tile.setEarnings(earnings);
        tile.setName(name);
        tile.setBuilding(isBuilding);
        tile.setNormal(normal);
        tiles.add(tile);
    }
    public void setUiIcons(UI ui) {
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

    public ArrayList<Tile> getMapList() {
        return mapList;
    }

    public void setMapList(ArrayList<Tile> mapList) {
        this.mapList = mapList;
    }
}
