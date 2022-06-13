package at.htblakaindorf.AHIF18;

import at.htblakaindorf.AHIF18.bl.IOAccess;

import javax.swing.*;

/**
 * Main-class of the application, where the general window and {@link GamePanel} is created
 *
 * @author Manuel Reinprecht
 * @author Marcel Schmidl
 * @version 1.1 - 13.06.2022
 */
public class Main {
    public static JFrame window;

    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Citybuild");
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.startGameThread();
        window.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
                IOAccess.storeResources(gamePanel.player.getFood(), gamePanel.player.getWood(),
                        gamePanel.player.getStone(), gamePanel.player.getIron(), gamePanel.player.getGold());
            }
        });
    }
}
