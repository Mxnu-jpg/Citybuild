package at.htblakaindorf.AHIF18;

import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.text.Position;
import java.awt.event.*;

public class KeyHandler implements KeyListener, MouseListener, MouseMotionListener {
    //Mouse
    private Pos pointerPosition;
    private boolean mouseClicked = false;
    private boolean mousePressed = false;
    private boolean up, down, left, right, zoom;
    GamePanel gp;
    private boolean sysinfo;
    private boolean menueClicked;
    private boolean removeBuilding = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
        pointerPosition = new Pos(0,0);
    }
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
        }
        if(code == KeyEvent.VK_I){
            sysinfo = true;
        }
        //Zoom
        /*if (code == KeyEvent.VK_UP){
            gp.changeZoom(1);
        }
        if (code == KeyEvent.VK_DOWN){
            gp.changeZoom(-1);
        }
        if(code == KeyEvent.VK_SPACE){
            gp.resetZoom();
        }*/ //Map border funktioniert nur mit defaultview

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


    public void clearMouseClick(){
        mouseClicked = false;
    }


    public boolean isRemoveBuilding() {
        return removeBuilding;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseClicked = true;
        mousePressed = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        pointerPosition = new Pos(e.getPoint().x, e.getPoint().y);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        pointerPosition = new Pos(e.getPoint().x, e.getPoint().y);
    }

    public boolean isInfo() {
       return sysinfo;
    }
    public boolean isMenueClicked() {
        return menueClicked;
    }

    //Setter
    public void setSysinfo(boolean sysinfo) {
        this.sysinfo = sysinfo;
    }

    public void setMenueClicked(boolean menueClicked) {
        this.menueClicked = menueClicked;
    }

    public void setRemoveBuilding(boolean removeBuilding) {
        this.removeBuilding = removeBuilding;
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

    public Pos getPointerPosition() {
        return pointerPosition;
    }

    public boolean isMouseClicked() {
        return mouseClicked;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public boolean isSysinfo() {
        return sysinfo;
    }

}
