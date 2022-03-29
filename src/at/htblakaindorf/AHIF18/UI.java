package at.htblakaindorf.AHIF18;

import at.htblakaindorf.AHIF18.Ground.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI {
    GamePanel gp;
    BufferedImage buildings;
    private int amount_of_items_in_UI;
    private int height_of_UI;
    Tile[] tile = new Tile[100];

    public UI(GamePanel gp){
        this.gp = gp;
        amount_of_items_in_UI = gp.getScreenWidth()/8;
        height_of_UI = gp.getScreenHeight()/6;

        try {
            setUIimages(10, "/res/building/building1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUIimages(int index, String imagePath) throws IOException {
        UtilityTool uTool = new UtilityTool();

            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.getTileSize(), gp.getTileSize());
    }
    public void createInventory(Graphics2D g2,int x, int y, int width, int height){
        g2.setColor(Color.GRAY);
        g2.fillRoundRect(x, y, width, height, 0, 0);

    }
    public void draw(Graphics2D g2){
        createInventory(g2,0,gp.getScreenHeight()-(height_of_UI), gp.getScreenWidth(), height_of_UI);
        g2.drawImage(tile[10].image,   amount_of_items_in_UI, gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image,amount_of_items_in_UI*2, gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image,amount_of_items_in_UI*3, gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image,amount_of_items_in_UI*4, gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image,amount_of_items_in_UI*5, gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image,amount_of_items_in_UI*6, gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image,amount_of_items_in_UI*7, gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image,amount_of_items_in_UI*8, gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
    }
}
