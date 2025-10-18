package arkanoid.model.brick;

import java.awt.Graphics;
import java.awt.Color;

/**
 * Gach binh thuong (1 lan va cham de pha huy).
 */
public class NormalBrick extends Brick {
    public NormalBrick(int x, int y, int width, int height) {
        super(x, y, width, height, 1);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.CYAN); //Gach mau xanh lam
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK); //Vien mau den
        g.drawRect(x, y, width, height);
    }
}