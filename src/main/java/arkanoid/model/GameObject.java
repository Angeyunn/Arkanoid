package arkanoid.model;

import java.awt.Rectangle; //dùng để xử lí va chạm

/**
 * Lớp trừu tượng cho tất cả đối tượng trong game.
 * Chứa các properties và method chung như vị trí, kich thước,...
 */
public abstract class GameObject {
    protected int x, y; // Luu toa do x, y cua goc tren cung ben trai
    protected int width, height; // Chieu rong, chieu cao cua doi tuong
    protected boolean isActive; // Kiem tra doi tuong con hoat dong hay khong

    //Phuong thuc khoi tao
    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isActive = true;
    }

    //Phuong thuc cap nhat trang thai cua doi tuong
    public abstract void update();

    //Phuong thuc ve doi tuong
    public abstract void render();

    //Xu li va cham
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    //getter va setter cua cac properties
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
}