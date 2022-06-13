package at.htblakaindorf.AHIF18;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <b>CityBuild</b><br>
 * Tool, for resizing and scaling the images used in the game
 *
 * @author Manuel Reinprecht
 * @version 1.0
 * @since 14.03.2022
 */
public class UtilityTool {

    /**
     * Scales the image based on a given width, height and the original image
     *
     * @param height   of the newly scaled image
     * @param width    of th newly scaled image
     * @param original image
     */
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();
        return scaledImage;
    }


}
