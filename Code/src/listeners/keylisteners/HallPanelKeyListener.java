package listeners.keylisteners;

import enums.Hall;
import listeners.BaseKeyListener;
import views.GamePanel;
import views.HallPanel;

import java.awt.event.KeyEvent;

public class HallPanelKeyListener extends BaseKeyListener {

    private final HallPanel hallPanel;
    public boolean monsterSpawn = false;

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
