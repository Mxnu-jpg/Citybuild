package at.htblakaindorf.AHIF18.db;

import at.htblakaindorf.AHIF18.Buildingname;
import at.htblakaindorf.AHIF18.GamePanel;
import at.htblakaindorf.AHIF18.Ground.Tile;
import at.htblakaindorf.AHIF18.UI;
import at.htblakaindorf.AHIF18.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.*;
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
            setTiles(0, "/res/tiles/ground/Grass.png", "Grass", new int[]{},new int[]{}, false, false);
            setTiles(1, "/res/tiles/ground/Sand.png", "Sand", new int[]{},new int[]{}, false, false);
            setTiles(2, "/res/tiles/ground/Flowers.png", "Wiese", new int[]{},new int[]{}, false, false);
            setTiles(3, "/res/tiles/ground/Tree.png", "Baum", new int[]{},new int[]{}, true, false);
            setTiles(4, "/tiles/ground/Coal-Ore.png", "Kohlefeld", new int[]{},new int[]{}, false, false);
            setTiles(5, "/tiles/ground/Bad-Iron-Ore.png", "wenig Eisenerz", new int[]{},new int[]{}, false, false);
            setTiles(6, "/res/tiles/ground/Iron-Ore.png", "viel Eisenerz", new int[]{},new int[]{}, false, false);
            setTiles(7, "/res/tiles/ground/Stone-Field.png", "Stein", new int[]{},new int[]{}, false, false);
            setTiles(8, "/res/tiles/ground/Normal Water.png", "Fischloses Wasser", new int[]{},new int[]{}, false, false);
            setTiles(9, "/res/tiles/ground/Rewarding Water.png", "Fischreiches Wasser", new int[]{},new int[]{}, false, false);
            setTiles(10, "/res/building/House1.png", "Haus", new int[]{50,0,0,0},new int[]{}, true, true);
            setTiles(11, "/res/building/Church.png", "Kirche", new int[]{0,300,10,1000},new int[]{0, 0, 0, 100},true, true);
            setTiles(12, "/res/building/Blacksmith.png", "Schmiede", new int[]{80,10,0,100},new int[]{0, 0, 00, 0},true, true);
            setTiles(13, "/res/building/Fisher.png", "Fischer", new int[]{20,0,0,0},new int[]{25, 0, 0, 0},true, true);
            setTiles(14, "/res/building/WheatFarm.png", "Wheat Farm", new int[]{45,0,0,50},new int[]{50,0,0,0},true, true);
            setTiles(15, "/res/building/Windmill.png", "Muehle", new int[]{200,0,0,10},new int[]{0,0,0,0},true, true);
            setTiles(16, "/res/building/Bakery.png", "Baeckerei", new int[]{45,0,0,50},new int[]{0,0,0,0},true, true);
            setTiles(17, "/res/building/Coal Mine.png", "Kohlemine", new int[]{50,0,0,50},new int[]{0, 0, 0, 0},true,true);
            setTiles(18, "/res/building/Iron Mine.png", "Eisenmine", new int[]{70,5,0,100},new int[]{0, 0, 50, 0},true, true);
            setTiles(19, "/res/building/Quarry.png", "Steinbruch", new int[]{70,5,0,100},new int[]{0, 0, 50, 0},true, true);
            setTiles(30, "/res/tiles/ground/Farmer Wheatfield.png", "Wheatfield", new int[]{},new int[]{},true, false);
            setTiles(31, "/res/icons/Bread.png", "Bread", new int[]{},new int[]{},false, false);
            setTiles(32, "/res/icons/Wood.png", "Wood",new int[]{},new int[]{}, false, false);
            setTiles(33, "/res/icons/Stone.png", "Stone",new int[]{},new int[]{}, false, false);
            setTiles(34, "/res/icons/Iron_Ingot.png", "Iron",new int[]{},new int[]{}, false, false);
            setTiles(35, "/res/icons/Gold_Ingot.png", "Gold",new int[]{},new int[]{}, false, false);
            setTiles(36, "/res/icons/Delete_Building.png", "Delete",new int[]{},new int[]{}, false, false);
            setTiles(37, "/res/icons/Settings.png", "Settings",new int[]{},new int[]{}, false, false);
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

    public void setTiles(int id, String path, String name,int[] costs, int[] earnings, boolean collision, boolean isBuilding) throws IOException {
        Tile tile = new Tile();
        tile.setCollision(collision);
        tile.setPath(path);
        tile.setImage(ImageIO.read(getClass().getResourceAsStream(path)));
        tile.setId(id);
        tile.setCosts(costs);
        tile.setEarnings(earnings);
        tile.setName(name);
        tile.setBuilding(isBuilding);
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
    public int getIDperName(String name){
        for (Tile tile : tiles) {
           if(tile.getName().equals(name))
               return tile.getId();
        }
        JOptionPane.showMessageDialog(null, "Leider gab es  waehrend der Berechung des Spieles einen Fehler, CityBuildDatabase, getIPperName\n", "Fehler", JOptionPane.ERROR_MESSAGE);
        return 0;
    }
    public String getNameperID(int id){
        for (Tile tile : tiles) {
            if(tile.getId() == id)
                return tile.getName();
        }
        JOptionPane.showMessageDialog(null, "Leider gab es  waehrend der Berechung des Spieles einen Fehler, CityBuildDatabase, getNameperIP\n", "Fehler", JOptionPane.ERROR_MESSAGE);
        return "";
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
