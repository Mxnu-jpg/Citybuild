package at.htblakaindorf.AHIF18.Ground;

import at.htblakaindorf.AHIF18.GamePanel;
import at.htblakaindorf.AHIF18.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];


    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[100];
        mapTileNum = new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];
        getTileImage();
        loadMap();
    }

    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/res/map/world01.txt");
            BufferedReader br = new BufferedReader(new BufferedReader(new InputStreamReader(is)));


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
                    }catch (ArrayIndexOutOfBoundsException e){
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
        setup(0, "/res/tiles/ground/grass");
        /*setup(1, "/res/tiles/ground/grass");
        setup(2, "/res/tiles/ground/grass");
        setup(3, "/res/tiles/ground/grass");
        setup(4, "/res/tiles/ground/grass");
        setup(5, "/res/tiles/ground/grass");
        setup(6, "/res/tiles/ground/grass");
        setup(7, "/res/tiles/ground/grass");
        setup(8, "/res/tiles/ground/grass");
        setup(9, "/res/tiles/ground/grass");*/
        setup(10, "/res/building/building1");
    }

    public void setup(int index, String imagePath){
        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream( imagePath + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.getTileSize(), gp.getTileSize());
        }catch(IOException e){
         e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        int tileNum;
        int worldX;
        int worldY;

        while (worldCol < gp.getMaxWorldCol() && worldRow < gp.getMaxWorldRow()) {

            tileNum = mapTileNum[worldCol][worldRow];
            worldX = worldCol * gp.getTileSize();
            worldY = worldRow * gp.getTileSize();
            double screenX =  (worldX - gp.getPlayer().worldX + gp.getPlayer().screenX);
            double screenY =  (worldY - gp.getPlayer().worldY + gp.getPlayer().screenY);


            if (    worldX + gp.getTileSize() > gp.getPlayer().worldX - gp.getPlayer().screenX &&
                    worldX - gp.getTileSize() < gp.getPlayer().worldX + gp.getPlayer().screenX &&
                    worldY + gp.getTileSize() > gp.getPlayer().worldY - gp.getPlayer().screenY &&
                    worldY - gp.getTileSize() < gp.getPlayer().worldY + gp.getPlayer().screenY) {
                try {
                    if(tileNum != 0){
                        g2.drawImage(tile[0].image, (int)screenX, (int)screenY, gp.getTileSize(), gp.getTileSize(), null);
                    }
                    g2.drawImage(tile[tileNum].image, (int)screenX, (int)screenY, gp.getTileSize(), gp.getTileSize(), null);

                }catch (NullPointerException e){
                    System.out.println("Die ausgewählte Ressource(Bild) auf der Welt ist nicht vorhanden");
                }
            }


            worldCol++;


            if (worldCol == gp.getMaxWorldCol()) {
                worldCol = 0;
                worldRow++;
            }

        }
    }
}
