package arkanoid.model;

/**
 * Dai dien cho paddle nguoi choi dieu khien.
 */
public class Paddle extends MovableObject {
    //Ham khoi tao
    public Paddle(int x, int y, int width, int height, double speed) {
        super(x, y, width, height, speed);
    }

    @Override
    public void update() {
        move();
        stopMoving(); //Dung lai ngay sau khi di chuyen, cho lenh tiep theo
    }

    @Override
    public void render() {
        //Ve hinh anh Paddle (lam sau)
    }

    //Di chuyen sang hai ben
    public void moveLeft() {
        this.velocityX = -speed;
    }

    public void moveRight() {
        this.velocityX = speed;
    }

    public void stopMoving() {
        this.velocityX = 0;
    }
}