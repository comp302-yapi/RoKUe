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
import superpower.SuperPower;
import views.BuildPanel;
import views.GamePanel;
import views.HallPanel;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class HallPanelKeyListener extends BaseKeyListener implements Serializable {

    private static final long serialVersionUID = 1L;

    private final HallPanel hallPanel;
    public boolean monsterSpawn = false;
    boolean isLureModeActive = false;
    private soundManager soundManager;

    public boolean luringGemCounter;

    public HallPanelKeyListener(HallPanel hallPanel) {
        super();
        this.soundManager = managers.soundManager.getInstance();
        this.hallPanel = hallPanel;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_O) {
            GameState gs = hallPanel.getViewManager().collectGameState();
            gs.currentMode = "Play";
            hallPanel.getViewManager().saveGame("src/saves/newSave.ser", gs);
            System.out.println(gs);
        }

        if (code == KeyEvent.VK_UP) {
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

        if (code == KeyEvent.VK_SPACE) {
            easterEgg();
            hallPanel.attackSoundPlayed = false;
            spacePressed = true;
            hallPanel.getPlayer().attacking = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_3) {
            if (hallPanel.getPlayer().level >= 10) {
                hallPanel.activateLightning();
            } else {
                hallPanel.triggerShake(2);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_2) {
            if (hallPanel.getPlayer().level >= 5) {
                hallPanel.activateFireBall();
            } else {
                hallPanel.triggerShake(1);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_1) {
            if (!hallPanel.isPaused()) {
                if (hallPanel.getPlayer().level >= 2) {
                    hallPanel.activateGroundSlam();
                } else {
                    hallPanel.triggerShake(0);
                }
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_G) {
            if (!hallPanel.isPaused()) {

                Player player = hallPanel.getPlayer();

                player.level = player.maxLevel;
                player.xpCurrent = player.xpMax - 1;
                player.gold = 9999;
                player.life = 100;
                hallPanel.easter1 = true;
                hallPanel.easter2 = true;
                hallPanel.easter3 = true;
                hallPanel.easter4 = true;
            }
        }

        if (code == KeyEvent.VK_R) {
            if (!hallPanel.isPaused()) {

                System.out.println("Check R");
                hallPanel.checkInventoryForReveal();
            }
        }

        if (code == KeyEvent.VK_P) {
            if (!hallPanel.isPaused()) {
                hallPanel.checkInventoryForCloak();
            }
        }

        if (code == KeyEvent.VK_B) {
            if (!hallPanel.isPaused()) {
                boolean checker = hallPanel.checkInventoryForLuringGem();
                if (checker) {
                    isLureModeActive = true;
                }
            }
        }

        if (isLureModeActive) {
            switch (code) {
                case KeyEvent.VK_A -> {
                    isLureModeActive = false;
                    System.out.println("THROWING");
                    hallPanel.throwGem("Left");
                }
                case KeyEvent.VK_D -> {
                    hallPanel.throwGem("Right");
                    isLureModeActive = false;
                }
                case KeyEvent.VK_W -> {
                    hallPanel.throwGem("Up");
                    isLureModeActive = false;
                }
                case KeyEvent.VK_S -> {
                    hallPanel.throwGem("Down");
                    isLureModeActive = false;
                }
                default -> System.out.println("Invalid direction! Use A, D, W, or S.");
            }
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

    public void easterEgg() {
        if(hallPanel.getPlayer().screenX == 916 && hallPanel.getPlayer().screenY == 718) {
            hallPanel.easter1 = true;
        }
        if(hallPanel.getPlayer().screenX == 916 && hallPanel.getPlayer().screenY == 128) {
            hallPanel.easter2 = true;
        }
        if(hallPanel.getPlayer().screenX == 376 && hallPanel.getPlayer().screenY == 128) {
            hallPanel.easter3 = true;
        }
        if(hallPanel.getPlayer().screenX == 376 && hallPanel.getPlayer().screenY == 718) {
            hallPanel.easter4 = true;
        }
    }

}
