package at.htblakaindorf.AHIF18.Ground;

import at.htblakaindorf.AHIF18.GamePanel;
import at.htblakaindorf.AHIF18.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class TileManager {

    GamePanel gp;
    Tile[] tile;
    Graphics2D g2M;
    int mapTileNum[][];
    private File file = new File("C:\\Users\\Marcel\\OneDrive - HTBLA Kaindorf\\Schule\\4. Klasse\\" +
            "POS\\CityBuild\\Citybuild\\src\\res\\map\\world01.txt");

    public void setG2M(Graphics2D g2M) {
        this.g2M = g2M;
    }

    public TileManager(GamePanel gp) {
        file.setWritable(true);
        this.gp = gp;
        tile = new Tile[100];
        mapTileNum = new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];
        getTileImage();
        InputStream is = getClass().getResourceAsStream("/res/map/world01.txt");
        BufferedReader br = new BufferedReader(new BufferedReader(new InputStreamReader(is)));
        loadMap(br);
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
        setup(0, "/tiles/ground/Grass.png", "Grass", false);
        setup(1, "/tiles/ground/Tree.png", "Tree", true);
        setup(10, "/res/building/Villager.png", "Villager Building", true);
        setup(11, "/res/building/Blacksmith.png", "Blacksmith", true);
        setup(12, "/res/building/Church.png", "Church", true);
        setup(13, "/res/building/Coal Mine.png", "Coal Mine", true);
        setup(14, "/res/building/Fisher.png", "Fisher", true);
        setup(15, "/res/building/Iron Mine.png", "Iron Mine", true);
        setup(16, "/res/building/Windmill.png", "Windmill", true);
        setup(20, "/res/tiles/ground/Farmer Wheatfield.png", "Wheatfield", true);


    }

    public void setup(int index, String imagePath, String name, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.getTileSize(), gp.getTileSize());
            tile[index].name = name;
            tile[index].collision = collision;
            tile[index].setId(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

        InputStream is = getClass().getResourceAsStream("/res/map/world01.txt");
        BufferedReader br = new BufferedReader(new BufferedReader(new InputStreamReader(is)));

        int counter = 0;
        String line = "";
        String helpLine = "";
        String[] lines = new String[50];
        String[] lineCol;

        try {
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

           /* for (int i = 0; i < lines.length; i++) {
                System.out.println(lines[i]);
            }*/


            //System.out.println(file);

            FileOutputStream fw = new FileOutputStream(file, false);
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader b = new BufferedReader(new FileReader(file));
            loadMap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
