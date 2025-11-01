package arkanoid.model.brick;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import arkanoid.utils.ImageLoader;


/**
 * Gach binh thuong (1 lan va cham de pha huy).
 */
public class NormalBrick extends Brick {
    private BufferedImage image; //Bien luu anh

    public NormalBrick(int x, int y, int width, int height) {

        super(x, y, width, height, 1);
        this.image = ImageLoader.loadImage("brick_normal.png");
    }

    @Override
    public void render(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        }
    }
}