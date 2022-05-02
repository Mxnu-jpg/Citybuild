package at.htblakaindorf.AHIF18.Ground;

import at.htblakaindorf.AHIF18.GamePanel;
import at.htblakaindorf.AHIF18.db.CityBuildDataBase;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;


public class TileManager {

    GamePanel gp;
    Tile[] tile;
    Graphics2D g2M;
    int mapTileNum[][];
    private File playerFile = Paths.get("", "data/map", "Playermap.txt").toFile();
    private File defaultFile = Paths.get("", "data/map", "Defaultmap.txt").toFile();

    public void setG2M(Graphics2D g2M) {
        this.g2M = g2M;
    }

    public TileManager(GamePanel gp) {
        try {
            System.out.printf(playerFile.getPath());
            playerFile.setWritable(true);
            this.gp = gp;
            tile = new Tile[100];
            mapTileNum = new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];
            getTileImage();
            BufferedReader br = new BufferedReader(new FileReader(playerFile));
            loadMap(br);
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

    private void getTileImage() {
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
        return CityBuildDataBase.getInstance().getTileById(getIdFromPosition(colpos, rowpos)).isCollision();
    }

    public boolean isBuilding(int colpos, int rowpos) {
        return CityBuildDataBase.getInstance().getTileById(getIdFromPosition(colpos, rowpos)).isBuilding();
    }

    public void removeBuilding(int colpos, int rowpos) {
        try {
            String linePlayer = "";
            String lineDefault = "";
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

            lineCol = lines[rowpos - 1].split(" ");
            id = Integer.parseInt(lineCol[colpos - 1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }
}
