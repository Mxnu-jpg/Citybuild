package at.htblakaindorf.AHIF18.Ground;

import at.htblakaindorf.AHIF18.Ground.Behaviours.ProduceFoodAverage;
import at.htblakaindorf.AHIF18.Ground.Behaviours.ProduceBehaviour;

import java.awt.image.BufferedImage;

/**
 * Dataclass for the Tiles placed on the map
 *
 * @author Manuel Reinprecht
 * @author Marcel Schmidl
 * @version 1.1 - 11.04.2022
 * */
public class Tile{

    private String name;
    public BufferedImage image;
    private String path;
    private boolean collision;
    private int id;
    private boolean building;
    private int[] costs;
    private int[] earnings;
    private ProduceBehaviour produceBehaviour = new ProduceFoodAverage();

    public Tile() {
    }

    public Tile(String name, int id, int[] costs, int[] earnings, boolean collision, boolean building) {
        this.name = name;
        this.collision = collision;
        this.id = id;
        this.building = building;
        this.costs = costs;
        this.earnings = earnings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setCosts(int[] costs) {
        this.costs = costs;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isBuilding() {
        return building;
    }

    public void setBuilding(boolean isBuilding) {
        this.building = isBuilding;
    }
    public int[] getCosts() {
        return costs;
    }

    public int[] getEarnings() {
        return earnings;
    }
    public void setEarnings(int[] earnings) {
        this.earnings = earnings;
    }

    public void setProduceBehaviour(ProduceBehaviour behaviour){
        this.produceBehaviour = behaviour;
    }
    public void produce(){
        produceBehaviour.produce(this.earnings);
    }
}
