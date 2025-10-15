// NormalBrick.java
package aarkanoid.gameObjects;
import java.awt.*;
public class NormalBrick extends Brick {
    public NormalBrick(int x, int y) { super(x, y, 45, 20, 1); }
    public int getScoreValue() { return 10; }
    protected Color getFallbackColor() { return Color.RED; }
}