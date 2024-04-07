package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws Exception {
        // JFrame is the window.
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("RyiSnow Game");

        // GamePanel holds all the information on the panel. Anything that starts with 'gp' refers to the GamePanel
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // loads position of objects
        gamePanel.setUpGame();

        // begins game thread
        gamePanel.startGameThread();
    }
}
