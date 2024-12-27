package listeners.keylisteners;

import controllers.HallController;
import enums.Hall;
import listeners.BaseKeyListener;
import object.ENCH_Reveal;
import object.OBJ_Door;
import object.SuperObject;
import views.GamePanel;
import views.HallPanel;

import java.awt.event.KeyEvent;

public class HallPanelKeyListener extends BaseKeyListener {

    private final HallPanel hallPanel;
    public boolean monsterSpawn = false;
    boolean isLureModeActive = false;

    public HallPanelKeyListener(HallPanel hallPanel) {
        super();
        this.hallPanel = hallPanel;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = true;

        }

        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        
        if (code == KeyEvent.VK_E) {
        	monsterSpawn = true;
        }

        if (code == KeyEvent.VK_R) {
            hallPanel.checkInventoryForReveal();
        }

        if (code == KeyEvent.VK_C) {
            hallPanel.checkInventoryForCloak();
        }

        if (code == KeyEvent.VK_B) {
            boolean checker = hallPanel.checkInventoryForLuringGem();
            if (checker) {
                isLureModeActive = true;
            }
        } else if (isLureModeActive) {
            switch (code) {
                case KeyEvent.VK_LEFT -> {
                    isLureModeActive = false;
                    hallPanel.throwGem("Left");

                }
                case KeyEvent.VK_RIGHT -> {
                    hallPanel.throwGem("Right");
                    isLureModeActive = false;
                }
                case KeyEvent.VK_UP -> {
                    hallPanel.throwGem("Up");
                    isLureModeActive = false;
                }
                case KeyEvent.VK_DOWN -> {
                    hallPanel.throwGem("Down");
                    isLureModeActive = false;
                }
                default -> System.out.println("Invalid direction! Use A, D, W, or S.");
            }
        }

        if (code == KeyEvent.VK_P) {
            hallPanel.setPaused(!hallPanel.isPaused());
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
