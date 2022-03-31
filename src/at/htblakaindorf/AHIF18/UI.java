package at.htblakaindorf.AHIF18;

import at.htblakaindorf.AHIF18.Ground.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class UI {
    GamePanel gp;
    private int amount_of_items_in_UI;
    private int amount_of_items_in_Bottom_UI;
    private int menuetilesize;
    private int height_of_Bottom_UI;
    private int height_of_Bottomsection_UI;
    private int height_of_Top_UI;
    private int middle_object;
    private int middle_object_Bottom_menue;

    Tile[] tile = new Tile[100];

    public UI(GamePanel gp){
        this.gp = gp;
        amount_of_items_in_UI = 10;
        amount_of_items_in_Bottom_UI = 6; // max 11
        height_of_Bottom_UI = gp.getScreenHeight()/6;
        height_of_Bottomsection_UI = gp.getScreenHeight()/4;
        height_of_Top_UI = gp.getScreenHeight()/16;
        menuetilesize = height_of_Bottom_UI/2;
        height_of_Bottomsection_UI = menuetilesize/2;
        middle_object = (gp.getScreenWidth()/ amount_of_items_in_UI)/2 - menuetilesize/2;
        middle_object_Bottom_menue = (gp.getScreenWidth()/ amount_of_items_in_Bottom_UI)/2 - menuetilesize/2;
        try {

            //setUIimages(10, "/res/tiles/ground/grass.png");
            setUIimages(10, "/res/building/Villager.png");
            setUIimages(11, "/res/building/blacksmith.png");
            setUIimages(12, "/res/building/Church.png");
            setUIimages(13, "/res/building/fisher.png");
            setUIimages(14, "/res/building/Windmill.png");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Fehler beim Einlesen der Bilder\n" , "Falsche Ressource",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public int calculateMenuePos(int i){//Synchron zu amount_of_items_in_UI-1

        if(amount_of_items_in_UI == 0)
            return middle_object;

        return (gp.getScreenWidth()/ amount_of_items_in_UI)*i + middle_object; // sehr sehr leichte zu wenig Pixel vermutlich wegen int zwischen (2-4) pixel
    }
    public int calculateBottomMenuePos(int i){//Synchron zu amount_of_items_in_Bottom_UI-1
        if(amount_of_items_in_Bottom_UI == 1)
            return middle_object_Bottom_menue; // sehr sehr leichte zu wenig Pixel vermutlich wegen int zwischen (2-4) pixel
        return (((gp.getScreenWidth()/amount_of_items_in_Bottom_UI)*i)/(amount_of_items_in_Bottom_UI/(amount_of_items_in_Bottom_UI/2)))
                + gp.getScreenWidth()/2 - ((((gp.getScreenWidth()/amount_of_items_in_Bottom_UI)*amount_of_items_in_Bottom_UI)/(amount_of_items_in_Bottom_UI/(amount_of_items_in_Bottom_UI/2))) -
                        (((gp.getScreenWidth()/amount_of_items_in_Bottom_UI)*1)/(amount_of_items_in_Bottom_UI/(amount_of_items_in_Bottom_UI/2))) + menuetilesize)/2;
    }

    private void setUIimages(int index, String imagePath) throws IOException {
        UtilityTool uTool = new UtilityTool();

            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream(imagePath ));
            tile[index].image = uTool.scaleImage(tile[index].image, menuetilesize, menuetilesize);
    }
    public void showBottomMenu(Graphics2D g2, int x, int y, int width, int height){
        g2.setColor(Color.darkGray);
        //Bottom MenuBar
        g2.fillRoundRect(x, y, width, height, 0, 0);

        //Hintergrund Farbe
        g2.setColor(Color.gray);

        //Hintergrund
        g2.fillRect(calculateMenuePos(0), gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(1), gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(2), gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(3), gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(4), gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(5), gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(6), gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(7), gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(8), gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(9), gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(10), gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(11), gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(12), gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, menuetilesize, menuetilesize);

        //UI Elemente
        g2.drawImage(tile[10].image, calculateMenuePos(0),   gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, null);
        g2.drawImage(tile[11].image, calculateMenuePos(1),   gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, null);
        g2.drawImage(tile[12].image, calculateMenuePos(2),   gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, null);
        g2.drawImage(tile[13].image, calculateMenuePos(3),   gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, null);
        g2.drawImage(tile[14].image, calculateMenuePos(4),   gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, null);
        g2.drawImage(tile[10].image, calculateMenuePos(5),   gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, null);
        g2.drawImage(tile[10].image, calculateMenuePos(6),   gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, null);
        g2.drawImage(tile[10].image, calculateMenuePos(7),   gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, null);
        g2.drawImage(tile[10].image, calculateMenuePos(8),   gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, null);
        g2.drawImage(tile[10].image, calculateMenuePos(9),   gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, null);
        g2.drawImage(tile[10].image, calculateMenuePos(10),   gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, null);
        g2.drawImage(tile[10].image, calculateMenuePos(11),   gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, null);
        g2.drawImage(tile[10].image, calculateMenuePos(12),   gp.getScreenHeight() - height_of_Bottom_UI + height_of_Bottom_UI/8, null);
    }
    public void showBottomMenufromBottomMenue(Graphics2D g2, int x, int y, int width, int height){
        g2.setColor(Color.blue);
        g2.fillRoundRect(calculateBottomMenuePos(0), y, width, height, 0, 0);
        g2.setColor(Color.green);
        g2.fillRoundRect(calculateBottomMenuePos(1), y, width, height, 0, 0);
        g2.setColor(Color.blue);
        g2.fillRoundRect(calculateBottomMenuePos(2), y, width, height, 0, 0);
        g2.setColor(Color.green);
        g2.fillRoundRect(calculateBottomMenuePos(3), y, width, height, 0, 0);
        g2.setColor(Color.blue);
        g2.fillRoundRect(calculateBottomMenuePos(4), y, width, height, 0, 0);
        g2.setColor(Color.green);
        g2.fillRoundRect(calculateBottomMenuePos(5), y, width, height, 0, 0);
    }
    public void createTopMenu(Graphics2D g2, int x, int y, int width, int height){
        g2.setColor(Color.GRAY);
        g2.fillRoundRect(x, y, width, height, 0, 0);

        /*g2.drawImage(tile[10].image,   gp.getUi().getAmount_of_items_in_UI(),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image,gp.getUi().getAmount_of_items_in_UI()*2, gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image,gp.getUi().getAmount_of_items_in_UI()*3, gp.getScreenHeight() - (gp.getScreenHeight()/8), null);*/

    }


    public void draw(Graphics2D g2){
        showBottomMenu(g2,0,gp.getScreenHeight()-height_of_Bottom_UI, gp.getScreenWidth(), height_of_Bottom_UI);
        showBottomMenufromBottomMenue(g2,0,gp.getScreenHeight() - height_of_Bottomsection_UI - height_of_Bottomsection_UI/6 , menuetilesize, height_of_Bottomsection_UI);
        createTopMenu(g2, 0, 0, gp.getScreenWidth(),  getHeight_of_Top_UI());
    }

    public int getAmount_of_items_in_UI() {
        return amount_of_items_in_UI;
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
