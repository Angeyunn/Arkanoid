package arkanoid.model.brick;

import java.awt.Graphics;
import java.awt.Color;

/**
 * Gach phat no ra cac huong xung quanh
 */
public class ExplosiveBrick extends Brick {

    public ExplosiveBrick(int x, int y, int width, int height) {
        super(x, y, width, height, 1);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.ORANGE); //Gach mau cam
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }

    // Giup GameManager biet day la gach no
    public boolean isExplosive() {
        return true;
    }
}