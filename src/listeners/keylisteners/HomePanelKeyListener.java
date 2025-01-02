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
import views.HomePanel;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class HomePanelKeyListener extends BaseKeyListener implements Serializable {

    private static final long serialVersionUID = 1L;

    private final HomePanel homePanel;
    public boolean monsterSpawn = false;
    boolean isLureModeActive = false;

    public HomePanelKeyListener(HomePanel homePanel) {
        super();
        this.homePanel = homePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

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

        if (code == KeyEvent.VK_M) {
            System.out.println(homePanel.getPlayer().gold);
        }

        if (code == KeyEvent.VK_B) {
            homePanel.buyArmor();
        }

        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
            homePanel.getPlayer().attacking = true;
        }

        if (code == KeyEvent.VK_Q) {
            homePanel.getViewManager().switchTo("TitlePanel", true);
        }

        if (code == KeyEvent.VK_ENTER) {
            homePanel.getViewManager().switchTo("BuildPanel", true);
        }


        if (code == KeyEvent.VK_O) {
            GameState gs = homePanel.getViewManager().collectGameState();
            gs.currentMode = "Home";
            homePanel.getViewManager().saveGame("newSave.ser", gs);
            System.out.println(gs);
        }


        if (code == KeyEvent.VK_E) {
            monsterSpawn = true;
        }

        if (code == KeyEvent.VK_P) {
            homePanel.setPaused(!homePanel.isPaused());
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
