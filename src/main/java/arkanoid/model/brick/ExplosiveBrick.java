package arkanoid.model.brick;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import arkanoid.utils.ImageLoader;


/**
 * Gach phat no ra cac huong xung quanh
 */
public class ExplosiveBrick extends Brick {
    private BufferedImage image; //Bien luu anh

    public ExplosiveBrick(int x, int y, int width, int height) {
        super(x, y, width, height, 1);
        this.image = ImageLoader.loadImage("brick_explosive.png");
    }

    @Override
    public void render(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        }
    }

    // Giup GameManager biet day la gach no
    public boolean isExplosive() {
        return true;
    }
}