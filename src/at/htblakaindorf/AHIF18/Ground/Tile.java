package at.htblakaindorf.AHIF18.Ground;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class Tile {

  String name;
  public BufferedImage image;
  boolean collision = false;
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
}
