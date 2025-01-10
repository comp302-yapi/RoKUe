package listeners.keylisteners;

import entity.GameState;
import listeners.BaseKeyListener;
import views.TitlePanel;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class TitlePanelKeyListener extends BaseKeyListener implements Serializable {

    private static final long serialVersionUID = 1L;

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

        // Yukarı ok tuşuyla yukarı hareket
        if (code == KeyEvent.VK_UP && titlePanel.getCommandNum() > 0) {
            titlePanel.setCommandNum(titlePanel.getCommandNum() - 1);
        }

        // Aşağı ok tuşuyla aşağı hareket (artık sınır 3)
        if (code == KeyEvent.VK_DOWN && titlePanel.getCommandNum() < 3) {
            titlePanel.setCommandNum(titlePanel.getCommandNum() + 1);
        }

        // ENTER tuşuyla seçilen menüyü aç
        if (code == KeyEvent.VK_ENTER) {
            if (titlePanel.getCommandNum() == 0) {
                titlePanel.getViewManager().switchTo("HomePanel", true);
            }

            if (titlePanel.getCommandNum() == 1) {
                titlePanel.getViewManager().switchTo("LoadPanel", true);
            }

            if (titlePanel.getCommandNum() == 2) {
                System.exit(0);
            }

            // Yeni eklenen seçenek (HELP)
            if (titlePanel.getCommandNum() == 3) {
                titlePanel.getViewManager().switchTo("HelpPanel", true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
