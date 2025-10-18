package arkanoid.main;

import arkanoid.view.GameWindow;
import javax.swing.SwingUtilities;

/**
 * File de chay phien ban GUI
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameWindow();
            }
        });
    }
}