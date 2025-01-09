package listeners.keylisteners;

import controllers.HallController;
import entity.GameState;
import entity.Player;
import enums.Hall;
import listeners.BaseKeyListener;
import managers.soundManager;
import object.ENCH_Reveal;
import object.OBJ_Door;
import object.SuperObject;
import views.*;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class BossPanelKeyListener extends BaseKeyListener implements Serializable {

    private static final long serialVersionUID = 1L;

    private final BossPanel bossPanel;
    public boolean monsterSpawn = false;
    boolean isLureModeActive = false;

    public BossPanelKeyListener(BossPanel bossPanel) {
        super();
        this.bossPanel = bossPanel;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            startEasterEgg();
            upPressed = true;
        }

        if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }

        if (code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }

        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }

        if (code == KeyEvent.VK_M) {
            System.out.println(bossPanel.getPlayer().gold);
        }

        if (code == KeyEvent.VK_B) {
            bossPanel.buyArmor();
        }

        if (e.getKeyCode() == KeyEvent.VK_G) {
            Player player = bossPanel.getPlayer();

            player.level = player.maxLevel;
            player.xpCurrent = player.xpMax - 1;
            player.gold = 9999;
//            player.life = 100;

        }

        if (e.getKeyCode() == KeyEvent.VK_3) {
            if (bossPanel.getPlayer().level >= 10) {
                bossPanel.activateLightning();
            } else {
                bossPanel.triggerShake(2);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_2) {
            if (bossPanel.getPlayer().level >= 5) {
                bossPanel.activateFireBall();
            } else {
                bossPanel.triggerShake(1);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_1) {
            if (bossPanel.getPlayer().level >= 2) {
                bossPanel.activateGroundSlam();
            } else {
                bossPanel.triggerShake(0);
            }
        }

        if (code == KeyEvent.VK_SPACE) {
            bossPanel.attackSoundPlayed = false;
            spacePressed = true;
            bossPanel.getPlayer().attacking = true;
        }

        if (code == KeyEvent.VK_8) {
            bossPanel.getPlayer().diamondSword = true;
            bossPanel.getPlayer().ironSword = false;
        }

        if (code == KeyEvent.VK_9) {
            bossPanel.getPlayer().diamondSword = false;
            bossPanel.getPlayer().ironSword = true;
        }

//        if (code == KeyEvent.VK_Q) {
//            bossPanel.getViewManager().switchTo("TitlePanel", true);
//        }
//
//        if (code == KeyEvent.VK_ENTER) {
//            bossPanel.getViewManager().switchTo("BuildPanel", true);
//        }


        if (code == KeyEvent.VK_O) {
            GameState gs = bossPanel.getViewManager().collectGameState();
            gs.currentMode = "Boss";
            bossPanel.getViewManager().saveGame("src/saves/newSave.ser", gs);
            System.out.println(gs);
        }


        if (code == KeyEvent.VK_E) {
            monsterSpawn = true;
            System.out.println(soundManager.getInstance().clip);
            System.out.println(soundManager.getInstance().clip.getFrameLength());
            System.out.println(soundManager.getInstance().activeClips);
            soundManager.getInstance().clip.stop();
            soundManager.getInstance().stop();
        }

        if (code == KeyEvent.VK_P) {
            bossPanel.setPaused(!bossPanel.isPaused());
        }

    }

    public void startEasterEgg() {
        if (bossPanel.easterEggFinal) {
            System.out.println("Starting Easter Egg");
            managers.soundManager.getInstance().stop();
            managers.soundManager.getInstance().setFile(18);
            managers.soundManager.getInstance().play();
            managers.soundManager.getInstance().loop();
            bossPanel.easterEggFinal = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }

        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }
}
