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

    public GameObject(int x, int y, int width, int height, BufferedImage texture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(x, y, width, height);
        this.texture = texture;
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


}