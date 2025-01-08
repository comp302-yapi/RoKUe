package listeners.keylisteners;

import entity.GameState;
import listeners.BaseKeyListener;
import views.LoadPanel;
import views.TitlePanel;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.Serializable;

public class LoadPanelKeyListener extends BaseKeyListener implements Serializable {

    private static final long serialVersionUID = 1L;

    private final LoadPanel loadPanel;

    public LoadPanelKeyListener(LoadPanel loadPanel) {
        this.loadPanel = loadPanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W && loadPanel.getCommandNum() > 0) {
            loadPanel.setCommandNum(loadPanel.getCommandNum() - 1);
        }

        if (code == KeyEvent.VK_S && loadPanel.getCommandNum() < 6) {
            loadPanel.setCommandNum(loadPanel.getCommandNum() + 1);
        }

        if (code == KeyEvent.VK_K) {
            loadPanel.getViewManager().switchTo("BossPanel", true);
        }

        if (code == KeyEvent.VK_ENTER) {
            int commandNum = loadPanel.getCommandNum();
            String[] saveFiles = listSaveFiles();

            if (commandNum >= 0 && commandNum < saveFiles.length) {
                System.out.println("Loading Game...");
                String selectedSaveFile = "src/saves/" + saveFiles[commandNum];
                GameState gs = loadPanel.getViewManager().loadGame(selectedSaveFile);

                loadPanel.getViewManager().restoreGameState(gs);

                switch (gs.currentMode) {
                    case "Build" -> loadPanel.getViewManager().switchTo("BuildPanel", true);
                    case "Play" -> loadPanel.getViewManager().switchTo("HallPanel", true);
                    case "Home" -> loadPanel.getViewManager().switchTo("HomePanel", true);
                    case "Boss" -> loadPanel.getViewManager().switchTo("BossPanel", true);
                }

                System.out.println(gs.toString());
            } else {
                loadPanel.getViewManager().switchTo("TitlePanel", true);
            }
        }
    }

    public static String[] listSaveFiles() {
        File saveDir = new File("src/saves");

        if (saveDir.exists() && saveDir.isDirectory()) {
            File[] saveFiles = saveDir.listFiles((dir, name) -> name.endsWith(".ser"));

            if (saveFiles != null && saveFiles.length > 0) {
                String[] fileNames = new String[saveFiles.length];
                for (int i = 0; i < saveFiles.length; i++) {
                    fileNames[i] = saveFiles[i].getName();
                }
                return fileNames;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}
