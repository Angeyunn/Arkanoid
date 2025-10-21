package aarkanoid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    private GameManager gameManager;

    public Main() {
        gameManager = GameManager.getInstance();
        initUI();
        startGameLoop();
    }

    private void initUI() {
        setTitle("Arkanoid Game - BIG SCREEN");
        setSize(GameManager.getScreenWidth(), GameManager.getScreenHeight());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        // Input handling
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                gameManager.handleKeyPress(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                gameManager.handleKeyRelease(e.getKeyCode());
            }
        });

        System.out.println("🎮 Game UI initialized - Big Screen: " + getWidth() + "x" + getHeight());
    }

    private void startGameLoop() {
        new Thread(() -> {
            System.out.println("🔄 Game loop started");
            while (true) {
                gameManager.update();
                repaint();
                try {
                    Thread.sleep(16); // ~60 FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            gameManager.render(g);
        }
    }

    public static void main(String[] args) {
        System.out.println("🚀 Starting Arkanoid Game - BIG SCREEN MODE...");
        SwingUtilities.invokeLater(() -> {
            Main game = new Main();
            game.setVisible(true);
            System.out.println("✅ Big screen game window shown");
        });
    }
}