package arkanoid.main;

import arkanoid.controller.InputHandler;
import arkanoid.manager.GameManager;
import arkanoid.view.TerminalRenderer;

/**
 * Diem khoi dau cho phien ban Terminal.
 * Chua Game Loop.
 */
public class MainTerminal {
    public static final int SCREEN_WIDTH = 60;
    public static final int SCREEN_HEIGHT = 25;

    public static void main(String[] args) {
        //1. Khoi tao cac thanh phan
        InputHandler inputHandler = new InputHandler();
        GameManager gameManager = new GameManager(SCREEN_WIDTH, SCREEN_HEIGHT);
        TerminalRenderer renderer = new TerminalRenderer(SCREEN_WIDTH, SCREEN_HEIGHT);

        //2. Bat dau game
        inputHandler.start();
        gameManager.loadLevelAndStart(1);
        renderer.clearConsole();

        //3. Game Loop
        boolean isRunning = true;
        while (isRunning) {
            //a. Xu li Input
            char key = inputHandler.getLastKey();
            if (key != '\0') {
                gameManager.handleInput(key);
                inputHandler.consumeLastKey();
            }

            //b. Cap nhat logic
            gameManager.updateGame();
            if (gameManager.getLives() <= 0) {
                isRunning = false;
            }

            //c. Ve lai man hinh
            renderer.render(
                    gameManager.getAllGameObjects(),
                    gameManager.getScore(),
                    gameManager.getLives()
            );

            //d. Tam dung
            try {
                Thread.sleep(100); //~10 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Ket thuc game
        System.out.println("GAME OVER!");
        System.out.printf("Final Score: %d\n", gameManager.getScore());
    }
}