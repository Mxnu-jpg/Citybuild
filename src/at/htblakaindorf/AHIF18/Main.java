package at.htblakaindorf.AHIF18;

import at.htblakaindorf.AHIF18.bl.IOAccess;

import javax.swing.*;
import java.awt.event.WindowEvent;

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
                IOAccess.storeResources(gamePanel.player.getFood(),gamePanel.player.getWood(),
                        gamePanel.player.getStone(),gamePanel.player.getIron(), gamePanel.player.getGold());
                }
        });
    }
}
