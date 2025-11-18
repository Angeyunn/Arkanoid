package arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public class Background {
    private final Image image;

    public Background(String path) {
        this.image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
    }

    public void render(GraphicsContext gc, double width, double height) {
        gc.drawImage(image, 0, 0, width, height);
    }

    public Image getImage() {
        return image;
    }
}
