package arkanoid.model.brick;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import arkanoid.utils.ImageLoader;
/**
 * Lop tao gach khong the bi pha huy
 */
public class UnbreakableBrick extends Brick {
    private BufferedImage image; //Bien luu anh

    //Ham khoi tao
    public UnbreakableBrick(int x, int y, int width, int height) {
        super(x, y, width, height, 1);
        this.image = ImageLoader.loadImage("brick_unbreakable.png");
    }

    @Override
    public void render(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        }
    }

    @Override
    public void takeHit() {
        //Gach khong the bi pha vo, nen khong can lam gi khi takeHit
    }
}
