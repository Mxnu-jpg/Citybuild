package at.htblakaindorf.AHIF18.Ground;

import at.htblakaindorf.AHIF18.GamePanel;
import at.htblakaindorf.AHIF18.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class TileManager {

    GamePanel gp;
    Tile[] tile;
    Graphics2D g2M;
    int mapTileNum[][];

    public void setG2M(Graphics2D g2M) {
        this.g2M = g2M;
    }

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
        setup(0, "/res/tiles/ground/grass.png");
        setup(1, "/res/tiles/ground/tree.png");
        setup(10, "/res/building/Villager.png");
    }

    public void setup(int index, String imagePath){
        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream( imagePath));
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
        double screenX;
        double screenY;

        while (worldCol < gp.getMaxWorldCol() && worldRow < gp.getMaxWorldRow()) {
            tileNum = mapTileNum[worldCol][worldRow];
            worldX = worldCol * gp.getTileSize();
            worldY = worldRow * gp.getTileSize();
            screenX =  (worldX - gp.getPlayer().worldX + gp.getPlayer().screenX);
            screenY =  (worldY - gp.getPlayer().worldY + gp.getPlayer().screenY);
            //Camera am Rand
            if(gp.getPlayer().screenX > gp.getPlayer().worldX){
                screenX = worldX;
            }
            if(gp.getPlayer().screenY > gp.getPlayer().worldY+gp.getUi().getHeight_of_Top_UI()){
                screenY = worldY+gp.getUi().getHeight_of_Top_UI();
            }
            int rightOffset = gp.getScreenWidth() - gp.getPlayer().screenX;
            if(rightOffset > gp.worldWidth - gp.getPlayer().worldX){
                screenX = gp.getScreenWidth() - (gp.worldWidth - worldX);

            }
            int bottomOffset = gp.getScreenHeight() - (gp.getPlayer().screenY);
            if(bottomOffset > gp.getWorldHeight() - gp.getPlayer().worldY+gp.getUi().getHeight_of_Bottom_UI()){
                screenY = gp.getScreenHeight() - (gp.getWorldHeight()-worldY+gp.getUi().getHeight_of_Bottom_UI());
            }

            //Nur was man sehen kann drawen
            if (    worldX + gp.getTileSize() > gp.getPlayer().worldX - gp.getPlayer().screenX &&
                    worldX - gp.getTileSize() < gp.getPlayer().worldX + gp.getPlayer().screenX &&
                    worldY + gp.getTileSize() > gp.getPlayer().worldY - gp.getPlayer().screenY &&
                    worldY - gp.getTileSize() < gp.getPlayer().worldY + gp.getPlayer().screenY) {
                try {
                    //draw Map transparent
                    if (tileNum !=0)
                        g2.drawImage(tile[0].image, (int)screenX, (int)screenY, gp.getTileSize(), gp.getTileSize(), null);
                        g2.drawImage(tile[tileNum].image, (int)screenX, (int)screenY, gp.getTileSize(), gp.getTileSize(), null);



                }catch (NullPointerException e){
                    System.out.println("Die ausgewählte Ressource(Bild) auf der Welt ist nicht vorhanden");
                }
            }else if (gp.getPlayer().screenX > gp.getPlayer().worldX ||
                      gp.getPlayer().screenY > gp.getPlayer().worldY ||
                      rightOffset > gp.worldWidth - gp.getPlayer().worldX ||
                      bottomOffset > gp.worldHeight - gp.getPlayer().worldY){

                if (tileNum !=0)
                    g2.drawImage(tile[0].image, (int)screenX, (int)screenY, gp.getTileSize(), gp.getTileSize(), null);
                g2.drawImage(tile[tileNum].image, (int)screenX, (int)screenY, gp.getTileSize(), gp.getTileSize(), null);
            }
            worldCol++;
            if (worldCol == gp.getMaxWorldCol()) {
                worldCol = 0;
                worldRow++;
            }



        }
    }


}
