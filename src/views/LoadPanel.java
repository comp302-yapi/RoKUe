package views;

import listeners.keylisteners.LoadPanelKeyListener;
import listeners.keylisteners.TitlePanelKeyListener;
import managers.ViewManager;
import managers.soundManager;
import utils.PanelUtils;
import java.awt.*;
import java.io.File;

public class LoadPanel extends NonPlayablePanel {

    private int commandNum;
    private final LoadPanelKeyListener loadPanelKeyListener;
    private soundManager soundManager;

    public LoadPanel(ViewManager viewManager) {
        super(viewManager);
        this.soundManager = managers.soundManager.getInstance();
        setCommandNum(0);
        this.loadPanelKeyListener = new LoadPanelKeyListener(this);
        this.addKeyListener(loadPanelKeyListener);
    }

    public int getCommandNum() {
        return commandNum;
    }

    public void setCommandNum(int commandNum) {
        this.commandNum = commandNum;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        drawTitleScreen(g2, this);
    }

    public void drawTitleScreen(Graphics2D g2, BasePanel panel) {
        g2.setColor(new Color(62, 41, 52));
        g2.fillRect(0, 0, BasePanel.screenWidth, BasePanel.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
        String title = "Choose Save";
        int x, y;
        x = PanelUtils.getXForCenteredText(title, panel, g2) - BasePanel.tileSize * 2;
        y = BasePanel.tileSize * 3;

        g2.setColor(new Color(40, 35, 38));
        g2.drawString(title, x + 5, y + 5);

        g2.setColor(new Color(26, 17, 23));
        g2.drawString(title, x, y);

        // Fetch save files
        String[] saveFiles = listSaveFiles();
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        y += BasePanel.tileSize * 3;

        // Draw save files
        if (saveFiles != null) {
            for (int i = 0; i < saveFiles.length; i++) {
                String saveFile = saveFiles[i];
                x = PanelUtils.getXForCenteredText(saveFile, panel, g2) - BasePanel.tileSize * 2;
                g2.drawString(saveFile, x, y);
                if (commandNum == i) {
                    g2.drawString(">", x - BasePanel.tileSize, y);
                }
                y += BasePanel.tileSize * 2;
            }
        }

        // Draw "Go Back" button in bottom right corner
        String goBackText = "Go Back";
        x = BasePanel.screenWidth - 175 - BasePanel.tileSize * 2;
        y = BasePanel.screenHeight - BasePanel.tileSize * 2;

        g2.setColor(new Color(26, 17, 23));
        g2.drawString(goBackText, x, y);

        // Highlight "Go Back" if selected
        if (commandNum == (saveFiles != null ? saveFiles.length : 0)) {
            g2.drawString(">", x - BasePanel.tileSize, y);
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
}
