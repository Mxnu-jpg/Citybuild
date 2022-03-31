package at.htblakaindorf.AHIF18;

import at.htblakaindorf.AHIF18.Ground.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI {
    GamePanel gp;
    BufferedImage buildings;
    private int amount_of_items_in_Bottom_UI;
    private int menuetilesize;
    private int height_of_Bottom_UI;
    private int height_of_Top_UI;
    private int middle_object;

    Tile[] tile = new Tile[100];

    public UI(GamePanel gp){
        this.gp = gp;
        amount_of_items_in_Bottom_UI = 16;
        height_of_Bottom_UI = gp.getScreenHeight()/6;
        height_of_Top_UI = gp.getScreenHeight()/16;
        menuetilesize = height_of_Bottom_UI/2;
        middle_object = (gp.getScreenWidth()/amount_of_items_in_Bottom_UI)/2 - menuetilesize/2;

        try {

            //setUIimages(10, "/res/tiles/ground/grass.png");
            setUIimages(10, "/res/building/building1.png");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Fehler beim Einlesen der Bilder\n" , "Falsche Ressource",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public int calculatemenuepos(int i){//Synchron zu amount_of_items_in_UI-1
        //System.out.println(first_object + menuetilesize);
        if(amount_of_items_in_Bottom_UI == 1)
            return middle_object;

        //erstes Und letzes Element gleicher Abstand von Rand
        /*if(i == 0)
            return middle_object;
        if(i == amount_of_items_in_Bottom_UI-1)
            return (gp.getScreenWidth() - (calculatemenuepos(0) + menuetilesize));
        */
        return (gp.getScreenWidth()/amount_of_items_in_Bottom_UI)*i + middle_object;
        //return (((gp.getScreenWidth() - (gp.getUi().calculatemenuepos(0) + gp.getUi().getMenuetilesize())) - (gp.getUi().calculatemenuepos(0) + gp.getUi().getMenuetilesize() + ))/amount_of_items_in_Bottom_UI)*i + first_object ;

        //return (((gp.getScreenWidth() -  ((gp.getScreenWidth()/amount_of_items_in_Bottom_UI)/2) + menuetilesize)/amount_of_items_in_Bottom_UI)*i) + calculatemenuepos(0) + menuetilesize/2; // letzter Wert zu hoch != jeder andere flasche berehcnung
    }

    private void setUIimages(int index, String imagePath) throws IOException {
        UtilityTool uTool = new UtilityTool();

            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream(imagePath ));
            tile[index].image = uTool.scaleImage(tile[index].image, menuetilesize, menuetilesize);
    }
    public void createBottomMenu(Graphics2D g2, int x, int y, int width, int height){
        g2.setColor(Color.GRAY);
        g2.fillRoundRect(x, y, width, height, 0, 0);

        g2.drawImage(tile[10].image, calculatemenuepos(0),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image, calculatemenuepos(1),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image, calculatemenuepos(2),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image, calculatemenuepos(3),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image, calculatemenuepos(4),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image, calculatemenuepos(5),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image, calculatemenuepos(6),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image, calculatemenuepos(7),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image, calculatemenuepos(8),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image, calculatemenuepos(9),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image, calculatemenuepos(10),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image, calculatemenuepos(11),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image, calculatemenuepos(12),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image, calculatemenuepos(13),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image, calculatemenuepos(14),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image, calculatemenuepos(15),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);



    }
    public void createTopMenu(Graphics2D g2, int x, int y, int width, int height){
        g2.setColor(Color.GRAY);
        g2.fillRoundRect(x, y, width, height, 0, 0);

        /*g2.drawImage(tile[10].image,   gp.getUi().getAmount_of_items_in_UI(),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image,gp.getUi().getAmount_of_items_in_UI()*2, gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image,gp.getUi().getAmount_of_items_in_UI()*3, gp.getScreenHeight() - (gp.getScreenHeight()/8), null);*/

    }

    public void draw(Graphics2D g2){
        createBottomMenu(g2,0,gp.getScreenHeight()-(gp.getUi().getHeight_of_Bottom_UI()), gp.getScreenWidth(), getHeight_of_Bottom_UI());
        createTopMenu(g2, 0, 0, gp.getScreenWidth(),  getHeight_of_Top_UI());
    }

    public int getAmount_of_items_in_Bottom_UI() {
        return amount_of_items_in_Bottom_UI;
    }

    public int getHeight_of_Bottom_UI() {
        return height_of_Bottom_UI;
    }

    public int getHeight_of_Top_UI() {
        return height_of_Top_UI;
    }

    public int getMenuetilesize() {
        return menuetilesize;
    }
}
