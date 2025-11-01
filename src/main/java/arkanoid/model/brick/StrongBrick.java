package arkanoid.model.brick;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import arkanoid.utils.ImageLoader;

/**
 * Lop gach cung hon (can 2 lan de pha huy)
 */
public class StrongBrick extends Brick {
    private BufferedImage imageNormal; //Bien luu anh
    private BufferedImage imageDamaged; //Bien luu anh khi con 1 mau

    //Ham khoi tao
    public StrongBrick(int x, int y, int width, int height) {
        super(x, y, width, height, 2);
        this.imageNormal = ImageLoader.loadImage("brick_strong.png");
        this.imageDamaged = ImageLoader.loadImage("brick_strong_damaged.png"); // Ảnh mới
    }

    @Override
    public void render(Graphics g) {
        BufferedImage imageToDraw = null; //Chon anh de ve dua tren hitPoints
        if (hitPoints == 2) {
            imageToDraw = imageNormal;
        } else {
            imageToDraw = imageDamaged;
        }
        if (imageToDraw != null) {
            g.drawImage(imageToDraw, x, y, width, height, null);
        }
    }
}
