package at.htblakaindorf.AHIF18;

import at.htblakaindorf.AHIF18.Object.Church;

public class AssetSetter {

    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new Church();
    }
}
