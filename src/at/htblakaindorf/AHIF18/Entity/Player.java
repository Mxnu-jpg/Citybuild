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
            if(worldY>screenY)//good take
            worldY -= speed;
            System.out.println(worldX);
            System.out.println(worldY);
        }
        if(kH.isDown() == true){

            worldY += speed;
        }
        if(kH.isLeft() == true){
            if(worldX>screenX)
            worldX -= speed;
        }
        if(kH.isRight() == true){
            if(worldX>screenX)
            worldX += speed;
        }
    }
    public void draw(Graphics2D g2){

        g2.setColor(Color.BLACK);//Black
        g2.fillRect(screenX,screenY, gp.getTileSize(), gp.getTileSize());
        //g2.setColor(new Color(0f,0f,0f,0f));//new Color(0f,0f,0f,0f)
    }
}
