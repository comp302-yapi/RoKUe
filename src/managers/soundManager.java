package managers;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;

public class soundManager implements Serializable {

    private static final long serialVersionUID = 1L;

    private static soundManager instance; // Singleton instance

    public transient Clip clip;
    URL[] soundURL = new URL[30];
    private transient FloatControl volumeControl;
    public final HashMap<Integer, Clip> activeClips = new HashMap<>();
    public Clip currentClip;

    // Private constructor to prevent direct instantiation
    private soundManager() {
        soundURL[0] = getClass().getResource("/res/sound/BattleMusic.wav");
        soundURL[1] = getClass().getResource("/res/sound/coin.wav");
        soundURL[2] = getClass().getResource("/res/sound/unlock.wav");
        soundURL[3] = getClass().getResource("/res/sound/receivedamage2.wav");
        soundURL[4] = getClass().getResource("/res/sound/burning.wav");
        soundURL[5] = getClass().getResource("/res/sound/sword1.wav");
        soundURL[6] = getClass().getResource("/res/sound/sword2.wav");
        soundURL[7] = getClass().getResource("/res/sound/hit-flesh.wav");
        soundURL[8] = getClass().getResource("/res/sound/fireball1.wav");
        soundURL[9] = getClass().getResource("/res/sound/fireball2.wav");
        soundURL[10] = getClass().getResource("/res/sound/level-up.wav");
        soundURL[11] = getClass().getResource("/res/sound/lightning.wav");
        soundURL[12] = getClass().getResource("/res/sound/error.wav");
        soundURL[13] = getClass().getResource("/res/sound/ZombieHit1.wav");
        soundURL[14] = getClass().getResource("/res/sound/ZombieHit1_1.wav");
        soundURL[15] = getClass().getResource("/res/sound/ZombieHit2.wav");
        soundURL[16] = getClass().getResource("/res/sound/BurningFinal.wav");
        soundURL[17] = getClass().getResource("/res/sound/purchase.wav");
        soundURL[18] = getClass().getResource("/res/sound/115.wav");

    }

    // Public method to access the Singleton instance
    public static synchronized soundManager getInstance() {
        if (instance == null) {
            instance = new soundManager();
        }
        return instance;
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

    public void increaseVolume(float amount) {
        if (volumeControl != null) {
            float currentVolume = volumeControl.getValue();
            float maxVolume = volumeControl.getMaximum();
            volumeControl.setValue(Math.min(currentVolume + amount, maxVolume));
        }
    }

    public void setVolumeForSound0(float amount) {
        setFile(0);
        increaseVolume(amount);
    }

    public void setVolumeForSound7(float amount) {
        setFile(7);
        increaseVolume(amount);
    }

    public void setVolumeForSound10(float amount) {
        setFile(10);
        if (volumeControl != null) {
            float currentVolume = volumeControl.getValue();
            volumeControl.setValue(currentVolume - amount);
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.flush();
            clip.setFramePosition(0);
        }
        this.clip = null;
    }
}