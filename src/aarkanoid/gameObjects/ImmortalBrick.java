// ImmortalBrick.java
package aarkanoid.gameObjects;
import java.awt.*;
public class ImmortalBrick extends Brick {
    public ImmortalBrick(int x, int y) { super(x, y, 45, 20, 999); }
    public void onHit() {} // Không làm gì
    public int getScoreValue() { return 0; }
    public boolean isImmortal() { return true; }
    protected Color getFallbackColor() { return Color.MAGENTA; }
}