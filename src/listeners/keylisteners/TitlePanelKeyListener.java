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

        if (code == KeyEvent.VK_W && titlePanel.getCommandNum() > 0) {
            titlePanel.setCommandNum(titlePanel.getCommandNum() - 1);
        }

        if (code == KeyEvent.VK_S && titlePanel.getCommandNum() < 2) {
            titlePanel.setCommandNum(titlePanel.getCommandNum() + 1);
        }

        if (code == KeyEvent.VK_ENTER) {
            if (titlePanel.getCommandNum() == 0) {
                titlePanel.getViewManager().switchTo("HomePanel", true);
            }

            if (titlePanel.getCommandNum() == 1) {
                System.out.println("Loading Game...");
                GameState gs = titlePanel.getViewManager().loadGame("newSave.ser");

                if (gs.currentMode.equals("Build")) {
                    titlePanel.getViewManager().restoreGameState(gs);
                    titlePanel.getViewManager().switchTo("BuildPanel", true);
                }

                if (gs.currentMode.equals("Play")) {
                    titlePanel.getViewManager().restoreGameState(gs);
                    titlePanel.getViewManager().switchTo("HallPanel", true);
                }

                if (gs.currentMode.equals("Home")) {
                    titlePanel.getViewManager().restoreGameState(gs);
                    titlePanel.getViewManager().switchTo("HomePanel", true);
                }

                System.out.println(gs.toString());
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
