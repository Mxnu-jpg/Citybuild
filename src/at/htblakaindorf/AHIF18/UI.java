package at.htblakaindorf.AHIF18;

import at.htblakaindorf.AHIF18.Ground.Tile;
import at.htblakaindorf.AHIF18.db.CityBuildDataBase;

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
    private int amount_of_top_menue_items;
    private int amount_of_top_menue_ressource_items;
    private int menuetilesize;
    private int height_of_Bottom_UI;
    private int height_of_Bottomsection_UI;
    private int height_of_Top_UI;
    private int size_of_Top_UI_Element;
    private int middle_object;
    private int middle_object_Bottom_menue;
    private int margin_from_Bottom_Menu;
    private int margin_from_Bottomsection_Menu;
    private int margin_from_Top_Menue;
    private int ressource_top_box;
    private int right_top_box;
    private Color bottomMenu = Color.darkGray;
    private Color topBar = Color.darkGray;
    private Color uiElements = Color.gray;
    private Color sectionElements = Color.gray;

    Tile[] tileBottomMenue = new Tile[100];
    Tile[] tileTopMenue = new Tile[100];

    public UI(GamePanel gp) {
        this.gp = gp;
        buildingpath = new ArrayList<Buildingname>();
        amount_of_items_in_UI = 10;
        amount_of_section_in_Bottom_UI = 6; // max 11
        amount_of_top_menue_items = 2;
        amount_of_top_menue_ressource_items = 5;
        height_of_Bottom_UI = gp.getScreenHeight() / 6;
        height_of_Bottomsection_UI = gp.getScreenHeight() / 4;
        height_of_Top_UI = gp.getScreenHeight() / 10;
        ressource_top_box = gp.getScreenWidth()-(gp.getScreenWidth()/8);
        right_top_box = gp.getScreenWidth()/8;
        menuetilesize = height_of_Bottom_UI / 2;
        size_of_Top_UI_Element = height_of_Top_UI/2;
        height_of_Bottomsection_UI = menuetilesize / 2;
        middle_object = (gp.getScreenWidth() / amount_of_items_in_UI) / 2 - menuetilesize / 2;
        middle_object_Bottom_menue = (gp.getScreenWidth() / amount_of_section_in_Bottom_UI) / 2 - menuetilesize / 2;
        margin_from_Bottom_Menu = height_of_Bottom_UI / 8;
        margin_from_Bottomsection_Menu = height_of_Bottomsection_UI / 6;
        margin_from_Top_Menue = height_of_Top_UI/4;

        //Elements Setzen in UI Max: amount_of_items_in_UI //Rest wird außerhalb gerendert
        CityBuildDataBase.getInstance().setBuildings(this);
        /*buildingpath.add(new Buildingname(10,"/res/building/House1.png", "Villager",this));
        buildingpath.add(new Buildingname(11, "/building/Blacksmith.png", "Smith",this));
        buildingpath.add(new Buildingname(12,"/res/building/Church.png", "Church",this));
        buildingpath.add(new Buildingname(13, "/building/Fisher.png", "Fisher",this));
        buildingpath.add(new Buildingname(14,"/res/building/Windmill.png", "Windmill",this));
        buildingpath.add(new Buildingname(15, "/building/Coal Mine.png", "Coal Mine",this));
        buildingpath.add(new Buildingname(16, "/building/Iron Mine.png", "Irone Mine",this));
        buildingpath.add(new Buildingname(10,"/res/building/House1.png", "Villager",this));
        buildingpath.add(new Buildingname(10,"/res/building/House1.png", "Villager",this));
        buildingpath.add(new Buildingname(10,"/res/building/House1.png", "Villager",this));
        buildingpath.add(new Buildingname(10,"/res/building/House1.png", "Villager",this));
        buildingpath.add(new Buildingname(10,"/res/building/House1.png", "Villager",this));*/

        setUiImages(CityBuildDataBase.getInstance().getBuildings());

    }

    public void setUiImages(ArrayList<Buildingname> buildingpath){

            try {
                for (int i = 0;i < amount_of_ready_items_in_UI;i++) {
                    setBottomUIimage(i, buildingpath.get(i).getId(),buildingpath.get(i).getPath(), buildingpath.get(i).getName());
                    setTopUIimage(i, buildingpath.get(i).getId(), buildingpath.get(i).getPath(), buildingpath.get(i).getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public int calculateRightTopMenuPos(int i){

        return gp.getScreenWidth() -  (right_top_box / amount_of_top_menue_items) * (i+1);
    }
    public int calculateRessourceTopMenuPos(int i){

        return  ((ressource_top_box / amount_of_top_menue_ressource_items) * i)
                - (gp.getScreenWidth()/amount_of_top_menue_ressource_items)/2;
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

    private void setBottomUIimage(int index, int id, String imagePath, String name) throws IOException {
        UtilityTool uTool = new UtilityTool();
        try {
            tileBottomMenue[index] = new Tile();
            tileBottomMenue[index].image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            tileBottomMenue[index].image = uTool.scaleImage(tileBottomMenue[index].image, menuetilesize, menuetilesize);
            tileBottomMenue[index].setName(name);
            tileBottomMenue[index].setId(id);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,
                    "Bild wurde nicht gefunden oder falsch geschrieben!!\n Fehlerquelle: " + imagePath , "Laden fehlgeschlagen",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void setTopUIimage(int index, int id, String imagePath, String name) throws IOException {
        UtilityTool uTool = new UtilityTool();
        try {
            tileTopMenue[index] = new Tile();
            tileTopMenue[index].image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            tileTopMenue[index].image = uTool.scaleImage(tileTopMenue[index].image, size_of_Top_UI_Element, size_of_Top_UI_Element);
            tileTopMenue[index].setName(name);
            tileTopMenue[index].setId(id);
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
        g2.drawImage(tileBottomMenue[0].image, calculateMenuePos(0), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, null);
        for (int i = 0; i < amount_of_ready_items_in_UI; i++) {
            g2.drawImage(tileBottomMenue[i].image, calculateMenuePos(i), gp.getScreenHeight() - height_of_Bottom_UI + margin_from_Bottom_Menu, null);
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

        //UI Elemente setzten Optionen, Building remove
        g2.drawImage(tileTopMenue[0].image, calculateRightTopMenuPos(0), margin_from_Top_Menue, null);
        g2.drawImage(tileTopMenue[0].image, calculateRightTopMenuPos(1), margin_from_Top_Menue, null);

        //UI Elemente setzen //Ressourcen
        g2.drawImage(tileTopMenue[9].image, calculateRessourceTopMenuPos(1), margin_from_Top_Menue, null);
        g2.drawImage(tileTopMenue[11].image, calculateRessourceTopMenuPos(2), margin_from_Top_Menue, null);
        g2.drawImage(tileTopMenue[0].image, calculateRessourceTopMenuPos(3), margin_from_Top_Menue, null);
        g2.drawImage(tileTopMenue[10].image, calculateRessourceTopMenuPos(4), margin_from_Top_Menue, null);
        g2.drawImage(tileTopMenue[0].image, calculateRessourceTopMenuPos(5), margin_from_Top_Menue, null);

        //Fare des Coutner setzen
        g2.setColor(Color.white);
        //Counter der Ressourcen setzen
        //gibt dir die Pixel zurück wie lange der String mit dem eingelesenen Text ist:
        //g2.getFontMetrics().stringWidth(String.valueOf(gp.getPlayer().getIron())
        g2.drawString(String.valueOf(gp.getPlayer().getFood()), calculateRessourceTopMenuPos(1) - g2.getFontMetrics().stringWidth(String.valueOf(gp.getPlayer().getFood())), margin_from_Top_Menue + size_of_Top_UI_Element/2 + size_of_Top_UI_Element/4);
        g2.drawString(String.valueOf(gp.getPlayer().getWood()), calculateRessourceTopMenuPos(2) - g2.getFontMetrics().stringWidth(String.valueOf(gp.getPlayer().getWood())), margin_from_Top_Menue + size_of_Top_UI_Element/2 + size_of_Top_UI_Element/4);
        g2.drawString(String.valueOf(gp.getPlayer().getStone()), calculateRessourceTopMenuPos(3) - g2.getFontMetrics().stringWidth(String.valueOf(gp.getPlayer().getStone())), margin_from_Top_Menue + size_of_Top_UI_Element/2 + size_of_Top_UI_Element/4);
        g2.drawString(String.valueOf(gp.getPlayer().getIron()), calculateRessourceTopMenuPos(4) - g2.getFontMetrics().stringWidth(String.valueOf(gp.getPlayer().getIron())), margin_from_Top_Menue + size_of_Top_UI_Element/2 + size_of_Top_UI_Element/4);
        g2.drawString(String.valueOf(gp.getPlayer().getGold()), calculateRessourceTopMenuPos(5) - g2.getFontMetrics().stringWidth(String.valueOf(gp.getPlayer().getGold())), margin_from_Top_Menue + size_of_Top_UI_Element/2 + size_of_Top_UI_Element/4);

        /*g2.drawImage(tile[10].image,   gp.getUi().getAmount_of_items_in_UI(),   gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image,gp.getUi().getAmount_of_items_in_UI()*2, gp.getScreenHeight() - (gp.getScreenHeight()/8), null);
        g2.drawImage(tile[10].image,gp.getUi().getAmount_of_items_in_UI()*3, gp.getScreenHeight() - (gp.getScreenHeight()/8), null);*/
    }
    public void updateUiElements(){
        //section
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
        return tileBottomMenue[pos];

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

    public int getAmount_of_top_menue_items() {
        return amount_of_top_menue_items;
    }

    public int getAmount_of_top_menue_ressource_items() {
        return amount_of_top_menue_ressource_items;
    }

    public int getSize_of_Top_UI_Element() {
        return size_of_Top_UI_Element;
    }

    public int getMargin_from_Top_Menue() {
        return margin_from_Top_Menue;
    }

    public int getRessource_top_box() {
        return ressource_top_box;
    }

    public int getRight_top_box() {
        return right_top_box;
    }
}

