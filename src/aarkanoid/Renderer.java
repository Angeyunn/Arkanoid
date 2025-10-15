package aarkanoid;

import aarkanoid.gameObjects.GameObject;
import aarkanoid.states.GameState;
import java.awt.*;
import java.util.List;

public class Renderer {

    public void render(Graphics g, List<GameObject> objects, int score, int lives, int level, GameState state) {
        // Clear screen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 520, 450);

        // Render objects
        for (GameObject obj : objects) {
            obj.render(g);
        }

        // Render UI
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 20, 430);
        g.drawString("Lives: " + lives, 420, 430);

        // Render state message
        if (state != GameState.PLAYING) {
            g.setFont(new Font("Arial", Font.BOLD, 32));
            String message = getStateMessage(state);
            g.drawString(message, 150, 200);
        }
    }

    private String getStateMessage(GameState state) {
        switch (state) {
            case MENU: return "PRESS SPACE TO START";
            case GAME_OVER: return "GAME OVER";
            case VICTORY: return "VICTORY!";
            default: return "";
        }
    }
}