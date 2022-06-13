package at.htblakaindorf.AHIF18;

/**
 * Data-class for the position calculation
 *
 * @author Manuel Reinprecht
 * @version 1.0 - 14.03.2022
 */
public class Pos {
    private int x;
    private int y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
