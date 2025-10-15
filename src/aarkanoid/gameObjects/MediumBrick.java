// MediumBrick.java
package aarkanoid.gameObjects;
import java.awt.*;
public class MediumBrick extends Brick {
    public MediumBrick(int x, int y) { super(x, y, 45, 20, 2); }
    public int getScoreValue() { return 25; }
    protected Color getFallbackColor() { return Color.ORANGE; }
}