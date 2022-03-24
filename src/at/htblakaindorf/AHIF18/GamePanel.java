package at.htblakaindorf.AHIF18;

import at.htblakaindorf.AHIF18.Entity.Player;
import at.htblakaindorf.AHIF18.Ground.Tile;
import at.htblakaindorf.AHIF18.Ground.TileManager;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;

public class GamePanel extends JPanel implements Runnable{
    final int originalTitleSize = 32;
    final int scale = 2;
    final int tilesize = originalTitleSize*scale;
    final int maxScreenCol = 24;
    final int maxScreenRow = 32;
    final int screenWidth = tilesize * maxScreenCol;
    final int screenHeight = tilesize * maxScreenRow;

    final int FPS = 60;
    Thread gameThread;
    KeyHandler kH = new KeyHandler();
    Player player = new Player(this,kH);
    int defaultx = 100;
    int defaulty = 100;
    int playerspeed = 4;

    TileManager tileM = new TileManager(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.GREEN);
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
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2); //Draw Ground
        player.draw(g2);
        /*g2.setColor(Color.BLACK);
        g2.fillRect(defaultx,defaulty, tilesize, tilesize);*/
        g2.dispose();
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
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public int getOriginalTitleSize() {
        return originalTitleSize;
    }

    public int getScale() {
        return scale;
    }

    public int getTilesize() {
        return tilesize;
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
}
