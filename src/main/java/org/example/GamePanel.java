package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private Timer timer;

    private Game game;
    private GameLogic logic;
    private GameRenderer renderer;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        game = new Game(WIDTH, HEIGHT);
        logic = new GameLogic(game, WIDTH, HEIGHT);
        renderer = new GameRenderer(WIDTH, HEIGHT);

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logic.update();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderer.render((Graphics2D) g, game);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT) game.paddle.left = true;
        if (code == KeyEvent.VK_RIGHT) game.paddle.right = true;
        if (code == KeyEvent.VK_P) game.paused = !game.paused;
        if (code == KeyEvent.VK_R && game.gameOver) {
            game = new Game(WIDTH, HEIGHT);
            logic = new GameLogic(game, WIDTH, HEIGHT);
        }
        if (code == KeyEvent.VK_ESCAPE) System.exit(0);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT) game.paddle.left = false;
        if (code == KeyEvent.VK_RIGHT) game.paddle.right = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}

