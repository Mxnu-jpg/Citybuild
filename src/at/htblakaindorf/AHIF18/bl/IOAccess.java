package at.htblakaindorf.AHIF18.bl;

import java.io.*;
import java.nio.file.Paths;

public class IOAccess {
    private final static File RESOURCE_FILE = Paths.get("", "data/datafiles", "resources.csv").toFile();

    public static void storeResources(int food, int wood, int stone, int iron, int gold){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(RESOURCE_FILE));
            bw.write(food + ";" + wood + ";" + stone + ";" + iron + ";" + gold);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String[] loadResources(){
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
