package at.htblakaindorf.AHIF18;

import at.htblakaindorf.AHIF18.GamePanel;
import at.htblakaindorf.AHIF18.Ground.Behaviours.*;
import at.htblakaindorf.AHIF18.Ground.Buildingobjects.*;
import at.htblakaindorf.AHIF18.Ground.Tile;
import at.htblakaindorf.AHIF18.db.CityBuildDataBase;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Management class for all {@link Tile}s on the map
 *
 * @author Manuel Reinprecht
 * @author Marcel Schmidl
 * @version 1.2
 * */
public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];
    int defMapTileNum[][];

    private File defaultFile = Paths.get("", "data/map", "Defaultmap.txt").toFile();
    private File finalMapFile = Paths.get("", "data/map", "Finalmap.txt").toFile();
    private ArrayList<Tile> tilesList;

    public TileManager(GamePanel gp) {
        try {
            tilesList = new ArrayList<>();
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Load the playermap with the given {@link BufferedReader} and set the {@link Building}s
     *
     * @param br BufferedReader to load the map
     * @param map contains id's of the map
     * */
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
            setProductionRate();
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
    //REAL EARNINGS
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
            case 4:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Stone("Stone", false, 4,
                        false, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0}, col, row));
                break;
            case 5:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Ironfield("Ironfield", false, 5,
                        false, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0}, col, row));
                break;
            case 6:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Coalfield("Coal", false, 6,
                        false, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0}, col, row));
                break;
            case 10:
                tilesList.add((row * gp.getMaxWorldRow() + col), new House("House", true, 10,
                        true, new int[]{50, 0, 0, 0}, new int[]{-10, 0, 0, 0, 5}, col, row));
                break;
            case 11:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Blacksmith("Blacksmith", true, 11,
                        true, new int[]{80, 10, 0, 100}, new int[]{0, 0, 0, 0, 0}, col, row));
                break;
            case 12:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Church("Church", true, 12,
                        true, new int[]{0, 300, 10, 1000}, new int[]{0, 0, 0, 0, 100}, col, row));
                break;
            case 13:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Fisher("Fisher", true, 13,
                        true, new int[]{20, 0, 0, 0}, new int[]{10, 0, 0, 0, 0}, col, row));
                break;
            case 14:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Windmill("Windmill", true, 14,
                        true, new int[]{200, 0, 0, 10}, new int[]{0, 0, 0, 0, 0}, col, row));
                break;
            case 15:
                tilesList.add((row * gp.getMaxWorldRow() + col), new CoalMine("CoalMine", true, 15,
                        true, new int[]{40, 0, 0, 50}, new int[]{0, 0, 0, 0, 0}, col, row));
                break;
            case 16:
                tilesList.add((row * gp.getMaxWorldRow() + col), new IronMine("IronMine", true, 16,
                        true, new int[]{70, 5, 0, 100}, new int[]{0, 0, 0, 10, 0}, col, row));
                break;
            case 17:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Farmer("Farmer", true, 17,
                        true, new int[]{40, 0, 0, 50}, new int[]{20, 0, 0, 0, 0}, col, row));
                break;
            case 18:
                tilesList.add((row * gp.getMaxWorldRow() + col), new Bakery("Bakery", true, 18,
                        true, new int[]{40, 0, 0, 50}, new int[]{0, 0, 0, 0, 0}, col, row));
                break;
            default:
                break;
        }
    }

    /**
     * Writes each {@link Tile} from defaultMap into the finalMap
     */
    public void buildMap() {
        try {
            String playerLine;
            String defaultLine;
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Adds a {@link Building} with its position on the map to a {@link List}.
     *
     * @param colpos column position of the {@link Building} on the map
     * @param rowpos row position of the {@link Building} on the map
     * @param building {@link Tile} which will be added to the map
     */
    public void setBuilding(int colpos, int rowpos, Tile building) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(finalMapFile));

            int counter = 0;
            String line;
            String[] linesPlayer = new String[50];
            String[] lineColPlayer;


            while ((line = br.readLine()) != null) {
                linesPlayer[counter] = line;
                counter++;
            }

            lineColPlayer = linesPlayer[rowpos - 1].split(" ");
            lineColPlayer[colpos - 1] = building.getId() + "";


            writeNewFile(lineColPlayer, linesPlayer, rowpos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes the {@link Building} on the given position from the map
     *
     * @param colpos column position of the {@link Building} on the map
     * @param rowpos row position of the {@link Building} on the map
     * */
    public void removeBuilding(int colpos, int rowpos) {
        try {
            String linePlayer;
            String lineDefault;
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


            writeNewFile(lineColPlayer, linesPlayer, rowpos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the new lines into the map
     *
     * @param rowpos row position of the {@link Tile}
     * @param lineColPlayer column IDs of the gamemap
     * @param linesPlayer lines of the gamemap file
     * */
    public void writeNewFile(String[] lineColPlayer, String[] linesPlayer, int rowpos) throws IOException {
       String line = "";

        for (int i = 0; i < lineColPlayer.length; i++) {
            if (i == lineColPlayer.length - 1) {
                line += lineColPlayer[i];
            } else {
                line += lineColPlayer[i] + " ";
            }
        }
        linesPlayer[rowpos - 1] = line;

        FileOutputStream fw = new FileOutputStream(finalMapFile, false);
        String content = "";
        for (int i = 0; i < linesPlayer.length; i++) {
            if (i == linesPlayer.length - 1) {
                content += linesPlayer[i];
            } else {
                content += linesPlayer[i] + "\n";
            }
        }
        fw.flush();
        fw.write(content.getBytes(StandardCharsets.UTF_8));
        fw.close();

        BufferedReader b = new BufferedReader(new FileReader(finalMapFile));
        loadMap(b, mapTileNum);
    }


    /**
     * Returns the ID of the {@link Tile} which is currently on the place of the given column and row
     *
     * @param colpos column position of the {@link Tile}
     * @param rowpos row position of the {@link Tile}
     * @return ID of the {@link Tile}
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

    /**
     * Sets the production rate of every {@link Building} on the map base on the
     * {@link Tile} they are placed on
     * */
    public void setProductionRate() { // die Überprüfungen werden nach ca 200 tiles nicht mehr richtig ausgeführt
        for (Tile tile1 : tilesList) {
            Building building = (Building) tile1;
            int id = getGroundIDFromPosition(building.getCol(), building.getRow());
            switch (id) {
                case 0:
                    building.setProduceBehaviour(new ProduceFoodAverage());
                    break;
                case 1:
                    building.setProduceBehaviour(new ProduceFoodBad());
                    break;
                case 2:
                    building.setProduceBehaviour(new ProduceFoodGood());
                    break;
                case 5:
                    building.setProduceBehaviour(new ProduceIronBad());
                    break;
                case 6:
                    building.setProduceBehaviour(new ProduceIronGood());
                    break;
                default:
                    return;
            }
            building.produce();
        }
    }

    /**
     * Returns the ID of the {@link Tile} which is the ground on the given position
     *
     * @param colpos column position of the {@link Tile}
     * @param rowpos row position of the {@link Tile}
     *
     * @return returns the ID of the {@link Tile}
     * */
    public int getGroundIDFromPosition(int colpos, int rowpos) {
        int result = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(defaultFile));
            String line;
            int counterRow = 0;

            while ((line = br.readLine()) != null) {
                String[] splittedLine = line.split(" ");
                if (rowpos == counterRow) {
                    result = Integer.parseInt(splittedLine[colpos]);
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

    /**
     * Checks if the {@link Tile} on the given position is an obstacle or not
     *
     * @param colpos column position of the {@link Tile}
     * @param rowpos row position of the {@link Tile}
     *
     * @return true, if the {@link Tile} is an obstacle
     */
    public boolean isObstacle(int colpos, int rowpos) {
        return CityBuildDataBase.getInstance().getTileById(getIdFromPosition(colpos, rowpos)).isCollision();
    }

    /**
     * Checks if the {@link Tile} on the given position is an {@link Building} or not
     *
     * @param colpos column position of the {@link Tile}
     * @param rowpos row position of the {@link Tile}
     *
     * @return true, if the {@link Tile} is an {@link Building}
     */
    public boolean isBuilding(int colpos, int rowpos) {
        return CityBuildDataBase.getInstance().getTileById(getIdFromPosition(colpos, rowpos)).isBuilding();
    }
    public boolean isNormal(int colpos, int rowpos) {
        return CityBuildDataBase.getInstance().getTileById(getIdFromPosition(colpos, rowpos)).isNormal();
    }


    /**
     * Sets the size of the {@link Tile} based on the {@link GamePanel} and
     * the {@link Tile} for the {@link Image}
     * */
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

    public Tile[] getTile() {
        return tile;
    }

    public void setTile(Tile[] tile) {
        this.tile = tile;
    }
}
