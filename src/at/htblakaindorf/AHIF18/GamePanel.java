package at.htblakaindorf.AHIF18;

import at.htblakaindorf.AHIF18.Entity.Player;
import at.htblakaindorf.AHIF18.bl.IOAccess;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * GamePanel for the actual map to play on
 *
 * @author Manuel Reinprecht
 * @version 1.3 - 13.06.2022
 */
public class GamePanel extends JPanel implements Runnable {
    /*
    Resolution HD:1920x1080 WQHD:2560x1440
    final int screenWidth = 1920;
    final int screenHeight = 1080;
    Tilemanagement
    final int originalTitleSize = 32; // 32x32 tile
    private int scale = 2;
    int tileSize = originalTitleSize*scale;
    final int maxScreenCol = screenWidth/ tileSize;
    final int maxScreenRow = screenHeight/ tileSize;
    */

    //Tilemanagement
    final int originalTitleSize = 32; // 32x32 tile
    private int scale = 2;
    int tileSize = originalTitleSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 9;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    //FPS
    final int FPS = 60;
    Graphics2D g2;
    BufferedImage tempScreen;

    Thread gameThread;
    KeyHandler kH = new KeyHandler(this);
    Player player = new Player(this, kH);
    UI ui = new UI(this);
    TileManager tileM = new TileManager(this);

    //WORLD SETTINGS
    private final int maxWorldCol = 50;
    private final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        //this.setBackground(new Color(0f,0f,0f,0f));
        this.setBackground(new Color(0f, 0f, 0f, 0f));
        this.setDoubleBuffered(true);
        this.addMouseListener(kH);
        this.addMouseMotionListener(kH);
        this.addKeyListener(kH);
        this.setFocusable(true);
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();

    }


    public void update() {
        player.update();
    }

    /**
     * Displays every Tile and UI in the Runningtime
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //RenderCalculator
        /*
        AffineTransform trans = new AffineTransform();
        trans.scale(2, 2);
        g2.setTransform(trans);
        double drawStart = 0;
        drawStart = System.nanoTime();
        */
        tileM.draw(g2); //Draw Ground
        player.draw(g2);
        ui.draw(g2);
        /*
        double drawEnd = System.nanoTime();
        double passed = drawEnd-drawStart;
        g2.setColor(Color.white);
        g2.drawString("Draw Time: " + 1000000000, 10, 400);
        System.out.println("Draw Time: " + passed/1000000000 + "/" + passed);
        */
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double prodcueInterval = 10;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        System.out.println("Gameloop");
        System.out.println(System.nanoTime());

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;


            if (prodcueInterval == 10) {
                prodcueInterval = 0;
                player.produce();
            }
            if (delta >= 1) {
                update();
                repaint();
                /*drawtotempScreen();
                drawtoScreen();*/
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                //System.out.println("FPS:" + drawCount);
                prodcueInterval++;
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public int getTileSize() {
        return tileSize;
    }


    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
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

    public TileManager getTileM() {
        return tileM;
    }
}
