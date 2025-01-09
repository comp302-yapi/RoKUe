package listeners.keylisteners;

import controllers.HallController;
import entity.GameState;
import entity.Player;
import managers.*;
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
    private soundManager soundManager;

    public HomePanelKeyListener(HomePanel homePanel) {
        super();
        this.homePanel = homePanel;
        this.soundManager = managers.soundManager.getInstance();
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

        if (code == KeyEvent.VK_E) {
            homePanel.equipBought();
        }

        if (e.getKeyCode() == KeyEvent.VK_G) {
            Player player = homePanel.getPlayer();

            player.level = player.maxLevel;
            player.xpCurrent = player.xpMax - 1;
            player.gold = 9999;
            player.life = 100;

        }

        if (code == KeyEvent.VK_SPACE) {
            homePanel.attackSoundPlayed = false;
            spacePressed = true;
            homePanel.getPlayer().attacking = true;
        }

        if (code == KeyEvent.VK_8) {
            homePanel.getPlayer().diamondSword = true;
            homePanel.getPlayer().ironSword = false;
        }

        if (code == KeyEvent.VK_9) {
            homePanel.getPlayer().diamondSword = false;
            homePanel.getPlayer().ironSword = true;
        }

        if (code == KeyEvent.VK_Q) {
            homePanel.getViewManager().switchTo("TitlePanel", true);
        }

        if (code == KeyEvent.VK_ENTER) {
            homePanel.getViewManager().switchTo("BuildPanel", true);
        }

        if (code == KeyEvent.VK_N) {
            homePanel.getPlayer().wearArmorLeatherLeg();
        }

        if (code == KeyEvent.VK_M) {
            homePanel.getPlayer().wearArmorIronLeg();
        }


        if (code == KeyEvent.VK_O) {
            GameState gs = homePanel.getViewManager().collectGameState();
            gs.currentMode = "Home";
            homePanel.getViewManager().saveGame("src/saves/newSave.ser", gs);
            System.out.println(gs);
        }


        if (code == KeyEvent.VK_L) {
            managers.soundManager.getInstance().stop();
        }

        //if (code == KeyEvent.VK_P) {
        //    homePanel.setPaused(!homePanel.isPaused());
        //}
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
