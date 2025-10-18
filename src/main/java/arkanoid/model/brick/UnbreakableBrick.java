package arkanoid.model.brick;

import java.awt.Color;
import java.awt.Graphics;
/**
 * Lop tao gach khong the bi pha huy
 */
public class UnbreakableBrick extends Brick {
    //Ham khoi tao
    public UnbreakableBrick(int x, int y, int width, int height) {
        super(x, y, width, height, 1);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.DARK_GRAY); //Gach mau xam toi
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }

    @Override
    public void takeHit() {
        //Gach khong the bi pha vo, nen khong can lam gi khi takeHit
    }
}
