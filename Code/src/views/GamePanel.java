package views;

import entity.Entity;
import entity.Player;
import listeners.keylisteners.GamePanelKeyListener;
import managers.AssetSetter;
import managers.CollisionChecker;
import managers.ViewManager;
import object.SuperObject;
import managers.TileManager;
import utils.PanelUtils;
import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel implements BasePanel {

    private final TileManager tileM;
    private final Player player;
    private final SuperObject[] obj;
    private final Entity[] monsters;
    private final CollisionChecker cChecker;
    private final ViewManager viewManager;
    private final GamePanelKeyListener keyListener;
    private boolean isPaused;

    public GamePanel(ViewManager viewManager) {
        keyListener =  new GamePanelKeyListener(this);
        tileM = new TileManager(this);
        player = new Player(this, keyListener);
        this.addKeyListener(keyListener);
        obj = new SuperObject[10];
        monsters = new Entity[20];
        cChecker = new CollisionChecker(this, tileM);
        this.viewManager = viewManager;

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        AssetSetter aSetter = new AssetSetter(this);
        aSetter.setObject();
        aSetter.spawnMonster();
    }

    @Override
    public void update() {
        if (!isPaused()) {
            player.move();

            for (Entity monster : monsters) {
                if (monster != null) {
                    monster.update();
                }
            }
        }
    }

    @Override
    public void showMessage(String message) {
        System.out.println("Message");
    }

    @Override
    public CollisionChecker getCollisionChecker() {
        return cChecker;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Entity[] getMonsters() {
        return monsters;
    }

    @Override
    public SuperObject[] getSuperObjects() {
        return obj;
    }

    @Override
    public TileManager getTileManager() {
        return tileM;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public ViewManager getViewManager() {
        return viewManager;
    }

    public void paintComponent(Graphics g) {
        // Update game
        update();

        // Repaint game
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Draw tiles
        tileM.draw(g2);

        // Draw super objects
        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(g2, this);
            }
        }

        // Draw monsters
        for (Entity monster : monsters) {
            if (monster != null) {
                monster.draw(g2);
            }
        }

        // Draw player
        player.draw(g2);

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
        y = this.screenHeight / 2;
        g2.drawString(text, x, y);
    }
}
