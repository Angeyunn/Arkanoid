package aarkanoid.gameObjects;
import java.awt.*;
public class StrongBrick extends Brick {
    public StrongBrick(int x, int y) { super(x, y, 45, 20, 3); }
    public int getScoreValue() { return 50; }
    protected Color getFallbackColor() { return Color.YELLOW; }
}