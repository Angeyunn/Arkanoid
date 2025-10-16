package arkanoid.model.brick;

/**
 * Lop gach cung hon (can 2 lan de pha huy)
 */
public class StrongBrick extends Brick {

    //Ham khoi tao
    public StrongBrick(int x, int y, int width, int height) {
        super(x, y, width, height, 2);
    }

    @Override
    public void render() {
        //Logic ve gach cung (lam sau)
    }
}
