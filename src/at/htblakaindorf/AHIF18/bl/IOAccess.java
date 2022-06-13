package at.htblakaindorf.AHIF18.bl;

import java.io.*;
import java.nio.file.Paths;

/**
 * Access-class to save and load the data for the game resources
 *
 * @author Marcel Schmidl
 * @version 1.1 - 13.06.2022
 */
public class IOAccess {
    private final static File RESOURCE_FILE = Paths.get("", "data/datafiles", "resources.csv").toFile();

    /**
     * Saves the game resources in a .csv file.
     *
     * @param food  food-value of the game
     * @param wood  wood-value of the game
     * @param stone stone-value of the game
     * @param iron  iron-value of the game
     * @param gold  gold-value of the game
     */
    public static synchronized void storeResources(int food, int wood, int stone, int iron, int gold) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(RESOURCE_FILE));
            bw.write(food + ";" + wood + ";" + stone + ";" + iron + ";" + gold);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the data for the game resources from the .csv file at the start of the game.
     *
     * @return values for each resource
     */
    public static synchronized String[] loadResources() {
        String[] resources = new String[5];

        try {
            BufferedReader br = new BufferedReader(new FileReader(RESOURCE_FILE));

            resources = br.readLine().split(";");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }
}
