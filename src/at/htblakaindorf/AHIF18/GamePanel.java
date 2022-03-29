package at.htblakaindorf.AHIF18;

import at.htblakaindorf.AHIF18.Entity.Entity;
import at.htblakaindorf.AHIF18.Entity.Player;
import at.htblakaindorf.AHIF18.Ground.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    /*
    //Resolution HD:1920x1080 WQHD:2560x1440
    final int screenWidth = 1920;
    final int screenHeight = 1080;
    //Tilemanagement
    final int originalTitleSize = 32; // 32x32 tile
    private int scale = 2;
    int tileSize = originalTitleSize*scale;
    final int maxScreenCol = screenWidth/ tileSize;
    final int maxScreenRow = screenHeight/ tileSize;
    */

    //Tilemanagement
    final int originalTitleSize = 16; // 32x32 tile
    private int scale = 3;
    int tileSize = originalTitleSize*scale;
    final int maxScreenCol = 30;
    final int maxScreenRow = 20;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    //FPS
    final int FPS = 60;

    Thread gameThread;
    KeyHandler kH = new KeyHandler(this);
    Player player = new Player(this,kH);
    UI ui = new UI(this);
    TileManager tileM = new TileManager(this);


    //Zoom
    int oldWorldWidth;
    int newWorldWidth;


    //WORLD SETTINGS
    private final int maxWorldCol = 50;
    private final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        //this.setBackground(new Color(0f,0f,0f,0f));
        this.setBackground(new Color(0f,0f,0f,0f));
        this.setDoubleBuffered(true);
        this.addKeyListener(kH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
        gameThread.run();
    }


    public void update(){
        player.update();
    }

    public void changeZoom(int i) {
    oldWorldWidth = tileSize * maxWorldCol;

        if((tileSize >=85)){
            if(i<0){
                tileSize += i;
            }

        }else if((tileSize <= 35)){
            if(i>0){
                tileSize += i;
            }

        }
        else{
                tileSize += i;
        }
        System.out.println(tileSize);

    newWorldWidth = tileSize * maxWorldCol;

    double multiplier = (double)newWorldWidth/oldWorldWidth;
    double x = player.worldX * multiplier;
    double y = player.worldY * multiplier;

    player.worldX = x;
    player.worldY = y;

    System.out.println(tileSize);
    System.out.println(scale);
    System.out.println(worldWidth);
    System.out.println(worldHeight);
    System.out.println(screenWidth);
    System.out.println(screenHeight);
    }
    public void resetZoom() { //Not Working!!
        oldWorldWidth = tileSize * maxWorldCol;
        tileSize = originalTitleSize*scale;
        newWorldWidth = tileSize * maxWorldCol;

        double multiplier = (double)newWorldWidth/oldWorldWidth;
        double x = player.worldX * multiplier;
        double y = player.worldY * multiplier;

        player.worldX = x;
        player.worldY = y;
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        tileM.setG2M(g2);

        //double drawStart = 0;
        //drawStart = System.nanoTime();
        tileM.draw(g2); //Draw Ground
        player.draw(g2);
        ui.draw(g2);
        //double drawEnd = System.nanoTime();
        //double passed = drawEnd-drawStart;
        //g2.setColor(Color.white);
        //g2.drawString("Draw Time: " + passed/1000000000, 10, 400);
        //System.out.println("Draw Time: " + passed/1000000000 + "/" + passed);

    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        System.out.println("Gameloop");
        System.out.println(System.nanoTime());

        while (gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            timer +=(currentTime- lastTime);
            lastTime = currentTime;

            if(delta >=1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                //System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getOriginalTitleSize() {
        return originalTitleSize;
    }

    public int getScale() {
        return scale;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxScreenCol() {
        return maxScreenCol;
    }

    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getFPS() {
        return FPS;
    }
    public Thread getGameThread() {
        return gameThread;
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public Player getPlayer() {
        return player;
    }

    public UI getUi() {
        return ui;
    }
}
