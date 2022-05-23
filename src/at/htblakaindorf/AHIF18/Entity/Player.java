package at.htblakaindorf.AHIF18.Entity;

import at.htblakaindorf.AHIF18.GamePanel;
import at.htblakaindorf.AHIF18.Ground.Buildingobjects.Building;
import at.htblakaindorf.AHIF18.Ground.Tile;
import at.htblakaindorf.AHIF18.KeyHandler;
import at.htblakaindorf.AHIF18.db.CityBuildDataBase;
import com.sun.security.jgss.GSSUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler kH;
    CityBuildDataBase db;
    Map<Integer, Integer> buildingCounter;
    Map<Integer, int[]> buildingEarnings;

    public final int screenX;
    public final int screenY;
    private int buildingID;
    private int food = 100;
    private int wood = 1000;
    private int stone = 1000;
    private int iron = 10;
    private int gold = 10000;


    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.kH = keyH;
        db = CityBuildDataBase.getInstance();
        buildingCounter = new HashMap<>();
        buildingEarnings = new HashMap<>();

        screenX = gp.getScreenWidth() / 2 - (gp.getTileSize() / 2);
        screenY = gp.getScreenHeight() / 2 - (gp.getTileSize() / 2);
        setDefaultValues();
    }

    public void setDefaultValues() {
        //spawn
        worldX = gp.getTileSize() * 25;
        worldY = gp.getTileSize() * 25;
        speed = 4;
    }

    public void update() {
        if (kH.isUp() == true) {
            int y = screenY - gp.getUi().getHeight_of_Top_UI();
            if (worldY > y)
                worldY -= speed;
            System.out.println(screenY + " - " + gp.getUi().getHeight_of_Top_UI());
            System.out.println(worldY);
            System.out.println("WorldY:" + worldY);
            System.out.println("WorldX" + worldX);
            System.out.println("ScreenX:" + screenX);
            System.out.println("ScreenY:" + screenY);
        }
        if (kH.isDown() == true) {
            if (worldY < gp.getWorldHeight() - screenY + gp.getUi().getHeight_of_Bottom_UI())//not perfect
                worldY += speed;
            System.out.println(worldY);
            System.out.println(worldX);
        }
        if (kH.isLeft() == true) {
            if (worldX != screenX)
                worldX -= speed;
            System.out.println(worldY);
            System.out.println(worldX);
        }
        if (kH.isRight() == true) {
            if (worldX <= gp.getWorldWidth() - screenX)//not perfect
                worldX += speed;
            System.out.println(worldY);
            System.out.println(worldX);
        }
        if (kH.isMouseClicked() == true) {
            kH.clearMouseClick();
            //Der Tile in der Mitte hat scale * originaltilesize das muss man hinzurechenn mouse = tilesize + screen
            //UI Measurements
            System.out.println("MX:" + kH.getPointerPosition().getX());
            System.out.println("MY:" + kH.getPointerPosition().getY());
            System.out.println("SX:" + (screenX * 2 + gp.getTileSize()));
            System.out.println("SY:" + (screenY * 2 + gp.getTileSize()));
            System.out.println("AUY:" + (gp.getUi().calculateMenuePos(0) + gp.getUi().getMenuetilesize()));
            System.out.println("EUY:" + (gp.getScreenWidth() - (gp.getUi().calculateMenuePos(0) + gp.getUi().getMenuetilesize())));
            System.out.println("SumY:" + ((gp.getScreenWidth() - (gp.getUi().calculateMenuePos(0) + gp.getUi().getMenuetilesize())) - (gp.getUi().calculateMenuePos(0) + gp.getUi().getMenuetilesize())));
            System.out.println("diff:" + (gp.getUi().calculateMenuePos(1) - (gp.getUi().calculateMenuePos(0) + gp.getUi().getMenuetilesize())));
            //OnClick Map
            if (kH.getPointerPosition().getY() >= gp.getUi().getHeight_of_Top_UI() && kH.getPointerPosition().getY() <= gp.getScreenHeight() - gp.getUi().getHeight_of_Bottom_UI()) {
                if (kH.isMenueClicked()) {
                    buildElementonMap(gp.getUi().getTile(getBuildingID()));
                    kH.setMenueClicked(false);
                }
                if (kH.isRemoveBuilding()) {
                    removeElementMap();
                    kH.setRemoveBuilding(false);
                }
            }

            //Top Menu clicked
            if (kH.getPointerPosition().getY() <= gp.getUi().getHeight_of_Top_UI() && kH.getPointerPosition().getY() >= 0) {
                topMenuClicked();
            }
            //Bottom Menu clicked
            if (kH.getPointerPosition().getY() >= gp.getScreenHeight() - gp.getUi().getHeight_of_Bottom_UI() && kH.getPointerPosition().getY() <= gp.getScreenHeight()) {
                bottomMenuClicked();
            }
        }

        if (kH.isInfo() == true) {
            kH.setSysinfo(false);
            for (Tile tile : CityBuildDataBase.getInstance().getMapList()) {
                if (tile != null) {
                    System.out.println(tile.getName());
                }
            }
        }
    }

    public void topMenuClicked() {
        System.out.println("Top Menue");
        if (kH.getPointerPosition().getY() >= gp.getUi().getMargin_from_Top_Menue() && kH.getPointerPosition().getY() <= gp.getUi().getHeight_of_Top_UI() - gp.getUi().getMargin_from_Top_Menue())
            optionsLineClicked();
    }

    public void bottomMenuClicked() {
        //Bottom Menu clicked
        if (kH.getPointerPosition().getY() >= gp.getScreenHeight() - gp.getUi().getHeight_of_Bottom_UI() + gp.getUi().getMargin_from_Bottom_Menu() &&
                kH.getPointerPosition().getY() <= gp.getScreenHeight() - gp.getUi().getHeight_of_Bottomsection_UI() - gp.getUi().getMargin_from_Bottomsection_Menu() - gp.getUi().getMargin_from_Bottom_Menu()) {
            if (kH.isMenueClicked())
                kH.setMenueClicked(false);
            else {
                kH.setMenueClicked(true);
                elementsLineClicked();
            }
        }
        if (kH.getPointerPosition().getY() >= gp.getScreenHeight() - gp.getUi().getHeight_of_Bottomsection_UI() - gp.getUi().getMargin_from_Bottomsection_Menu() && kH.getPointerPosition().getY() <= gp.getScreenHeight() - gp.getUi().getMargin_from_Bottomsection_Menu())
            sectionLineClicked();
    }

    private void sectionLineClicked() {
        for (int i = 0; i < gp.getUi().getAmount_of_items_in_UI(); i++)
            if (kH.getPointerPosition().getX() <= gp.getUi().calculateBottomMenuePos(i) + gp.getUi().getMenuetilesize() && kH.getPointerPosition().getX() >= gp.getUi().calculateBottomMenuePos(i)) {
                updateUiElements(i);
                System.out.println("Section clicked, Section: " + (i + 1) + ", Name: ");
            }
    }

    private void elementsLineClicked() {
        for (int i = 0; i < gp.getUi().getAmount_of_ready_items_in_UI(); i++) {
            if (kH.getPointerPosition().getX() <= gp.getUi().calculateMenuePos(i) + gp.getUi().getMenuetilesize() && kH.getPointerPosition().getX() >= gp.getUi().calculateMenuePos(i)) {
                System.out.println("Elements clicked, Elements: " + (i + 1) + ", Name: " + gp.getUi().getTile(i).getName());
                if (kH.isMenueClicked()) {
                    buildingID = i;
                }
            }
        }
    }

    private void optionsLineClicked() { // work
        for (int i = 0; i < gp.getUi().getAmount_of_top_menue_items(); i++) {
            if (kH.getPointerPosition().getX() <= gp.getUi().calculateRightTopMenuPos(i) + gp.getUi().getSize_of_Top_UI_Element() && kH.getPointerPosition().getX() >= gp.getUi().calculateRightTopMenuPos(i)) {
                if (i == 0) // Options
                    System.out.println("options");
                if (i == 1) { //Remove building
                    System.out.println("Removed Clicked");
                    kH.setRemoveBuilding(true);
                }
            }
        }
    }


    private void updateUiElements(int i) {

    }

    private void buildElementonMap(Tile tile) {
        int row = (int) (((worldY - screenY + kH.getPointerPosition().getY()) / gp.getTileSize()) + 1);
        int col = (int) (((worldX - screenX + kH.getPointerPosition().getX()) / gp.getTileSize()) + 1);

        System.out.println("Builded, Col:" + col);
        System.out.println("Row:" + row);
        if (col >= gp.getMaxWorldCol())
            col = gp.getMaxWorldCol();
        if (row >= gp.getMaxWorldRow())
            row = gp.getMaxWorldRow();
        if (!gp.getTileM().isObstacle(col, row)) {
            int[] cost = db.getTileById(tile.getId()).getCosts();

            if (getWood() < cost[0]) {
                JOptionPane.showMessageDialog(null, "Leider reicht der Bestand von Holz für dieses Gebäude nicht aus\n", "Zu wenig Holz", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (getStone() < cost[1]) {
                JOptionPane.showMessageDialog(null, "Leider reicht der Bestand von Steine für dieses Gebäude nicht aus\n", "Zu wenig Steine", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (getIron() < cost[2]) {
                JOptionPane.showMessageDialog(null, "Leider reicht der Bestand von Eisen für dieses Gebäude nicht aus\n", "Zu wenig Eisen", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (getGold() < cost[3]) {
                JOptionPane.showMessageDialog(null, "Leider reicht der Bestand von Gold für dieses Gebäude nicht aus\n", "Zu wenig Gold", JOptionPane.ERROR_MESSAGE);
                return;
            }

            System.out.println();
            setWood(getWood() - cost[0]);
            setStone(getStone() - cost[1]);
            setIron(getIron() - cost[2]);
            setGold(getGold() - cost[3]);
            gp.getTileM().setBuilding(col, row, tile);
        }

    }

    private void removeElementMap() {
        int row = (int) (((worldY - screenY + kH.getPointerPosition().getY()) / gp.getTileSize()) + 1);
        int col = (int) (((worldX - screenX + kH.getPointerPosition().getX()) / gp.getTileSize()) + 1);

        System.out.println("Removed, Col:" + col);
        System.out.println("Row:" + row);

        if (gp.getTileM().isBuilding(col, row)) {

            setWood(getWood() + db.getTileById(gp.getTileM().getIdFromPosition(col, row)).getCosts()[0] / 4);
            setStone(getStone() + db.getTileById(gp.getTileM().getIdFromPosition(col, row)).getCosts()[1] / 4);
            setIron(getIron() + db.getTileById(gp.getTileM().getIdFromPosition(col, row)).getCosts()[2] / 4);
            setGold(getGold() + db.getTileById(gp.getTileM().getIdFromPosition(col, row)).getCosts()[3] / 4);
            gp.getTileM().removeBuilding(col, row);
        }
    }

    public void produce() {
        ArrayList<Tile> tiles = db.getMapList();

        for (Tile tile : tiles) {
            if(buildingCounter.get(tile.getId()) == null){
                buildingCounter.put(tile.getId(), 1);
            }else{
                buildingCounter.put(tile.getId(), buildingCounter.get(tile.getId()) + 1);
            }
            buildingEarnings.put(tile.getId(), new int[]{tile.getEarnings()[0] * buildingCounter.get(tile.getId()),
                    tile.getEarnings()[1] * buildingCounter.get(tile.getId()),
                    tile.getEarnings()[2] * buildingCounter.get(tile.getId()),
                    tile.getEarnings()[3] * buildingCounter.get(tile.getId()),
                    tile.getEarnings()[4] * buildingCounter.get(tile.getId())});
        }
        Set<Integer> set = buildingEarnings.keySet();
        for (Integer integer : set) {
            System.out.println(getIron() + " - " + buildingEarnings.get(integer)[3]);
            setFood(getFood() + buildingEarnings.get(integer)[0]);
            setWood(getWood() + buildingEarnings.get(integer)[1]);
            setStone(getStone() + buildingEarnings.get(integer)[2]);
            setIron(getIron() + buildingEarnings.get(integer)[3]);
            setGold(getGold() +buildingEarnings.get(integer)[4]);
        }

    }

    public void draw(Graphics2D g2) {
        //g2.setColor(Color.black);//Black
        //g2.setColor(new Color(0f,0f,0f,0f));//new Color(0f,0f,0f,0f)


    }

    //Setter
    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    //Getter
    public int getWood() {
        return wood;
    }

    public int getGold() {
        return gold;
    }

    public int getFood() {
        return food;
    }

    public int getStone() {
        return stone;
    }

    public int getIron() {
        return iron;
    }

    public int getBuildingID() {
        return buildingID;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setStone(int stone) {
        this.stone = stone;
    }

    public void setIron(int iron) {
        this.iron = iron;
    }
}
