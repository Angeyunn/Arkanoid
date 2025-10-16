package arkanoid.model.brick;

/**
 * Lop tao gach khong the bi pha huy
 */
public class UnbreakableBrick extends Brick {
    //Ham khoi tao
    public UnbreakableBrick(int x, int y, int width, int height) {
        super(x, y, width, height, 1);
    }

    @Override
    public void render() {
        //logic ve gach cung (lam sau)
    }

    @Override
    public void takeHit() {
        //Gach khong the bi pha vo, nen khong can lam gi khi takeHit
    }
}
