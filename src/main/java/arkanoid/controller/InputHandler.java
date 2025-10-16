package arkanoid.controller;

import java.io.IOException;

/**
 * Xu li input trong mot luong rieng biet de khong gian doan vong lap game chinh
 */
public class InputHandler implements Runnable {
    //volatile: dam bao gia tri cua bien duoc cap nhat dung giua cac luong
    private volatile char lastKey;

    //Ham khoi tao
    public InputHandler() {
        this.lastKey = '\0'; //Ky tu null
    }

    public void start() {
        Thread inputThread = new Thread(this);
        //setDaemon(true): luong nay se tu dong ket thuc khi chuong trinh chinh dung
        inputThread.setDaemon(true);
        inputThread.start();
    }

    //Phuong thuc chay trong luong rieng
    @Override
    public void run() {
        try {
            while (true) {
                //System.in.read() se dung luong nay lai cho den khi co phim duoc nhan
                lastKey = (char) System.in.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Lay phim cuoi cung nguoi choi nhan
    public char getLastKey() {
        return lastKey;
    }

    //Reset phim vua nhap, tranh xu li nhieu lan
    public void consumeLastKey() {
        this.lastKey = '\0';
    }
}