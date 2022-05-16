package at.htblakaindorf.AHIF18.Ground;

import at.htblakaindorf.AHIF18.GamePanel;
import at.htblakaindorf.AHIF18.Ground.Behaviours.ProduceAverage;
import at.htblakaindorf.AHIF18.Ground.Buildingobjects.*;
import at.htblakaindorf.AHIF18.db.CityBuildDataBase;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class TileManager {

    GamePanel gp;
    Tile[] tile;
    Graphics2D g2M;
    int mapTileNum[][];
    private File playerFile = Paths.get("", "data/map", "Playermap.txt").toFile();
    private File defaultFile = Paths.get("", "data/map", "Defaultmap.txt").toFile();
    private File finalMapFile = Paths.get("", "data/map", "Finalmap.txt").toFile();
    private ArrayList<Tile> tilesList;
    private ArrayList<Tile> tileObjectsList;


    public void setG2M(Graphics2D g2M) {
        this.g2M = g2M;
    }

    public TileManager(GamePanel gp) {
        try {
            tilesList = new ArrayList<>();
            tileObjectsList = new ArrayList<>();
            System.out.printf(playerFile.getPath());
            playerFile.setWritable(true);
            this.gp = gp;
            tile = new Tile[100];
            mapTileNum = new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];
            getTileImage();
            buildMap();
            BufferedReader br = new BufferedReader(new FileReader(finalMapFile));
            loadMap(br);
            loadPlayerMapTileObjects();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void loadMap(BufferedReader br) {
        try {
            int col = 0;
            int row = 0;
            while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
                String line = br.readLine();

                while (col < gp.getMaxWorldCol()) {
                    String numbers[] = line.split(" ");
                    try {
                        int num = Integer.parseInt(numbers[col]);
                        mapTileNum[col][row] = num;

                        createSpecificBuilding(num, col, row);
                        CityBuildDataBase.getInstance().setMapList(tilesList);
                        col++;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        JOptionPane.showMessageDialog(null,
                                "Die Welt konnte nicht geladen werden\n" + "Maximale Spalten: " + gp.getMaxWorldCol() +
                                        ", maximale Zeilen: " + gp.getMaxWorldRow(), "Laden fehlgeschlagen",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                }
                if (col == gp.getMaxWorldCol()) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getTileImage() {
        CityBuildDataBase.getInstance().setTileSize(gp);
        List<Tile> tiles = CityBuildDataBase.getInstance().getTiles();
        for (Tile tile1 : tiles) {
            tile[tile1.getId()] = tile1;
        }
    }

    /*public void setup(int index, String imagePath, String name, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.getTileSize(), gp.getTileSize());
            tile[index].setName(name);
            tile[index].setCollision(collision);
            tile[index].setId(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        int tileNum;
        int worldX;
        int worldY;
        double screenX;
        double screenY;


        while (worldCol < gp.getMaxWorldCol() && worldRow < gp.getMaxWorldRow()) {
            tileNum = mapTileNum[worldCol][worldRow];
            worldX = worldCol * gp.getTileSize();
            worldY = worldRow * gp.getTileSize();
            screenX = (worldX - gp.getPlayer().worldX + gp.getPlayer().screenX);
            screenY = (worldY - gp.getPlayer().worldY + gp.getPlayer().screenY);
            //Camera am Rand
            if (gp.getPlayer().screenX > gp.getPlayer().worldX) {
                screenX = worldX;
            }
            if (gp.getPlayer().screenY > gp.getPlayer().worldY + gp.getTileSize()) {
               screenY = worldY;
            }
            if (gp.getPlayer().screenY > gp.getPlayer().worldY + gp.getUi().getHeight_of_Top_UI()) {
                screenY = worldY + gp.getUi().getHeight_of_Top_UI();
            }
            int rightOffset = gp.getScreenWidth() - gp.getPlayer().screenX;
            if (rightOffset > gp.worldWidth - gp.getPlayer().worldX) {
                screenX = gp.getScreenWidth() - (gp.worldWidth - worldX);

            }
            int bottomOffset = gp.getScreenHeight() - (gp.getPlayer().screenY);
            if (bottomOffset > gp.getWorldHeight() - gp.getPlayer().worldY + gp.getUi().getHeight_of_Bottom_UI()) {
                screenY = gp.getScreenHeight() - (gp.getWorldHeight() - worldY + gp.getUi().getHeight_of_Bottom_UI());
            }

            //Nur was man sehen kann drawen
            if (worldX + gp.getTileSize() > gp.getPlayer().worldX - gp.getPlayer().screenX &&
                    worldX - gp.getTileSize() < gp.getPlayer().worldX + gp.getPlayer().screenX &&
                    worldY + gp.getTileSize() > gp.getPlayer().worldY - gp.getPlayer().screenY &&
                    worldY - gp.getTileSize() < gp.getPlayer().worldY + gp.getPlayer().screenY) {
                try {
                    //draw Map transparent
                    if (tileNum != 0)
                        g2.drawImage(tile[0].image, (int) screenX, (int) screenY, gp.getTileSize(), gp.getTileSize(), null);
                        g2.drawImage(tile[tileNum].image, (int) screenX, (int) screenY, gp.getTileSize(), gp.getTileSize(), null);

                } catch (NullPointerException e) {
                    System.out.println("Die ausgewählte Ressource(Bild) nicht vorhanden oder nicht initialisiert, ID-Map:" + tileNum);
                    e.printStackTrace();
                }
            } else if (gp.getPlayer().screenX > gp.getPlayer().worldX ||
                    gp.getPlayer().screenY > gp.getPlayer().worldY ||
                    rightOffset > gp.worldWidth - gp.getPlayer().worldX ||
                    bottomOffset > gp.worldHeight - gp.getPlayer().worldY) {

                if (tileNum != 0)
                    g2.drawImage(tile[0].image, (int) screenX, (int) screenY, gp.getTileSize(), gp.getTileSize(), null);
                    g2.drawImage(tile[tileNum].image, (int) screenX, (int) screenY, gp.getTileSize(), gp.getTileSize(), null);
            }
            worldCol++;
            if (worldCol == gp.getMaxWorldCol()) {
                worldCol = 0;
                worldRow++;
            }


        }
    }

    public void setBuilding(int colbuidling, int rowbuilding, Tile building) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(playerFile));

            int counter = 0;
            String line = "";
            String helpLine = "";
            String[] lines = new String[50];
            String[] lineCol;


            while ((line = br.readLine()) != null) {
                lines[counter] = line;
                counter++;
            }

            lineCol = lines[rowbuilding - 1].split(" ");
            lineCol[colbuidling - 1] = building.getId() + "";

            for (int i = 0; i < lineCol.length; i++) {
                if (i == lineCol.length - 1) {
                    helpLine += lineCol[i];
                } else {
                    helpLine += lineCol[i] + " ";
                }
            }
            lines[rowbuilding - 1] = helpLine;

            FileOutputStream fw = new FileOutputStream(playerFile, false);
            String content = "";
            for (int i = 0; i < lines.length; i++) {
                if (i == lines.length - 1) {
                    content += lines[i];
                } else {
                    content += lines[i] + "\n";
                }
            }
            //System.out.println(content);
            fw.flush();
            fw.write(content.getBytes(StandardCharsets.UTF_8));
            fw.close();

            BufferedReader b = new BufferedReader(new FileReader(playerFile));
            loadMap(b);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Die ausgewählte Map konnte nicht gefunden werden, Quelle: " + playerFile.getPath(), "Ressource konnte nicht gefunden werden",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isObstacle(int colpos, int rowpos) {
        //TODO:soll den collission boolean von dem ausgewählten col und row zurückgeben

        return CityBuildDataBase.getInstance().getTileById(getIdFromPosition(colpos, rowpos)).isCollision();
    }

    public boolean isBuilding(int colpos, int rowpos) {
        //TODO:soll den buidling boolean von dem ausgewählten col und row zurückgeben

        return CityBuildDataBase.getInstance().getTileById(getIdFromPosition(colpos, rowpos)).isBuilding();
    }

    public void removeBuilding(int colpos, int rowpos) {
        //TODO:überschreibe wert von Playerfile mit dem Wert der gleichen Stelle von Defaultmap
        try {
            String linePlayer;
            String lineDefault;
            String helpLine = "";
            String[] linesPlayer = new String[50];
            String[] linesDefault = new String[50];

            String[] lineColPlayer;
            String[] lineColDefault;

            int counter = 0;

            BufferedReader brPlayer = new BufferedReader(new FileReader(playerFile));
            BufferedReader brDefault = new BufferedReader(new FileReader(defaultFile));

            while ((linePlayer = brPlayer.readLine()) != null && (lineDefault = brDefault.readLine()) != null) {
                linesPlayer[counter] = linePlayer;
                linesDefault[counter] = lineDefault;
                counter++;
            }

            lineColPlayer = linesPlayer[rowpos - 1].split(" ");

            lineColDefault = linesDefault[rowpos - 1].split(" ");
            lineColPlayer[colpos - 1] = lineColDefault[colpos - 1];

            for (int i = 0; i < lineColPlayer.length; i++) {
                if (i == lineColPlayer.length - 1) {
                    helpLine += lineColPlayer[i];
                } else {
                    helpLine += lineColPlayer[i] + " ";
                }
            }
            linesPlayer[rowpos - 1] = helpLine;

            FileOutputStream fw = new FileOutputStream(playerFile, false);
            String content = "";
            for (int i = 0; i < linesPlayer.length; i++) {
                if (i == linesPlayer.length - 1) {
                    content += linesPlayer[i];
                } else {
                    content += linesPlayer[i] + "\n";
                }
            }
            //System.out.println(content);
            fw.flush();
            fw.write(content.getBytes(StandardCharsets.UTF_8));
            fw.close();

            BufferedReader b = new BufferedReader(new FileReader(playerFile));
            loadMap(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getIdFromPosition(int colpos, int rowpos) {
        int id = 0;
        try {

            BufferedReader br = new BufferedReader(new FileReader(playerFile));

            int counter = 0;
            String line = "";
            String[] lines = new String[50];
            String[] lineCol;

            while ((line = br.readLine()) != null) {
                lines[counter] = line;
                counter++;
            }

            lineCol = lines[rowpos -1].split(" ");
            id = Integer.parseInt(lineCol[colpos-1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    public ArrayList<Tile> getTilesList() {
        return tilesList;
    }

    public void loadPlayerMapTileObjects() {
        try {
            BufferedReader brPlayerFile = new BufferedReader(new FileReader(playerFile));
            String line = "";
            String[] splitLine;

            while ((line = brPlayerFile.readLine()) != null) {
                splitLine = line.split(" ");
                for (int i = 0; i < splitLine.length - 1; i++) {
                    //System.out.println(splitLine[i]);
                    //Switch -> id -> neues Objekt erstellen

                    tileObjectsList.add(CityBuildDataBase.getInstance().getTileById(Integer.parseInt(splitLine[i])));
                }
            }
            brPlayerFile.close();
        } catch (IOException e) {

        }
    }

    /**
     * Adds each kind of building with its position on the map to a list.
     * @param id id of the building which is added
     * @param col column position of the building
     * @param row row position of the building
     */
    public void createSpecificBuilding(int id, int col, int row) {
        //Update bei neuen Building
        switch (id) {
            case 0:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Grass(col, row));
                break;
            case 1:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Sand(col, row));
                break;
            case 2:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Flowers(col, row));
                break;
            case 3:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Tree(col, row));
                break;
            case 10:
                tilesList.add((row * gp.getMaxWorldRow() + col), new House(col, row));
                break;
            case 11:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Blacksmith(col, row));
                break;
            case 12:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Church(col, row));
                break;
            case 13:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Fisher(col, row));
                break;
            case 14:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Windmill(col, row));
                break;
            case 15:
                tilesList.add((row * gp.getMaxWorldRow() + col), new CoalMine(col, row));
                break;
            case 16:
                tilesList.add((row * gp.getMaxWorldRow() + col), new IronMine(col, row));
                break;
            case 17:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Farmer(col, row));
                break;
            case 18:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Bakery(col, row));
                break;
            default:
                break;
        }
    }
    public void setProductionRate(){
        for (Tile tile1 : tilesList) {
            tile1.setProduceBehaviour(new ProduceAverage());
            tile1.produce();
        }
    }
    /*public int getGroundIDFromPosition(int col, int row){
        int id = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(defaultFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return id;
    }*/

    public void buildMap(){
        try {
            String playerLine = "";
            String defaultLine = "";
            String finalMap = "";

            BufferedReader brPlayerMap = new BufferedReader(new FileReader(playerFile));
            BufferedReader brDefaultMap = new BufferedReader(new FileReader(defaultFile));

            while((playerLine = brPlayerMap.readLine()) != null
                    && (defaultLine = brDefaultMap.readLine()) != null){

                String[] playerIDs = playerLine.split(" ");
                String[] defaultIDs = defaultLine.split(" ");;

                for(int i = 0; i < 50; i++){
                    //System.out.println(playerIDs[i] + " " + defaultIDs[i]);
                    if(!playerIDs[i].matches("[0-9]")){
                        finalMap += playerIDs[i] + " ";
                    }else{
                        finalMap += defaultIDs[i] + " ";
                    }
                    if(i == 49){
                        finalMap += "\n";
                    }
                }
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(finalMapFile));
            bw.flush();
            bw.write(finalMap);
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
