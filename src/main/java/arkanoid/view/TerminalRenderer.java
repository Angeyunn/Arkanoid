package arkanoid.view;

import arkanoid.model.*;
import arkanoid.model.brick.*;
import arkanoid.model.powerup.ExpandPaddlePowerUp;
import arkanoid.model.powerup.PowerUp;
import arkanoid.model.powerup.ShrinkPaddlePowerUp;

import java.io.IOException;
import java.util.List;

/**
 * Nhan trang thai hien tai cua game va ve cac object len terminal.
 */
public class TerminalRenderer {
    private final int width;
    private final int height;
    private final char[][] screenBuffer; //Mang 2D de ve cac doi tuong

    public TerminalRenderer(int width, int height) {
        this.width = width;
        this.height = height;
        this.screenBuffer = new char[height][width];
    }

    //Phuong thuc chinh de ve game trong mot frame
    public void render(List<GameObject> gameObjects, int score, int lives) {
        clearBuffer(); //xoa buffer
        //Ve cac doi tuong vao buffer
        for (GameObject obj : gameObjects) {
            if (obj.isActive()) {
                drawObject(obj);
            }
        }
        String info = String.format("Score: %d, Lives: %d", score, lives);
        drawText(info, 1, 0);
        printBuffer(); //in buffer ra man hinh
    }

    //Xoa man hinh console khi bat dau
    public void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Xoa buffer va ve lai khung vien
    private void clearBuffer() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                    screenBuffer[i][j] = '#';
                } else {
                    screenBuffer[i][j] = ' ';
                }
            }
        }
    }

    //In buffer ra console
    private void printBuffer() {
        //Ma ANSI: dua con tro ve goc tren cung de ghi de len frame cu
        System.out.print("\033[H");
        for (int i = 0; i < height; i++) {
            System.out.println(new String(screenBuffer[i]));
        }
    }

    //Ve chuoi text vao buffer
    private void drawText(String text, int x, int y) {
        for (int i = 0; i < text.length(); i++) {
            if (x + i < width - 1) {
                screenBuffer[y][x + i] = text.charAt(i);
            }
        }
    }

    //Ve mot object vao buffer
    private void drawObject(GameObject obj) {
        char symbol = '?'; //Ki tu mac dinh

        if (obj instanceof Ball) symbol = 'O';
        else if (obj instanceof Paddle) symbol = '=';
        else if (obj instanceof ExplosiveBrick) symbol = 'E';
        else if (obj instanceof StrongBrick) symbol = 'S';
        else if (obj instanceof UnbreakableBrick) symbol = 'X';
        else if (obj instanceof NormalBrick) symbol = '#';
        else if (obj instanceof ExpandPaddlePowerUp) symbol = 'P';
        else if (obj instanceof ShrinkPaddlePowerUp) symbol = 'p';

        //Ve vao buffer dua tren toa do va kich thuoc
        for (int i = 0; i < obj.getHeight(); i++) {
            for (int j = 0; j < obj.getWidth(); j++) {
                int screenX = (int)obj.getX() + j;
                int screenY = (int)obj.getY() + i;
                if (screenY > 0 && screenY < height - 1 && screenX > 0 && screenX < width - 1) {
                    screenBuffer[screenY][screenX] = symbol;
                }
            }
        }
    }
}