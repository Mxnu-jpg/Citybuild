package at.htblakaindorf.AHIF18;

import javax.swing.*;

public class Main {
    public static JFrame window;
    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Clark muss immer meckern");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.startGameThread();
    }
    //Falls du das jemals sehen wirst muss ich dir sagen das deine Bilder viel zu groß sind mach das mal besser bitte PS Manu der gerade die UI fertig gemacht hat amena
}
