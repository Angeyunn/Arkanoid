package arkanoid.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Lop de tai hinh anh tu thu muc resources
 */
public class ImageLoader {
    public static BufferedImage loadImage(String path) {
        InputStream in = ImageLoader.class.getClassLoader().getResourceAsStream(path);
        try  {
            return ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
