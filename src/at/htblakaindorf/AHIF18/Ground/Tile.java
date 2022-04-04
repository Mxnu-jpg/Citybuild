package at.htblakaindorf.AHIF18.Ground;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class Tile {

  private String name;
  public BufferedImage image;
  private boolean collision;
  private int id ;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }
}
