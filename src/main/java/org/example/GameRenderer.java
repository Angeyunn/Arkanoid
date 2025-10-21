package org.example;

import javax.swing.*;
import java.awt.*;

public class GameRenderer {
    private final int WIDTH, HEIGHT;
    private Image background;

    public GameRenderer(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.background = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
    }

    public void render(Graphics2D g2, Game game) {
        g2.drawImage(background, 0, 0, WIDTH, HEIGHT, null);

        game.wall.draw(g2);
        g2.drawImage(game.paddle.image, game.paddle.x, game.paddle.y, game.paddle.width, game.paddle.height, null);
        g2.drawImage(game.ball.image, (int)game.ball.x, (int)game.ball.y, game.ball.size, game.ball.size, null);

        for (Brick b : game.bricks) {
            g2.drawImage(b.image, b.x, b.y, b.width, b.height, null);
        }

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Consolas", Font.PLAIN, 18));
        g2.drawString("Score: " + game.score, 20, 25);
        g2.drawString("Lives: " + game.lives, WIDTH - 120, 25);

        if (game.paused)
            drawCenteredText(g2, "PAUSED", new Font("Consolas", Font.BOLD, 36));
        if (game.gameOver)
            drawCenteredText(g2, "GAME OVER", new Font("Consolas", Font.BOLD, 36));
    }

    private void drawCenteredText(Graphics2D g2, String text, Font font) {
        FontMetrics fm = g2.getFontMetrics(font);
        int x = (WIDTH - fm.stringWidth(text)) / 2;
        int y = (HEIGHT - fm.getHeight()) / 2 + fm.getAscent();
        g2.setFont(font);
        g2.drawString(text, x, y);
    }
}
