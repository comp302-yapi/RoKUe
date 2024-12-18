package views;

import listeners.keylisteners.TitlePanelKeyListener;
import managers.ViewManager;
import utils.PanelUtils;
import java.awt.*;

public class TitlePanel extends NonPlayablePanel {

    private int commandNum;
    private final TitlePanelKeyListener titlePanelKeyListener;

    public TitlePanel(ViewManager viewManager) {
        super(viewManager);
        setCommandNum(0);
        this.titlePanelKeyListener = new TitlePanelKeyListener(this);
        this.addKeyListener(titlePanelKeyListener);
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
        g2.fillRect(0, 0, panel.screenWidth, panel.screenHeight);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
        String text = "RoKue Like";
        int x, y;
        x = PanelUtils.getXForCenteredText(text, panel, g2) - panel.tileSize*2;
        y = panel.tileSize * 3;

        // SHADOW
        g2.setColor(new Color(40, 35, 38));
        g2.drawString(text, x+5, y+5);

        // MAIN COLOR
        g2.setColor(new Color(26, 17, 23));
        g2.drawString(text, x, y);

        // BLUE BOY IMAGE
        /*
        x = panel.screenWidth/2 - (panel.tileSize*2)/2 -(panel.tileSize*2);
        y += panel.tileSize*3;
        g2.drawImage(panel.monster[2].up1, x, y, panel.tileSize*2, panel.tileSize*2, null);
        */
        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW GAME";
        x = PanelUtils.getXForCenteredText(text, panel, g2) - panel.tileSize*2;
        y += panel.tileSize*5;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - panel.tileSize, y);
        }

        text = "LOAD GAME";
        x = PanelUtils.getXForCenteredText(text, panel, g2) - panel.tileSize*2;
        y += panel.tileSize*3;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - panel.tileSize, y);
        }

        text = "QUIT GAME";
        x = PanelUtils.getXForCenteredText(text, panel, g2) - panel.tileSize*2;
        y += panel.tileSize*3;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - panel.tileSize, y);
        }
    }
}
