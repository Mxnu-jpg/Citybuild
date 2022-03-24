package at.htblakaindorf.AHIF18.Entity;

import at.htblakaindorf.AHIF18.GamePanel;
import at.htblakaindorf.AHIF18.KeyHandler;

import java.awt.*;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler kH;

    public int screenX;
    public int screenY;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.kH = keyH;

        screenX = gp.getScreenWidth()/2 - (gp.getTilesize()/2);
        screenY = gp.getScreenHeight()/2 - (gp.getTilesize()/2);
        setDefaultValues();
    }

    public void setDefaultValues(){
        worldX = gp.getTilesize() * 23;
        worldY = gp.getTilesize() * 21;
        speed = 4;
    }
    public void update(){
        if(kH.isUp() == true){
            worldY -= speed;

        }
        if(kH.isDown() == true){
            worldY += speed;
        }
        if(kH.isLeft() == true){
            worldX -= speed;
        }
        if(kH.isRight() == true){
            worldX += speed;
        }
    }
    public void draw(Graphics2D g2){
        g2.setColor(Color.BLACK);//new Color(0f,0f,0f,0f)
        g2.fillRect(screenX,screenX, gp.getTilesize(), gp.getTilesize());
    }
}
