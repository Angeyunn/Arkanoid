package arkanoid.model;

/**
 * Dai dien cho qua bong.
 */
public class Ball extends MovableObject {
    //Ham khoi tao
    public Ball(int x, int y, int size, double speed) {
        //Bong la hinh vuong nen width = height
        super(x, y, size, size, speed);
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render() {
        //Ve hinh anh qua bong (lam sau)
    }

    //Dao nguoc huong di chuyen khi va cham
    public void reverseXVelocity() {
        this.velocityX *= -1;
    }

    public void reverseYVelocity() {
        this.velocityY *= -1;
    }
}