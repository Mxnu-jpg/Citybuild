package at.htblakaindorf.AHIF18.Object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Church extends Superobject{

    public Church() {
        name = "Church";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/building/church.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
