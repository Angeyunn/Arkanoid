package arkanoid.model.brick;

import arkanoid.model.GameObject;

/**
 * Lop truu tuong cho tat ca cac loai gach.
 */
public abstract class Brick extends GameObject {
    protected int hitPoints; //So lan can va cham de pha huy

    //Ham khoi tao
    public Brick(int x, int y, int width, int height, int hitPoints) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
    }

    //Ham kiem tra bi pha hay chua
    public boolean isDestroyed() {
        return hitPoints <= 0;
    }

    //Xu li khi bi bong va cham
    public void takeHit() {
        if (hitPoints > 0) {
            hitPoints--;
        }
        if (isDestroyed()) {
            this.isActive = false;
        }
    }

    @Override
    public void update() {
        //Gach khong di chuyen
    }
}