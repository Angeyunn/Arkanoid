package arkanoid.view;

import arkanoid.manager.GameManager;
import arkanoid.utils.ImageLoader;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Lop quan li viec ve va xu li input cho Menu.
 * Quan li boi GameManager và GamePanel.
 */
public class MenuScreen {
    private BufferedImage backgroundUnmuted;
    private BufferedImage backgroundMuted;
    public MenuScreen() {
        this.backgroundUnmuted = ImageLoader.loadImage("background_1.png");
        this.backgroundMuted = ImageLoader.loadImage("background_2.png");    }
    //Ham ve Menu
    public void render(Graphics g, GameManager gm) {
        boolean isMuted = gm.getSoundManager().isMuted();
        if (isMuted) {
            if (backgroundMuted != null) {
                g.drawImage(backgroundMuted, 0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT, null);
            }
        } else {
            if (backgroundUnmuted != null) {
                g.drawImage(backgroundUnmuted, 0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT, null);
            }
        }
    }
    //Ham xu li input cho menu
    public void handleInput(int keyCode, GameManager gm, boolean isPressed) {
        if (!isPressed) {
            return;
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            gm.goToLevelSelect();
        }

        if (keyCode == KeyEvent.VK_M) {
            gm.getSoundManager().toggleMute(); //Bat, tat am thanh
        }
    }

    private void drawCenteredString(Graphics g, String string, Font font, int y) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (GamePanel.PANEL_WIDTH - metrics.stringWidth(string)) / 2;
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(string, x, y);
    }
}
