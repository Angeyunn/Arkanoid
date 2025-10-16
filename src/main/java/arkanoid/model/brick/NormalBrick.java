package arkanoid.model.brick;

/**
 * Gach binh thuong (1 lan va cham de pha huy).
 */
public class NormalBrick extends Brick {
    public NormalBrick(int x, int y, int width, int height) {
        super(x, y, width, height, 1);
    }

    @Override
    public void render() {
        //Ve hinh anh gach (lam sau)
    }
}