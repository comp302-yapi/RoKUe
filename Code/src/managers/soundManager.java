package managers;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class soundManager {

    Clip clip;
    URL[] soundURL = new URL[30];

    public soundManager() {

        soundURL[0] = getClass().getResource("/res/sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/res/sound/coin.wav");
        soundURL[2] = getClass().getResource("/res/sound/unlock.wav");
        soundURL[3] = getClass().getResource("/res/sound/fanfare.wav");

    }

    public void setFile(int i) {
        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void play() {

        clip.start();

    }

    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    public void stop() {

        clip.stop();

    }

}
