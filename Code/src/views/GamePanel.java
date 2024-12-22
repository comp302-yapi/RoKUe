package views;

import entity.Arrow;
import entity.Entity;
import listeners.keylisteners.GamePanelKeyListener;
import managers.AssetSetter;
import managers.ViewManager;
import object.SuperObject;
import utils.PanelUtils;

import java.awt.*;


public class GamePanel extends PlayablePanel {

    private final GamePanelKeyListener keyListener;
    private boolean isPaused;

    public GamePanel(ViewManager viewManager) {
        super(viewManager);

        keyListener = new GamePanelKeyListener(this);
        this.addKeyListener(keyListener);
        getPlayer().addKeyListener(keyListener);

        AssetSetter aSetter = new AssetSetter(this);
        initAssets(aSetter);
    }

    private void initAssets(AssetSetter aSetter) {
        aSetter.setObject();
        aSetter.spawnMonster();
    }

    @Override
    public void update() {
        getPlayer().move();

        // Update monsters
        for (Entity monster : getMonsters()) {
            if (monster != null) {
                monster.update();
            }
        }

        // Update arrows
        for (int i = 0; i < getArrows().length; i++) {
            if (getArrows()[i] != null) {
                getArrows()[i].update();

                // Remove expired arrows
                if (getArrows()[i].isExpired()) {
                    getArrows()[i] = null;
                }
            }
        }


    }

    @Override
    public void showMessage(String message) {
        System.out.println("Message");
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void paintComponent(Graphics g) {
        // Update game
        update();

        // Repaint game
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setFont(arial_40);

        // Draw tiles
        getTileManager().draw(g2);

        // Draw super objects
        for (SuperObject superObject : getSuperObjects()) {
            if (superObject != null) {
                superObject.draw(g2, this);
            }
        }

        // Draw monsters
        for (Entity monster : getMonsters()) {
            if (monster != null) {
                monster.draw(g2);
            }
        }
        // Draw arrows
        for (Arrow arrow : getArrows()) {
            if (arrow != null) {
                arrow.draw(g2);
            }
        }

        // Draw player
        getPlayer().draw(g2);

        // If paused
        if (isPaused()) {
            drawPauseScreen(g2);
        }

        g2.dispose();
    }

    private void drawPauseScreen(Graphics2D g2) {
        int x, y;
        String text = "PAUSED";
        x = PanelUtils.getXForCenteredText(text, this, g2);
        y = BasePanel.screenHeight / 2;
        g2.drawString(text, x, y);
    }
}
