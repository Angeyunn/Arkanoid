package arkanoid.model.brick;

import java.awt.Color;
import java.awt.Graphics;
/**
 * Lop gach cung hon (can 2 lan de pha huy)
 */
public class StrongBrick extends Brick {

    //Ham khoi tao
    public StrongBrick(int x, int y, int width, int height) {
        super(x, y, width, height, 2);
    }

    @Override
    public void render(Graphics g) {
        //Ve mau dua tren hitPoint
        if (hitPoints == 2) {
            g.setColor(Color.GRAY);
        } else {
            g.setColor(Color.LIGHT_GRAY);
        }
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }
}
