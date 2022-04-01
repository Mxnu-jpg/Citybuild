package at.htblakaindorf.AHIF18.Entity;

import at.htblakaindorf.AHIF18.GamePanel;
import at.htblakaindorf.AHIF18.KeyHandler;

import java.awt.*;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler kH;

    public final int screenX;
    public final int screenY;


    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.kH = keyH;

        screenX = gp.getScreenWidth()/2 - (gp.getTileSize()/2);
        screenY = gp.getScreenHeight()/2 - (gp.getTileSize()/2);
        setDefaultValues();
    }

    public void setDefaultValues(){
        //spawn
        worldX = gp.getTileSize() * 25;
        worldY = gp.getTileSize() * 25;
        speed = 4;
    }

    public void update(){
        if(kH.isUp() == true){
            if(worldY!=screenY-gp.getUi().getHeight_of_Top_UI())
            worldY -= speed;
            System.out.println(worldY);
        }
        if(kH.isDown() == true){
            if(worldY!=gp.getWorldHeight()-screenY+gp.getUi().getHeight_of_Bottom_UI())//not perfect
            worldY += speed;
            System.out.println(worldY);
            System.out.println(gp.getWorldHeight());
        }
        if(kH.isLeft() == true){
            if(worldX!=screenX)
            worldX -= speed;
        }
        if(kH.isRight() == true){
            if(worldX!=gp.getWorldWidth() - screenX)//not perfect
            worldX += speed;
        }
        if(kH.isMouseClicked() == true){
            kH.clearMouseClick();
            //Der Tile in der Mitte hat scale * originaltilesize das muss man hinzurechenn mouse = tilesize + screen
            //UI Measurements
            System.out.println("MX:" + kH.getPointerPosition().getX());
            System.out.println("MY:" + kH.getPointerPosition().getY());
            System.out.println("SX:" + (screenX*2 + gp.getTileSize()));
            System.out.println("SY:" + (screenY*2 + gp.getTileSize()));
            System.out.println("AUY:" + (gp.getUi().calculateMenuePos(0) + gp.getUi().getMenuetilesize()));
            System.out.println("EUY:" + (gp.getScreenWidth() - (gp.getUi().calculateMenuePos(0) + gp.getUi().getMenuetilesize())));
            System.out.println("SumY:" + ((gp.getScreenWidth() - (gp.getUi().calculateMenuePos(0) + gp.getUi().getMenuetilesize())) - (gp.getUi().calculateMenuePos(0) + gp.getUi().getMenuetilesize())));
            System.out.println("diff:" + ( gp.getUi().calculateMenuePos(1) - (gp.getUi().calculateMenuePos(0)+ gp.getUi().getMenuetilesize())));
            //Menu clicked
            if(kH.getPointerPosition().getY() >= gp.getScreenHeight() - gp.getUi().getHeight_of_Bottom_UI() && kH.getPointerPosition().getY() <= gp.getScreenHeight()){
                    menuClicked();
            }
            //
        }

        if(kH.isInfo() == true){
            kH.setSysinfo(false);
            System.out.println("System - Info:");;
        }
    }
    public void menuClicked(){
        //Bottom Menu clicked
        if(kH.getPointerPosition().getY() >= gp.getScreenHeight() - gp.getUi().getHeight_of_Bottom_UI() + gp.getUi().getMargin_from_Bottom_Menu() &&
                kH.getPointerPosition().getY() <= gp.getScreenHeight() - gp.getUi().getHeight_of_Bottomsection_UI() - gp.getUi().getMargin_from_Bottomsection_Menu() - gp.getUi().getMargin_from_Bottom_Menu())
            elementsLineClicked();

        if(kH.getPointerPosition().getY() >= gp.getScreenHeight() - gp.getUi().getHeight_of_Bottomsection_UI() - gp.getUi().getMargin_from_Bottomsection_Menu()  && kH.getPointerPosition().getY() <= gp.getScreenHeight() - gp.getUi().getMargin_from_Bottomsection_Menu())
            sectionLineClicked();

    }

    private void sectionLineClicked() {
        for (int i = 0;i < gp.getUi().getAmount_of_items_in_UI(); i++)
            if(kH.getPointerPosition().getX() <= gp.getUi().calculateBottomMenuePos(i) + gp.getUi().getMenuetilesize() && kH.getPointerPosition().getX() >= gp.getUi().calculateBottomMenuePos(i))
        System.out.println("Section clicked, Section: " + (i+1) + "Name: ");
    }

    private void elementsLineClicked() {
        for (int i = 0;i < gp.getUi().getAmount_of_items_in_UI(); i++)
        if(kH.getPointerPosition().getX() <= gp.getUi().calculateMenuePos(i) + gp.getUi().getMenuetilesize() && kH.getPointerPosition().getX() >= gp.getUi().calculateMenuePos(i))
        System.out.println("Elements clicked, Elements: " + (i+1) + "Name: ");
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.black);//Black
        g2.fillRect(screenX,screenY, gp.getTileSize(), gp.getTileSize());
        //g2.setColor(new Color(0f,0f,0f,0f));//new Color(0f,0f,0f,0f)
    }
}
