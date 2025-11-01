package arkanoid.model.brick;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import arkanoid.utils.ImageLoader;

/**
 * Lop gach cung hon (can 2 lan de pha huy)
 */
public class StrongBrick extends Brick {
    private BufferedImage image; //Bien luu anh

    //Ham khoi tao
    public StrongBrick(int x, int y, int width, int height) {
        super(x, y, width, height, 2);
        this.image = ImageLoader.loadImage("brick_strong.png");
    }

    @Override
    public void render(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        }
    }
}
