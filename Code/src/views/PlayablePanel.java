package views;

import entity.Entity;
import entity.Player;
import managers.CollisionChecker;
import managers.TileManager;
import managers.ViewManager;
import object.SuperObject;
import javax.swing.*;
import java.awt.*;

public abstract class PlayablePanel extends JPanel implements BasePanel {

    private final Player player;
    private final TileManager tileM;
    private final SuperObject[] obj;
    private final Entity[] monsters;
    private final CollisionChecker cChecker;
    private final ViewManager viewManager;

    protected PlayablePanel(ViewManager viewManager) {
        this.player = new Player(this);
        this.tileM = new TileManager(this);
        this.obj = new SuperObject[15];
        this.monsters = new Entity[20];
        this.cChecker = new CollisionChecker(this, tileM);
        this.viewManager = viewManager;

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
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

    @Override
    public ViewManager getViewManager() {
        return viewManager;
    }
}
