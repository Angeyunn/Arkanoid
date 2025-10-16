package arkanoid.model.brick;

/**
 * Gach phat no ra cac huong xung quanh
 */
public class ExplosiveBrick extends Brick {

    public ExplosiveBrick(int x, int y, int width, int height) {
        super(x, y, width, height, 1);
    }

    @Override
    public void render() {
        // TODO: Logic vẽ gạch nổ
    }

    // Giup GameManager biet day la gach no
    public boolean isExplosive() {
        return true;
    }
}