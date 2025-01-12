package views;

import listeners.keylisteners.TitlePanelKeyListener;
import managers.ViewManager;
import managers.soundManager;
import utils.PanelUtils;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends NonPlayablePanel {

    private int commandNum;
    private final TitlePanelKeyListener titlePanelKeyListener;
    private soundManager soundManager;
    private ImageIcon backgroundImage;
    private final ImageIcon menuScreen = new ImageIcon(getClass().getResource("/res/tiles/MenuScreen.png"));
    ImageIcon scaledIcon;

    public TitlePanel(ViewManager viewManager) {
        super(viewManager);
        this.soundManager = soundManager.getInstance();
        setCommandNum(0);
        this.titlePanelKeyListener = new TitlePanelKeyListener(this);
        this.addKeyListener(titlePanelKeyListener);

        // Set background image
        backgroundImage = menuScreen;
        Image scaledImage = backgroundImage.getImage().getScaledInstance(1600, 1000, Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(scaledImage);

//        playMusic(0);
        managers.soundManager.getInstance().activeClips.put(1, soundManager.clip);
        System.out.println(managers.soundManager.getInstance().activeClips);
    }

    public void playMusic(int i) {
        soundManager.setFile(i);
        soundManager.play();
        soundManager.loop();
    }

    public void stopMusic() {
        soundManager.stop();
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
        Graphics2D g2 = (Graphics2D) g;

        drawTitleScreen(g2, this);

    }

    public void drawTitleScreen(Graphics2D g2, BasePanel panel) {
        g2.setColor(new Color(62, 41, 52)); // Background color
        g2.fillRect(0, 0, BasePanel.screenWidth, BasePanel.screenHeight);

        // Background Image
        g2.drawImage(scaledIcon.getImage(), 0, 0, null);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
        String titleText = "RoKue Like";
        int titleX = PanelUtils.getXForCenteredText(titleText, panel, g2) - 400;
        int titleY = BasePanel.screenHeight / 5;

        // Shadow
        g2.setColor(new Color(40, 35, 38));
        g2.drawString(titleText, titleX + 3, titleY + 3);

        // Main text
        g2.setColor(new Color(26, 17, 23));
        g2.drawString(titleText, titleX, titleY);

        // MENU OPTIONS
        String[] menuOptions = {"NEW GAME", "LOAD GAME", "QUIT GAME", "HELP"}; // Help seçeneğini ekledik
        int menuStartY = titleY + BasePanel.tileSize * 5;
        int lineHeight = BasePanel.tileSize * 3;

        // Fixed positions for menu options
        int[] optionYPositions = new int[menuOptions.length];
        for (int i = 0; i < menuOptions.length; i++) {
            optionYPositions[i] = menuStartY + (i * lineHeight);
        }

        for (int i = 0; i < menuOptions.length; i++) {
            String optionText = menuOptions[i];
            int optionX = 200;
            int optionY = optionYPositions[i];

            if (commandNum == i) {
                // Apply special effect for the selected option
                g2.setColor(new Color(255, 215, 0)); // Highlight color (gold)
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 56F)); // Larger font size
                g2.drawString(">", optionX - BasePanel.tileSize, optionY); // Arrow indicator
            } else {
                g2.setColor(Color.white); // Default color
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F)); // Default font size
            }

            // Draw the option text at the fixed position
            g2.drawString(optionText, optionX, optionY);
        }
    }
}