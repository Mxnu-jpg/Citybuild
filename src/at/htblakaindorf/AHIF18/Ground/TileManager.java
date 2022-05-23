package at.htblakaindorf.AHIF18.Ground;

import at.htblakaindorf.AHIF18.GamePanel;
import at.htblakaindorf.AHIF18.Ground.Behaviours.ProduceAverage;
import at.htblakaindorf.AHIF18.Ground.Behaviours.ProduceBad;
import at.htblakaindorf.AHIF18.Ground.Behaviours.ProduceGood;
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
    int defMapTileNum[][];

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
            finalMapFile.setWritable(true);
            this.gp = gp;
            tile = new Tile[100];
            mapTileNum = new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];
            defMapTileNum = new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];
            getTileImage();
            buildMap();
            BufferedReader b = new BufferedReader(new FileReader(defaultFile));
            loadMap(b, defMapTileNum);

            BufferedReader br = new BufferedReader(new FileReader(finalMapFile));
            loadMap(br, mapTileNum);
            loadPlayerMapTileObjects();

            setProductionRate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void loadMap(BufferedReader br, int map[][]) {
        try {
            int col = 0;
            int row = 0;
            while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
                String line = br.readLine();

                while (col < gp.getMaxWorldCol()) {
                    String numbers[] = line.split(" ");
                    try {
                        int num = Integer.parseInt(numbers[col]);
                        map[col][row] = num;
                        System.out.println(num);
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

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        int tileNum;
        int defTileMap;
        int worldX;
        int worldY;
        double screenX;
        double screenY;


        while (worldCol < gp.getMaxWorldCol() && worldRow < gp.getMaxWorldRow()) {
            tileNum = mapTileNum[worldCol][worldRow];
            defTileMap = defMapTileNum[worldCol][worldRow];
            worldX = worldCol * gp.getTileSize();
            worldY = worldRow * gp.getTileSize();
            screenX = (worldX - gp.getPlayer().worldX + gp.getPlayer().screenX);
            screenY = (worldY - gp.getPlayer().worldY + gp.getPlayer().screenY);
            //Kamera am Rand
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
                    if (!(tileNum >= 0 && tileNum < 10))
                        g2.drawImage(tile[defTileMap].image, (int) screenX, (int) screenY, gp.getTileSize(), gp.getTileSize(), null);
                    g2.drawImage(tile[tileNum].image, (int) screenX, (int) screenY, gp.getTileSize(), gp.getTileSize(), null);

                } catch (NullPointerException e) {
                    System.out.println("Die ausgewählte Ressource(Bild) nicht vorhanden oder nicht initialisiert, ID-Map:" + tileNum);
                    e.printStackTrace();
                }
            } else if (gp.getPlayer().screenX > gp.getPlayer().worldX ||
                    gp.getPlayer().screenY > gp.getPlayer().worldY ||
                    rightOffset > gp.worldWidth - gp.getPlayer().worldX ||
                    bottomOffset > gp.worldHeight - gp.getPlayer().worldY) {

               /* if (tileNum != 0)
                    g2.drawImage(tile[0].image, (int) screenX, (int) screenY, gp.getTileSize(), gp.getTileSize(), null);
                g2.drawImage(tile[tileNum].image, (int) screenX, (int) screenY, gp.getTileSize(), gp.getTileSize(), null);
                */
            }
            worldCol++;
            if (worldCol == gp.getMaxWorldCol()) {
                worldCol = 0;
                worldRow++;
            }


        }
    }

    /**
     * Adds each kind of building with its position on the map to a list.
     *
     * @param id  id of the building which is added
     * @param col column position of the building
     * @param row row position of the building
     */
    public void createSpecificBuilding(int id, int col, int row) {
        if(tilesList.size() == 2500){
            tilesList.remove(row * gp.getMaxWorldRow() + col);
        }
        //Update bei neuen Building
        switch (id) {
            case 0:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Grass("Grass", false, 0,
                        false, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0}, col, row));
                break;
            case 1:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Sand("Sand", false, 1,
                        false, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0}, col, row));
                break;
            case 2:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Flowers("Flowers", false, 2,
                        false, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0}, col, row));
                break;
            case 3:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Tree("Tree", false, 3,
                        false, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0}, col, row));
                break;
            case 10:
                tilesList.add((row * gp.getMaxWorldRow() + col), new House("House", true, 10,
                        true, new int[]{50, 0, 0, 0}, new int[]{-10, 0, 0, 0, 5}, col, row));
                break;
            case 11:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Blacksmith("Blacksmith", true, 11,
                        true, new int[]{80, 10, 0, 100}, new int[]{0, 0, 0, 20, 0}, col, row));
                break;
            case 12:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Church("Church", true, 12,
                        true, new int[]{0, 300, 10, 1000}, new int[]{0, 0, 0, 0, 50}, col, row));
                break;
            case 13:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Fisher("Fisher", true, 13,
                        true, new int[]{20, 0, 0, 0}, new int[]{2, 0, 0, 0, 0}, col, row));
                break;
            case 14:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Windmill("Windmill", true, 14,
                        true, new int[]{200, 0, 0, 10}, new int[]{5, 0, 0, 0, 0}, col, row));
                break;
            case 15:
                tilesList.add((row * gp.getMaxWorldRow() + col), new CoalMine("CoalMine", true, 15,
                        true, new int[]{40, 0, 0, 50}, new int[]{0, 0, 15, 15, 0}, col, row));
                break;
            case 16:
                tilesList.add((row * gp.getMaxWorldRow() + col), new IronMine("IronMine", true, 16,
                        true, new int[]{70, 5, 0, 100}, new int[]{0, 0, 0, 50, 0}, col, row));
                break;
            case 17:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Farmer("Farmer", true, 17,
                        true, new int[]{40, 0, 0, 50}, new int[]{8, 0, 0, 0, 0}, col, row));
                break;
            case 18:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Bakery("Bakery", true, 18,
                        true, new int[]{40, 0, 0, 50}, new int[]{10, 0, 0, 0, 0}, col, row));
                break;
            default:
                break;
        }
    }

    /**
     * Writes each Tile from @defaultFile into the @finalMapFile
     */
    public void buildMap() {
        try {
            String playerLine = "";
            String defaultLine = "";
            String finalMap = "";

            BufferedReader brPlayerMap = new BufferedReader(new FileReader(finalMapFile));
            BufferedReader brDefaultMap = new BufferedReader(new FileReader(defaultFile));

            while ((playerLine = brPlayerMap.readLine()) != null
                    && (defaultLine = brDefaultMap.readLine()) != null) {

                String[] playerIDs = playerLine.split(" ");
                String[] defaultIDs = defaultLine.split(" ");
                ;

                for (int i = 0; i < 50; i++) {
                    //System.out.println(playerIDs[i] + " " + defaultIDs[i]);
                    if (!playerIDs[i].matches("[0-9]")) {
                        finalMap += playerIDs[i] + " ";
                    } else {
                        finalMap += defaultIDs[i] + " ";
                    }
                    if (i == 49) {
                        finalMap += "\n";
                    }
                }
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(finalMapFile));
            bw.flush();
            bw.write(finalMap);
            bw.close();
            setProductionRate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds each kind of building with its position on the map to a list.
     *
     * @param colbuidling column position of the buidling on the map
     * @param rowbuilding row position of the building on the map
     * @param building    Tile Object which will be added to the Map
     */
    public void setBuilding(int colbuidling, int rowbuilding, Tile building) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(finalMapFile));

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

            FileOutputStream fw = new FileOutputStream(finalMapFile, false);
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
            BufferedReader b = new BufferedReader(new FileReader(finalMapFile));
            loadMap(b, mapTileNum);
            setProductionRate();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Die ausgewählte Map konnte nicht gefunden werden, Quelle: " + finalMapFile.getPath(), "Ressource konnte nicht gefunden werden",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

            BufferedReader brPlayer = new BufferedReader(new FileReader(finalMapFile));
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

            FileOutputStream fw = new FileOutputStream(finalMapFile, false);
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

            BufferedReader b = new BufferedReader(new FileReader(finalMapFile));
            loadMap(b, mapTileNum);
            setProductionRate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the ID of the Tile which is currently on the place of the given column and row
     *
     * @param colpos column-position of the Tile
     * @param rowpos row-position of the tile
     * @return ID of the Tile
     */
    public int getIdFromPosition(int colpos, int rowpos) {
        int id = 0;
        try {

            BufferedReader br = new BufferedReader(new FileReader(finalMapFile));

            int counter = 0;
            String line = "";
            String[] lines = new String[50];
            String[] lineCol;

            while ((line = br.readLine()) != null) {
                lines[counter] = line;
                counter++;
            }

            lineCol = lines[rowpos - 1].split(" ");
            id = Integer.parseInt(lineCol[colpos - 1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void loadPlayerMapTileObjects() {
        try {
            BufferedReader brPlayerFile = new BufferedReader(new FileReader(finalMapFile));
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

    public void setProductionRate() {
        for (Tile tile1 : tilesList) {
            Building building = (Building) tile1;
            int id = getGroundIDFromPosition(building.getCol(), building.getRow());
            switch (id) {
                case 0:
                    building.setProduceBehaviour(new ProduceAverage());
                    break;
                case 1:
                    building.setProduceBehaviour(new ProduceBad());
                    break;
                case 2:
                    building.setProduceBehaviour(new ProduceGood());
                    break;
                default:
                    return;
            }
            building.produce();
        }
    }

    public int getGroundIDFromPosition(int col, int row) {
        int result = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(defaultFile));
            String line;
            int counterRow = 0;

            while ((line = br.readLine()) != null) {
                String[] splittedLine = line.split(" ");
                if (row == counterRow) {
                    result = Integer.parseInt(splittedLine[col]);
                }
                counterRow++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean isObstacle(int colpos, int rowpos) {
        return CityBuildDataBase.getInstance().getTileById(getIdFromPosition(colpos, rowpos)).isCollision();
    }

    public boolean isBuilding(int colpos, int rowpos) {
        return CityBuildDataBase.getInstance().getTileById(getIdFromPosition(colpos, rowpos)).isBuilding();
    }

    public void getTileImage() {
        CityBuildDataBase.getInstance().setTileSize(gp);
        List<Tile> tiles = CityBuildDataBase.getInstance().getTiles();
        for (Tile tile1 : tiles) {
            tile[tile1.getId()] = tile1;
        }
    }

    public ArrayList<Tile> getTilesList() {
        return tilesList;
    }
}
