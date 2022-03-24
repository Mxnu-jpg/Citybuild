package at.htblakaindorf.AHIF18;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean up, down, left, right, zoom;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W){
            up = true;
        }
        if (code == KeyEvent.VK_A){
            left = true;
        }
        if (code == KeyEvent.VK_S){
            down = true;
        }
        if (code == KeyEvent.VK_D){
            right = true;
        }if (code == KeyEvent.VK_SPACE){
            zoom = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W){
            up = false;
        }
        if (code == KeyEvent.VK_A){
            left = false;
        }
        if (code == KeyEvent.VK_S){
            down = false;
        }
        if (code == KeyEvent.VK_D){
            right = false;
        }
    }
    //Setter


    public void setZoom(boolean zoom) {
        this.zoom = zoom;
    }

    //Getter
    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isZoom() {
        return zoom;
    }

}
