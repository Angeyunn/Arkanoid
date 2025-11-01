package arkanoid.model;

/**
 * Lop truu tuong cho cac doi tuong co the di chuyen (ball, paddle,...).
 * Ke thua tu GameObject va them thuoc tinh van toc.
 */
public abstract class MovableObject extends GameObject {
    protected double velocityX, velocityY;
    protected double speed;

    //Phuong thuc khoi tao
    public MovableObject(int x, int y, int width, int height, double speed) {
        super(x, y, width, height);
        this.speed = speed;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    //phuong thuc di chuyen co ban nhat
    public void move() {
        x += velocityX;
        y += velocityY;
    }

    //Phuong thuc tinh toan van toc dua tren speed
    public void setDirection(double X, double Y) {
        //Chuan hoa vector huong
        double length = Math.sqrt(X * X + Y * Y);
        if (length != 0) {
            this.velocityX = (X / length) * speed;
            this.velocityY = (Y / length) * speed;
        } else {
            this.velocityX = 0;
            this.velocityY = 0;
        }
    }
}