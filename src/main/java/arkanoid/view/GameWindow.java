package arkanoid.view;

import javax.swing.JFrame;

/**
 * Lop tao ra cua so chinh cho game
 */
public class GameWindow {
    private JFrame frame;

    public GameWindow() {
        frame = new JFrame();
        GamePanel gamePanel = new GamePanel(); //Tao panel de ve game
        frame.add(gamePanel); //Them panel vao cua so
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Thoat game khi an X
        frame.setResizable(false); //Khong cho phep thay doi kich thuoc
        frame.pack();
        frame.setLocationRelativeTo(null); //Vi tri cua so giua man hinh
        frame.setVisible(true); //Hien thi cua so
    }
}
