package at.htblakaindorf.AHIF18;

import at.htblakaindorf.AHIF18.Ground.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    private ArrayList<Buildingname> buildingpath;
    private int amount_of_items_in_UI;
    private int amount_of_section_in_Bottom_UI;
    private int amount_of_ready_items_in_UI;
    private int menuetilesize;
    private int height_of_Bottom_UI;
    private int height_of_Bottomsection_UI;
    private int height_of_Top_UI;
    private int middle_object;
    private int middle_object_Bottom_menue;
    private int margin_from_Bottom_Menu;
    private int margin_from_Bottomsection_Menu;
    private Color bottomMenu = Color.darkGray;
    private Color topBar = Color.darkGray;
    private Color uiElements = Color.gray;
    private Color sectionElements = Color.gray;

    Tile[] tileMenue = new Tile[100];

    public UI(GamePanel gp) {
        this.gp = gp;
        buildingpath = new ArrayList<Buildingname>();
        amount_of_items_in_UI = 10;
        amount_of_section_in_Bottom_UI = 6; // max 11
        height_of_Bottom_UI = gp.getScreenHeight() / 6;
        height_of_Bottomsection_UI = gp.getScreenHeight() / 4;
        height_of_Top_UI = gp.getScreenHeight() / 16;
        menuetilesize = height_of_Bottom_UI / 2;
        height_of_Bottomsection_UI = menuetilesize / 2;
        middle_object = (gp.getScreenWidth() / amount_of_items_in_UI) / 2 - menuetilesize / 2;
        middle_object_Bottom_menue = (gp.getScreenWidth() / amount_of_section_in_Bottom_UI) / 2 - menuetilesize / 2;
        margin_from_Bottom_Menu = height_of_Bottom_UI / 8;
        margin_from_Bottomsection_Menu = height_of_Bottomsection_UI / 6;

        //Elements Setzen in UI Max: amount_of_items_in_UI //Rest wird außerhalb gerendert
        buildingpath.add(new Buildingname(10,"/res/building/Villager.png", "Villager",this));
        buildingpath.add(new Buildingname(11,"/res/building/blacksmith.png", "Smith",this));
        buildingpath.add(new Buildingname(12,"/res/building/Church.png", "Church",this));
        buildingpath.add(new Buildingname(13,"/res/building/fisher.png", "Fisher",this));
        buildingpath.add(new Buildingname(14,"/res/building/Windmill.png", "Windmill",this));
        buildingpath.add(new Buildingname(15,"/res/building/coal-mine.png", "Coal Mine",this));
        buildingpath.add(new Buildingname(16,"/res/building/iron-mine.png", "Irone Mine",this));
        buildingpath.add(new Buildingname(10,"/res/building/Villager.png", "Villager",this));
        buildingpath.add(new Buildingname(10,"/res/building/Villager.png", "Villager",this));
        buildingpath.add(new Buildingname(10,"/res/building/Villager.png", "Villager",this));
        buildingpath.add(new Buildingname(10,"/res/building/Villager.png", "Villager",this));
        buildingpath.add(new Buildingname(10,"/res/building/Villager.png", "Villager",this));

        setUiImages(buildingpath);

    }

    public void setUiImages(ArrayList<Buildingname> buildingpath){

            try {
                for (int i = 0;i < amount_of_ready_items_in_UI;i++) {
                    setUIimage(i, buildingpath.get(i).getId(),buildingpath.get(i).getPath(), buildingpath.get(i).getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public int calculateMenuePos(int i) {//Synchron zu amount_of_items_in_UI-1

        if (amount_of_items_in_UI == 0)
            return middle_object;

        return (gp.getScreenWidth() / amount_of_items_in_UI) * i + middle_object; // sehr sehr leichte zu wenig Pixel vermutlich wegen int zwischen (2-4) pixel
    }

    public int calculateBottomMenuePos(int i) {//Synchron zu amount_of_items_in_Bottom_UI-1
        if (amount_of_section_in_Bottom_UI == 1)
            return middle_object_Bottom_menue; // sehr sehr leichte zu wenig Pixel vermutlich wegen int zwischen (2-4) pixel
        return (((gp.getScreenWidth() / amount_of_section_in_Bottom_UI) * i) / (amount_of_section_in_Bottom_UI / (amount_of_section_in_Bottom_UI / 2)))
                + gp.getScreenWidth() / 2 - ((((gp.getScreenWidth() / amount_of_section_in_Bottom_UI) * amount_of_section_in_Bottom_UI) / (amount_of_section_in_Bottom_UI / (amount_of_section_in_Bottom_UI / 2))) -
                ((gp.getScreenWidth() / amount_of_section_in_Bottom_UI) / (amount_of_section_in_Bottom_UI / (amount_of_section_in_Bottom_UI / 2))) + menuetilesize) / 2; // Meine Zwangsstörungen kicken diese scheiß 3
    }

    private void setUIimage(int index, int id, String imagePath, String name) throws IOException {
        UtilityTool uTool = new UtilityTool();
        try {
            tileMenue[index] = new Tile();
            tileMenue[index].image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            tileMenue[index].image = uTool.scaleImage(tileMenue[index].image, menuetilesize, menuetilesize);
            tileMenue[index].setName(name);
            tileMenue[index].setId(id);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,
                    "Bild wurde nicht gefunden oder falsch geschrieben!!\n Fehlerquelle: " + imagePath , "Laden fehlgeschlagen",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showBottomMenu(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(bottomMenu);
        //Bottom MenuBar
        g2.fillRoundRect(x, y, width, height, 0, 0);

        //Hintergrund Farbe von UI Elemente
        g2.setColor(uiElements);

        //Hintergrund setzten
        g2.fillRect(calculateMenuePos(0), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(1), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(2), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(3), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(4), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(5), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(6), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(7), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(8), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(9), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(10), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(11), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, menuetilesize, menuetilesize);
        g2.fillRect(calculateMenuePos(12), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, menuetilesize, menuetilesize);

        //UI Elemente setzten
        g2.drawImage(tileMenue[0].image, calculateMenuePos(0), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, null);
        for (int i = 0; i < amount_of_ready_items_in_UI; i++) {
            g2.drawImage(tileMenue[i].image, calculateMenuePos(i), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, null);
        }
    }

    public void showBottomMenufromBottomMenue(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(sectionElements);

        g2.fillRoundRect(calculateBottomMenuePos(0), y, width, height, 0, 0);
        g2.fillRoundRect(calculateBottomMenuePos(1), y, width, height, 0, 0);
        g2.fillRoundRect(calculateBottomMenuePos(2), y, width, height, 0, 0);
        g2.fillRoundRect(calculateBottomMenuePos(3), y, width, height, 0, 0);
        g2.fillRoundRect(calculateBottomMenuePos(4), y, width, height, 0, 0);
        g2.fillRoundRect(calculateBottomMenuePos(5), y, width, height, 0, 0);
    }

    public void showTopBar(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(topBar);
        g2.fillRoundRect(x, y, width, height, 0, 0);

        /*g2.drawImage(tile[10].image,   gp.getUi().getAmount_of_items_in_UI(),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image,gp.getUi().getAmount_of_items_in_UI()*2, gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image,gp.getUi().getAmount_of_items_in_UI()*3, gp.getScreenHeight() - (gp.getScreenHeight()/8), null);*/

    }
    public void updateUiElements(){

    }


    public void draw(Graphics2D g2) {

        showBottomMenu(g2, 0, gp.getScreenHeight() - height_of_Bottom_UI, gp.getScreenWidth(), height_of_Bottom_UI);
        showBottomMenufromBottomMenue(g2, 0, gp.getScreenHeight() - height_of_Bottomsection_UI - margin_from_Bottomsection_Menu, menuetilesize, height_of_Bottomsection_UI);
        showTopBar(g2, 0, 0, gp.getScreenWidth(), height_of_Top_UI);

       /* g2.setColor(Color.green);
        g2.fillRoundRect(0, gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, gp.getScreenWidth(), menuetilesize , 0, 0);
        */
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

    public int getHeight_of_Bottomsection_UI() {
        return height_of_Bottomsection_UI;
    }

    public int getMargin_from_Bottom_Menu() {
        return margin_from_Bottom_Menu;
    }

    public int getMargin_from_Bottomsection_Menu() {
        return margin_from_Bottomsection_Menu;
    }

    public Tile getTile (int pos){
        return tileMenue[pos];

    }

    public void setAmount_of_items_in_UI(int amount_of_items_in_UI) {
        this.amount_of_items_in_UI = amount_of_items_in_UI;
    }

    public void setAmount_of_section_in_Bottom_UI(int amount_of_section_in_Bottom_UI) {
        this.amount_of_section_in_Bottom_UI = amount_of_section_in_Bottom_UI;
    }

    public void addAmount_of_ready_items_in_UI() {
        this.amount_of_ready_items_in_UI++;
    }

    public int getAmount_of_section_in_Bottom_UI() {
        return amount_of_section_in_Bottom_UI;
    }

    public int getAmount_of_ready_items_in_UI() {
        return amount_of_ready_items_in_UI;
    }
}

