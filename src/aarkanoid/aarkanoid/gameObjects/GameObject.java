package aarkanoid.gameObjects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;

public abstract class GameObject {
    protected int x, y, width, height;
    protected Rectangle bounds;
    protected BufferedImage texture;
    protected String texturePath;

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(x, y, width, height);
        texture = null;
        texturePath = null;
    }

    public GameObject(int x, int y, int width, int height, String texturePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(x, y, width, height);
        this.texturePath = texturePath;
        loadTexture();
    }

    protected void loadTexture() {
        try{
            if(texture != null){
                texture = ImageIO.read(getClass().getClassLoader().getResource(texturePath));
            }
            if(texture == null){
                texture = ImageIO.read(new File("res/" + texturePath));
            }
        } catch (Exception e) {
            System.err.println("Error loading texture: " + texturePath);
            texture = null;
        }
        if(texture == null){
            createFallbackTexture();
        }
    }

    protected void createFallbackTexture() {
        texture = new BufferedImage(Math.max(1, width), Math.max(1, height), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = texture.createGraphics();
        g2d.setColor(getFallbackColor());
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
    }

    protected abstract Color getFallbackColor();
    public abstract void render(Graphics g);

    public void update() {
        bounds.setLocation(x, y);
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public Rectangle getBounds() {return bounds;}

    public BufferedImage getTexture() {return texture;}

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        bounds.setLocation(x, y);
    }

    public void setX(int x) {
        this.x = x;
        bounds.x = x;
    }
    public void setY(int y) {
        this.y = y;
        bounds.y = y;
    }
}