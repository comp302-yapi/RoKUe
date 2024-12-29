package listeners.keylisteners;

import listeners.BaseKeyListener;
import saveStates.GameState;
import views.TitlePanel;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

public class TitlePanelKeyListener extends BaseKeyListener implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L; // Add this for versioning

    private final TitlePanel titlePanel;

    public TitlePanelKeyListener(TitlePanel titlePanel) {
        this.titlePanel = titlePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W && titlePanel.getCommandNum() > 0) {
            titlePanel.setCommandNum(titlePanel.getCommandNum() - 1);
        }

        if (code == KeyEvent.VK_S && titlePanel.getCommandNum() < 2) {
            titlePanel.setCommandNum(titlePanel.getCommandNum() + 1);
        }

        if (code == KeyEvent.VK_ENTER) {
            if (titlePanel.getCommandNum() == 0) {
                titlePanel.getViewManager().switchTo("BuildPanel", true); //TODO CHANGE BACK TO BUILD PANEL
            }

            if (titlePanel.getCommandNum() == 2) {
                System.exit(0);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
