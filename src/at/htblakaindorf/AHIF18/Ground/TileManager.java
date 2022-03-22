package at.htblakaindorf.AHIF18.Ground;

import at.htblakaindorf.AHIF18.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    Random rn = new Random();
    int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[20];
        mapTileNum = new int[gp.getMaxScreenCol()][gp.getMaxScreenRow()];
        getTileImage();//passt sich der Rows und Columns an
        //loadMap(); //Funktion um Map zu laden!!Sie werden aber nicht übereinander gelegt;
    }

    public void loadMap(){
        try {
        InputStream is = getClass().getResourceAsStream("/res/map/map01.txt");
        BufferedReader br = new BufferedReader(new BufferedReader(new InputStreamReader(is)));


        int col = 0;
        int row = 0;
        while (col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()){
            String line = br.readLine();

            while(col< gp.getMaxScreenCol()){
                String numbers[] = line.split(" ");

                int num = Integer.parseInt(numbers[col]);
                mapTileNum[col][row] = num;
                col++;
            }
            if(col == gp.getMaxScreenCol()){
                col = 0;
                row++;
            }
        }
        br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getTileImage() {

        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass1.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass2.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass3.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass4.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/building/building1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2)
    {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()){

            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gp.getTilesize(), gp.getTilesize(), null);
            col++;
            x += gp.getTilesize();

            if(col == gp.getMaxScreenCol()){
                col = 0;
                x = 0;
                row++;
                y += gp.getTilesize();
            }

        }
    }
}
