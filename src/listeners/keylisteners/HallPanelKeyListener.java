package listeners.keylisteners;

import controllers.HallController;
import entity.GameState;
import enums.Hall;
import listeners.BaseKeyListener;
import object.ENCH_Reveal;
import object.OBJ_Door;
import object.SuperObject;
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

    public HallPanelKeyListener(HallPanel hallPanel) {
        super();
        this.hallPanel = hallPanel;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_O) {
            GameState gs = hallPanel.getViewManager().collectGameState();
            hallPanel.getViewManager().saveGame("newSave.ser", gs);
            System.out.println(gs);
        }

        if (code == KeyEvent.VK_L) {
            System.out.println("Pressed Load");
            GameState gs = hallPanel.getViewManager().loadGame("newSave.ser");
            System.out.println(gs.toString());
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
                case KeyEvent.VK_A -> {
                    isLureModeActive = false;
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

        if (code == KeyEvent.VK_P) {
            hallPanel.setPaused(!hallPanel.isPaused());
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
    }
}
