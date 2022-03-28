package at.htblakaindorf.AHIF18;

import at.htblakaindorf.AHIF18.Ground.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI {
    GamePanel gp;
    BufferedImage buildings;
    Tile[] tile = new Tile[100];

    public UI(GamePanel gp){
        this.gp = gp;

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
    public void draw(Graphics2D g2){
      //  g2.drawImage();
        g2.drawImage(tile[10].image,gp.getScreenWidth()/4, gp.getScreenHeight() - gp.tileSize*2, null);
    }
}
