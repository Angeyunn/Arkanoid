package aarkanoid.gameObjects;
import java.awt.*;
public class HardBrick extends Brick {
    public HardBrick(int x, int y) { super(x, y, 45, 20, 4); }
    public int getScoreValue() { return 100; }
    protected Color getFallbackColor() { return Color.BLUE; }
}
