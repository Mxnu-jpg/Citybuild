package at.htblakaindorf.AHIF18.Entity;

import at.htblakaindorf.AHIF18.GamePanel;
import at.htblakaindorf.AHIF18.Ground.Tile;
import at.htblakaindorf.AHIF18.KeyHandler;
import at.htblakaindorf.AHIF18.bl.IOAccess;
import at.htblakaindorf.AHIF18.db.CityBuildDataBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler kH;
    CityBuildDataBase db;
    Map<Integer, Integer> buildingCounter;
    Map<Integer, int[]> buildingEarnings;

    public final int screenX;
    public final int screenY;
    private int buildingID;
    private int food;
    private int wood;
    private int stone;
    private int iron;
    private int gold;


    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.kH = keyH;
        db = CityBuildDataBase.getInstance();

        screenX = gp.getScreenWidth() / 2 - (gp.getTileSize() / 2);
        screenY = gp.getScreenHeight() / 2 - (gp.getTileSize() / 2);
        setDefaultValues();
    }

    public void setDefaultValues() {
        //spawn
        worldX = gp.getTileSize() * 25;
        worldY = gp.getTileSize() * 25;
        speed = 10;
        String[] resources = IOAccess.loadResources();
        setFood(Integer.parseInt(resources[0]));
        setWood(Integer.parseInt(resources[1]));
        setStone(Integer.parseInt(resources[2]));
        setIron(Integer.parseInt(resources[3]));
        setGold(Integer.parseInt(resources[4]));


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
            if (worldY < gp.getWorldHeight() - screenY + gp.getUi().getHeight_of_Bottom_UI()- gp.getTileSize())
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
            if (worldX <= gp.getWorldWidth() - screenX - gp.getTileSize())
                worldX += speed;
            System.out.println(worldY);
            System.out.println(worldX);
        }
        for(int i=0;i<gp.getUi().getAmount_of_items_in_UI();i++) // nach tooltipanzige muss wenn bei neuer Sache nochmal neu angezeigt werden, tooltip wird nicht geupdated
        if((kH.getPointerPosition().getX() >= gp.getUi().calculateMenuePos(i) && gp.getUi().calculateMenuePos(i)+gp.getUi().getMenuetilesize() >= kH.getPointerPosition().getX())
                && gp.getScreenHeight() - gp.getUi().getHeight_of_Bottom_UI()+gp.getUi().getMargin_from_Bottom_Menu() <= kH.getPointerPosition().getY()
                && gp.getScreenHeight() - gp.getUi().getHeight_of_Bottom_UI()+gp.getUi().getMargin_from_Bottom_Menu()+gp.getUi().getMenuetilesize() >= kH.getPointerPosition().getY()){
            //Tooltips
            gp.setToolTipText("Costs: " + db.getTileById(gp.getUi().getTile(i).getId()).getCosts()[0] + ", "
                    + db.getTileById(gp.getUi().getTile(i).getId()).getCosts()[1] + ", "
                    + db.getTileById(gp.getUi().getTile(i).getId()).getCosts()[2] + ", " + db.getTileById(gp.getUi().getTile(i).getId()).getCosts()[3] + " Earnings: " + db.getTileById(gp.getUi().getTile(i).getId()).getEarnings()[0] + ", "
                    + db.getTileById(gp.getUi().getTile(i).getId()).getEarnings()[1] + ", "
                    + db.getTileById(gp.getUi().getTile(i).getId()).getEarnings()[2] + ", " + db.getTileById(gp.getUi().getTile(i).getId()).getEarnings()[3]);
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
            updateProtocol();
            System.out.println(gp.getUi().getMapProtocols());
        }
        IOAccess.storeResources(getFood(),
                getWood(), getStone(), getIron(), getGold());
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
                kH.setMenueClicked(true);
                elementsLineClicked();
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
                if (i == 0) { // Options
                    System.out.println("options");
                    updateProtocol();
                    System.out.println(gp.getUi().getMapProtocols());
                }
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

            if(tile.getId() == db.getIDperName("Eisenmine") && !(gp.getTileM().getIdFromPosition(col,row) == db.getIDperName("wenig Eisenerz") || gp.getTileM().getIdFromPosition(col,row) == db.getIDperName("viel Eisenerz"))){
                JOptionPane.showMessageDialog(null, "Eine Eisenmine kann nur auf Eisenerz platziert werden, nicht auf " + db.getNameperID(gp.getTileM().getIdFromPosition(col,row)), "falscher Ort", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if(tile.getId() == db.getIDperName("Kohlemine") && !(gp.getTileM().getIdFromPosition(col,row) == db.getIDperName("Kohlefeld"))){
                JOptionPane.showMessageDialog(null, "Eine Kohlemine kann nur auf einem Kohlefelder platziert werden, nicht auf " + db.getNameperID(gp.getTileM().getIdFromPosition(col,row)), "falscher Ort", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if(tile.getId() == db.getIDperName("Fischer") && !(gp.getTileM().getIdFromPosition(col,row) == db.getIDperName("Wasser") || gp.getTileM().getIdFromPosition(col,row) == db.getIDperName("Fischreichem Wasser"))){
                JOptionPane.showMessageDialog(null, "Ein Fischer kann nur auf Wasser platziert werden, nicht auf " + db.getNameperID(gp.getTileM().getIdFromPosition(col,row)), "falscher Ort", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if(tile.getId() == db.getIDperName("Steinbruch") && !(gp.getTileM().getIdFromPosition(col,row) == db.getIDperName("Stein"))){
                JOptionPane.showMessageDialog(null, "Ein Steinmetz kann nur auf Stein platziert werden, nicht auf " + db.getNameperID(gp.getTileM().getIdFromPosition(col,row)), "falscher Ort", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if(((tile.getId() >= 10 && tile.getId() <= 12)  || (tile.getId() >= 14 && tile.getId() <= 16)) && gp.getTileM().getIdFromPosition(col,row) <= 9 && gp.getTileM().getIdFromPosition(col,row) >= 3 ){
                JOptionPane.showMessageDialog(null, "Eine " + tile.getName()+ " kann nicht auf " + db.getNameperID(gp.getTileM().getIdFromPosition(col,row)) +" platziert werden.", "falscher Ort", JOptionPane.ERROR_MESSAGE);
                return;
            }

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

    public void updateProtocol(){// soll von einer Gebäudeart alle einnahmen und ausgaben + anzahl zurückgeben
        ArrayList<Tile> tiles = db.getMapList();
        String protocols = "";

        for (Tile tile : tiles) {
            buildingEarnings.put(tile.getId(), new int[]{0,0,0,0,0});
        }

        for (Tile tile : tiles) {
            if (buildingCounter.get(tile.getId()) == null) {
                buildingCounter.put(tile.getId(), 1);
            } else {
                buildingCounter.put(tile.getId(), buildingCounter.get(tile.getId()) + 1);
            }
            if(tile.getId() >= 10) {
                protocols += tile.getName() + " earnings: " + tile.getEarnings()[0] + ", " + tile.getEarnings()[1] +
                        ", " + tile.getEarnings()[2] + ", " + tile.getEarnings()[3] + ", " + tile.getEarnings()[4] + "\n";
            }
        }
        gp.getUi().setMapInfos(protocols);
    }

    public void produce() {

        ArrayList<Tile> tiles = db.getMapList();
        boolean blacksmithonMap = false;
        boolean coalmineOnMap = false;
        boolean bakeryOnMap = false;
        boolean windmillonMap= false;
        int chainfood = 0;
        String protocols = "";
        buildingCounter = new HashMap<>();
        buildingEarnings = new HashMap<>();
        int fsum = 0;
        int wsum = 0;
        int ssum = 0;
        int isum = 0;
        int gsum = 0;

        for (Tile tile : tiles) {
            buildingEarnings.put(tile.getId(), new int[]{0,0,0,0,0});
        }

        for (Tile tile : tiles) {
            if (buildingCounter.get(tile.getId()) == null) {
                buildingCounter.put(tile.getId(), 1);
            } else {
                buildingCounter.put(tile.getId(), buildingCounter.get(tile.getId()) + 1);
            }
            if(tile.getId() == db.getIDperName("Bauer")) // alle Buildings die mit Kette funktionieren
                chainfood += tile.getEarnings()[0];
            else    //landen alle Buildigns die eine Kette haben
                buildingEarnings.put(tile.getId(), new int[]{buildingEarnings.get(tile.getId())[0] += tile.getEarnings()[0],
                buildingEarnings.get(tile.getId())[1] += tile.getEarnings()[1],buildingEarnings.get(tile.getId())[2] += tile.getEarnings()[2],
                buildingEarnings.get(tile.getId())[3] += tile.getEarnings()[3],buildingEarnings.get(tile.getId())[4] += tile.getEarnings()[4]});

            if(tile.getId() >= 10) {
                System.out.println(tile.getName() + " earnings: " + tile.getEarnings()[0] + " " + tile.getEarnings()[1] +
                        ", " + tile.getEarnings()[2] + ", " + tile.getEarnings()[3] + ", " + tile.getEarnings()[4]);
                protocols += tile.getName() + " earnings: " + tile.getEarnings()[0] + ", " + tile.getEarnings()[1] +
                        ", " + tile.getEarnings()[2] + ", " + tile.getEarnings()[3] + ", " + tile.getEarnings()[4] + "\n";
            }
        }
        if(buildingCounter.get(db.getIDperName("Schmiede")) != null)
            blacksmithonMap = true;
        if(buildingCounter.get(db.getIDperName("Kohlemine")) != null)
            coalmineOnMap = true;
        if(buildingCounter.get(db.getIDperName("Muehle")) != null)
             windmillonMap = true;
        if(buildingCounter.get(db.getIDperName("Baeckerei")) != null)
             bakeryOnMap = true;
        Set<Integer> set = buildingEarnings.keySet();
        for (Integer integer : set) {
            setFood(getFood() + buildingEarnings.get(integer)[0]);
            setWood(getWood() + buildingEarnings.get(integer)[1]);
            setStone(getStone() + buildingEarnings.get(integer)[2]);
            if(blacksmithonMap && coalmineOnMap)// Kette Wenn alles da ist dann Iron neu setzten
            setIron(getIron() + buildingEarnings.get(integer)[3]);
            setGold(getGold() + buildingEarnings.get(integer)[4]);

            fsum = fsum + buildingEarnings.get(integer)[0];
            wsum = wsum + buildingEarnings.get(integer)[1];
            ssum = ssum + buildingEarnings.get(integer)[2];
            isum = isum + buildingEarnings.get(integer)[3];
            gsum = gsum + buildingEarnings.get(integer)[4];
        }
        if (windmillonMap && bakeryOnMap)
        setFood(getFood() + chainfood); // Addiert das Essen aller Gebäude die eine Kette haben
        System.out.println("--------------------------------");
        System.out.println("Foodsum:" + fsum);
        System.out.println("Woodsum:" + wsum);
        System.out.println("Stonesum:" + ssum);
        System.out.println("Ironsum:" + isum);
        System.out.println("Goldsum:" + gsum);
        System.out.println("--------------------------------");
        gp.getUi().setMapInfos(protocols);
    }

    public void draw(Graphics2D g2) {
        //g2.setColor(Color.black);//Black
        //g2.setColor(new Color(0f,0f,0f,0f));//new Color(0f,0f,0f,0f)
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
