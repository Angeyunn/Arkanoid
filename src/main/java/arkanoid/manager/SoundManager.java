package arkanoid.manager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    //Su dung map de luu cac file am thanh da duoc tai
    private Map<String, Clip> clips;

    public SoundManager() {
        clips = new HashMap<>();
    }

    //Phuong thuc tai mot file am thanh vao cache
    public void loadSound(String sound) {

        //Kiem tra am thanh da duoc tai chua
        if (clips.containsKey(sound)) {
            return; //Neu am thanh da duoc tai thi return luon de tranh tai lai
        }

        URL soundURL = getClass().getClassLoader().getResource(sound); //Tim file am thanh

        //Khoi lenh dung de doc file am thanh
        try (AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundURL)) {
            Clip clip = AudioSystem.getClip(); //Tao ra mot clip de chua am thanh
            clip.open(audioInput); //Mo file am thanh va luu am thanh vao clip
            clips.put(sound, clip); //Luu clip vao cache de su dung
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    //Phuong thuc phat am thanh da co trong cache
    public void play(String sound) {
        Clip clip = clips.get(sound); //Lay am thanh tu cache
        if (clip != null) {
            //Neu tim thay co am thanh dang phat, se dung lai ngay lap tuc
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0); //Tua am thanh ve tu dau
            clip.start(); //Bat dau am thanh
        }
    }
}
