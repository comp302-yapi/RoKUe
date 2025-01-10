package views;

import listeners.keylisteners.LoadPanelKeyListener;
import listeners.keylisteners.TitlePanelKeyListener;
import managers.ViewManager;
import managers.soundManager;
import utils.PanelUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class LoadPanel extends NonPlayablePanel {

    private int commandNum;
    private final LoadPanelKeyListener loadPanelKeyListener;
    private soundManager soundManager;
    private ImageIcon backgroundImage;
    private final ImageIcon menuScreen = new ImageIcon(getClass().getResource("/res/tiles/MenuScreen.png"));
    ImageIcon scaledIcon;

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

        // Set background image
        backgroundImage = menuScreen;
        Image scaledImage = backgroundImage.getImage().getScaledInstance(1600, 1000, Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(scaledImage);

        drawTitleScreen(g2, this);
    }

    public void drawTitleScreen(Graphics2D g2, BasePanel panel) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, BasePanel.screenWidth, BasePanel.screenHeight);

        // Background Image
        g2.drawImage(scaledIcon.getImage(), 0, 0, null);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
        String title = "Choose Save";
        int x, y;
        x = 200;
        y = BasePanel.tileSize * 3;

        g2.setColor(Color.white);
        g2.drawString(title, x, y);

        // Fetch save files
        String[] saveFiles = listSaveFiles();
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        y += BasePanel.tileSize * 3;

        // Draw save files
        if (saveFiles != null) {
            for (int i = 0; i < saveFiles.length; i++) {
                String saveFile = saveFiles[i];
                x = 200;

                if (commandNum == i) {
                    // Special effect for the selected save file
                    g2.setColor(Color.YELLOW); // Highlight color
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 56F)); // Larger font size
                    g2.drawString(">", x - BasePanel.tileSize, y);
                } else {
                    g2.setColor(Color.white); // Default color
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F)); // Default font size
                }

                g2.drawString(saveFile, x, y);
                y += BasePanel.tileSize * 2;
            }
        }

        // Draw "Go Back" button in bottom right corner
        String goBackText = "Go Back";
        x = 200;
        y = BasePanel.screenHeight - BasePanel.tileSize * 2;

        if (commandNum == (saveFiles != null ? saveFiles.length : 0)) {
            // Special effect for the selected "Go Back" button
            g2.setColor(Color.white); // Highlight color
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 56F)); // Larger font size
            g2.drawString(">", x - BasePanel.tileSize, y);
        } else {
            g2.setColor(Color.white); // Default color
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F)); // Default font size
        }

        g2.drawString(goBackText, x, y);
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
